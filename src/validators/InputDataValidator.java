package validators;

import com.sun.net.httpserver.HttpExchange;
import utils.DecodingFileUtil;
import utils.ReadingData;
import utils.enumPack.UserLegalNullValuesDefined;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class InputDataValidator {
    private InputDataValidator() {
        throw new IllegalStateException("Utility class");
    }

    private static boolean checkForValidData(String row) {
        String[] array = row.split("&");
        return checkForValidData(array);
    }

    private static boolean checkForValidData(String[] array) {
        return Arrays.stream(array)
                .allMatch(InputDataValidator::checkForValidText);
    }

    private static Map<String, String> getDataInput(String row) {
        return DecodingFileUtil.parseUrlEncoded(row, "&");
    }

    public static Optional<Map<String, String>>  getValidDataOrThrowAnException(HttpExchange exchange) {
        String row = ReadingData.getData(exchange);

        boolean isDataValid = checkForValidData(row);

        if (!isDataValid) {
            return Optional.empty();
        }
        return Optional.ofNullable(getDataInput(row));
    }

    private static boolean checkForValidText(String input) {
        if (!input.contains("=")) return false;

        String[] array = input.split("=");

        if (checkForNullableValue(array[0])) return true;
        if (array.length != 2) return false;

        String val = array[1];
        return !val.isBlank();
    }

    private static boolean checkForNullableValue(String employeeArg) {
        try {
            return UserLegalNullValuesDefined.parseString(employeeArg).isDefine();
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
