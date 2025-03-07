package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DecodingFileUtil {
    private DecodingFileUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Map<String, String> parseUrlEncoded(String input, String delimiter) {
        String[] lines = input.split(delimiter);

        return Arrays.stream(lines)
                .map(DecodingFileUtil::decode)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Optional<Map.Entry<String, String>> decode(String kv) {
        if (kv == null || kv.isBlank() || !kv.contains("="))
            return Optional.empty();

        String[] parts = kv.split("=");

        String key = URLDecoder.decode(parts[0], StandardCharsets.UTF_8);
        String value = URLDecoder.decode(parts[1], StandardCharsets.UTF_8);

        return Optional.of(Map.entry(key, value));
    }
}
