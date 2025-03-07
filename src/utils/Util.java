package utils;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Util {
    private Util() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> Type getType(TypeToken<T> typeToken) {
        return typeToken.getType();
    }
}
