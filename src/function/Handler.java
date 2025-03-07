package function;

import com.sun.net.httpserver.HttpExchange;

@FunctionalInterface
public interface Handler {
    void handle(HttpExchange exchange);
}
