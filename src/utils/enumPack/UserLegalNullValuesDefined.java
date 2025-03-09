package utils.enumPack;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum UserLegalNullValuesDefined {
    NAME(false), SURNAME(false), MIDDLE_NAME(true), EMAIL(false), PASSWORD(false);

    private final boolean define;

    UserLegalNullValuesDefined(boolean define) {
        this.define = define;
    }

    public boolean isDefine() {
        return define;
    }

    private static UserLegalNullValuesDefined findSimilarEnumObj(String val) {
        return Arrays.stream(UserLegalNullValuesDefined.values())
                .map(Enum::toString)
                .filter(enumVal -> removeSymbol(enumVal).equalsIgnoreCase(val))
                .findFirst()
                .map(enumStr -> UserLegalNullValuesDefined.valueOf(enumStr.toUpperCase()))
                .orElseThrow(() -> new NoSuchElementException(val));
    }

    public static UserLegalNullValuesDefined parseString(String arg) {
        return findSimilarEnumObj(arg);
    }

    private static String removeSymbol(String str) {
        return str.replace("_", "");
    }
}
