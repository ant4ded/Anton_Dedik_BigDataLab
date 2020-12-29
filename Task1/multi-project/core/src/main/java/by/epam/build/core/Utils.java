package by.epam.build.core;

import by.epam.utils.StringUtils;

import java.util.Arrays;

public class Utils {
    public static boolean isAllPositiveNumbers(String... strings) {
        return Arrays.stream(strings)
                .filter(StringUtils::isPositiveNumber)
                .count() == strings.length;
    }
}
