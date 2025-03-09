package utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ReadingData {

    private ReadingData() {
        throw new IllegalStateException("Utility class");
    }

    public static String getData(HttpExchange exchange) {
        try (BufferedReader reader = getReader(exchange)) {
            if (reader.ready())
                return readingBufferReader(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String readingBufferReader(BufferedReader reader) {
        return reader.lines()
                .collect(Collectors.joining(""));
    }

    private static BufferedReader getReader(HttpExchange exchange) {
        InputStream inputStream = exchange.getRequestBody();
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }
}
