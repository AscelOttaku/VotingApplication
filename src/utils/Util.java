package utils;

import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Util {
    private Util() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> Type getType(TypeToken<T> typeToken) {
        return typeToken.getType();
    }

    public static Integer calculateVoteProccentInAllVotes(long voteQuantity, long totalVotesQuantity) {
        if (voteQuantity == 0 || totalVotesQuantity == 0)
            return 0;

        return Math.toIntExact((100 * voteQuantity) / totalVotesQuantity);
    }

    public static Map<String, Object> createDataModel(String name, Object arg) {
        return Map.of(name, arg);
    }

    public static long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String getCookieValue(HttpExchange exchange) {
        return exchange.getRequestHeaders()
                .getOrDefault("Cookie", List.of(""))
                .getFirst();
    }
}
