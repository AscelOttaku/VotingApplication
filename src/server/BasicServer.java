package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import exceptions.ExceptionBody;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import function.Handler;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BasicServer {
    private final HttpServer server;
    private final static Configuration freemarker = initFreeMarker();
    private final Map<String, Handler> handlers = new HashMap<>();

    public BasicServer(int port) throws IOException {
        server = createServer(port);
        registerMainHandler();
    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            // путь к каталогу в котором у нас хранятся шаблоны
            // это может быть совершенно другой путь, чем тот, откуда сервер берёт файлы
            // которые отправляет пользователю
            cfg.setDirectoryForTemplateLoading(new File("data"));

            // прочие стандартные настройки о них читать тут
            // https://freemarker.apache.org/docs/pgui_quickstart_createconfiguration.html
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
        try {
            // Загружаем шаблон из файла по имени.
            // Шаблон должен находится по пути, указанном в конфигурации
            Template temp = freemarker.getTemplate(templateFile);

            // freemarker записывает преобразованный шаблон в объект класса writer
            // а наш сервер отправляет клиенту массивы байт
            // по этому нам надо сделать "мост" между этими двумя системами

            // создаём поток, который сохраняет всё, что в него будет записано в байтовый массив
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // создаём объект, который умеет писать в поток и который подходит для freemarker
            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {

                // обрабатываем шаблон заполняя его данными из модели
                // и записываем результат в объект "записи"
                temp.process(dataModel, writer);
                writer.flush();

                // получаем байтовый поток
                byte[] data = stream.toByteArray();

                // отправляем результат клиенту
                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private void registerMainHandler() {
        server.createContext("/", this::handleIncomingRequest);
    }

    private static void setContentType(HttpExchange exchange, ContentType type) {
        exchange.getResponseHeaders().set("Content-Type", String.valueOf(type));
    }

    private void handleIncomingRequest(HttpExchange exchange) {
        Handler handler = getHandlers().getOrDefault(makeKey(exchange), this::sendResponse404);
        handler.handle(exchange);
    }

    protected void sendResponse404(HttpExchange exchange) {
        String errorMessage = "404 Not Found";
        try {
            sendByteData(exchange, ResponseCodes.NOT_FOUND, ContentType.TEXT_PLAIN, errorMessage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToActionFailedPage(HttpExchange exchange, ExceptionBody body) {
        renderTemplate(exchange, "error/errorPage.ftlh", Map.of("exception", body));
    }

    private HttpServer createServer(int port) throws IOException {
        return HttpServer.create(new InetSocketAddress(port), 50);
    }

    public void lunchServer() {
        server.start();
    }

    private static String makeKey(String method, String route) {
        return String.format("%s %s", method.toUpperCase(), route);
    }

    private static String makeKey(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        path = checkAndRemoveIfSlashAtTheEnd(path);

        path = ensureStartWithSlash(path);

        return makeKey(method, path);
    }

    private static String ensureStartWithSlash(String route) {
        return route.startsWith("/") ? route : "/" + route;
    }

    private static String checkAndRemoveIfSlashAtTheEnd(String route) {
        if (route.endsWith("/") && route.length() > 1)
            return route.substring(0, route.length() - 1);

        return route;
    }

    protected void registerGet(String url, Handler handler) {
        registerGenericHandler("GET", url, handler);
    }

    protected void registerPost(String url, Handler handler) {
        registerGenericHandler("POST", url, handler);
    }

    private void registerGenericHandler(String method, String route, Handler handler) {
        getHandlers().put(makeKey(method, route), handler);
    }

    public Map<String, Handler> getHandlers() {
        return handlers;
    }

    private Path makeFilePath(HttpExchange exchange) {
        return makeFilePath(exchange.getRequestURI().getPath());
    }

    protected Path makeFilePath(String... s) {
        return Path.of("data", s);
    }

    protected final void sendByteData(
            HttpExchange exchange,
            ResponseCodes responseCode,
            ContentType contentType,
            byte[] data
    ) throws IOException {
        try (OutputStream output = exchange.getResponseBody()) {
            setContentType(exchange, contentType);
            exchange.sendResponseHeaders(responseCode.getCode(), 0);
            output.write(data);
            output.flush();
        }
    }

    protected void redirect(HttpExchange exchange, String path) {
        try {
            exchange.getResponseHeaders().set("Location", path);
            exchange.sendResponseHeaders(ResponseCodes.REDIRECT.getCode(), 0);
            exchange.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected final void sendFile(
            HttpExchange exchange,
            Path pathToFile,
            ContentType contentType
    ) {
        try {
            if (Files.notExists(pathToFile)) {
                sendResponse404(exchange);
                return;
            }
            byte[] data = Files.readAllBytes(pathToFile);
            sendByteData(exchange, ResponseCodes.OK, contentType, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
