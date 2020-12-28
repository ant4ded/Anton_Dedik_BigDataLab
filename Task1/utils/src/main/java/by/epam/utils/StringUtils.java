package by.epam.utils;

import org.apache.commons.lang3.math.NumberUtils;

public class StringUtils {
    public static boolean isPositiveNumber(String string) {
        return NumberUtils.isCreatable(string) && NumberUtils.toInt(string) > 0;
    }
}
