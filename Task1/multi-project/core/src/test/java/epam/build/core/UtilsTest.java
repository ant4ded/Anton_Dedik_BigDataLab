package epam.build.core;

import by.epam.build.core.Utils;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {
    @Test
    public void isAllPositiveNumbers_oneNull_false() {
        Assert.assertFalse(Utils.isAllPositiveNumbers(null, "4535", "67"));
    }

    @Test
    public void isAllPositiveNumbers_empty_false() {
        Assert.assertFalse(Utils.isAllPositiveNumbers("", "4535", "67"));
    }

    @Test
    public void isAllPositiveNumbers_positiveNumbers_true() {
        Assert.assertTrue(Utils.isAllPositiveNumbers("123", "4535", "67"));
    }

    @Test
    public void isAllPositiveNumbers_notAllPositiveNumbers_false() {
        Assert.assertFalse(Utils.isAllPositiveNumbers("-123", "4535", "67"));
    }

    @Test
    public void isAllPositiveNumbers_notAllNumbers_false() {
        Assert.assertFalse(Utils.isAllPositiveNumbers("123q", "4535", "67"));
    }
}