package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Optional;

public class JsonMapper {
    private static final Gson gson;

    private JsonMapper() {
        throw new IllegalStateException("Utility class");
    }

    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .setPrettyPrinting().create();
    }

    public static <T> Optional<T> readFile(String path, Type type) {
        try {
            return Optional.of(gson.fromJson(new FileReader(path), type));
        } catch (JsonSyntaxException | FileNotFoundException | NullPointerException e) {
            return Optional.empty();
        }
    }

    public static <T> void writeToFile(T value, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            String json = gson.toJson(value);
            writer.write(json);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

