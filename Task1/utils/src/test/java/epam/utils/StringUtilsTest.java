package epam.utils;

import by.epam.utils.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void isPositiveNumber_positiveNumber_true() {
        Assert.assertTrue(StringUtils.isPositiveNumber("123"));
    }

    @Test
    public void isPositiveNumber_notNumber_false() {
        Assert.assertFalse(StringUtils.isPositiveNumber("123q"));
    }

    @Test
    public void isPositiveNumber_negativeNumber_false() {
        Assert.assertFalse(StringUtils.isPositiveNumber("-123"));
    }
}