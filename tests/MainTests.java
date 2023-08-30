import org.junit.Assert;
import org.junit.Test;


public class MainTests {

    @Test
    public void testBadInput_EmptyString() {
        try {
            Main.calc("");
            Assert.fail("is not an arithmetic statement");
        } catch (Exception ignore) {
            // success
        }
    }

    @Test
    public void testBadInput_IsNotAnArithmeticStatement() {
        try {
            Main.calc("1");
            Assert.fail("isn't an arithmetic stmt.");
        } catch (Exception ignore) {
        }

        try {
            Main.calc("1 + ");
            Assert.fail("isn't an arithmetic stmt.");
        } catch (Exception ignore) {
        }

        try {
            Main.calc("V");
            Assert.fail("isn't an arithmetic stmt.");
        } catch (Exception ignore) {
        }

        try {
            Main.calc("V - ");
            Assert.fail("isn't an arithmetic stmt.");
        } catch (Exception ignore) {
        }
    }

    @Test
    public void testBadInput_DifferentNumbersTypes() {
        try {
            Main.calc("1 + I");
            Assert.fail("different numbers types");
        } catch (Exception ignore) {
        }

        try {
            Main.calc("I + 1");
            Assert.fail("different numbers types");
        } catch (Exception ignore) {
        }
    }

    @Test
    public void testAdd() {

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {

                int actual = Integer.parseInt(Main.calc(i + " + " + j));
                Assert.assertEquals(i + j, actual);
            }
        }
    }

    @Test
    public void testSub() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {

                int actual = Integer.parseInt(Main.calc(i + " - " + j));
                Assert.assertEquals(i - j, actual);
            }
        }
    }

    @Test
    public void testMul() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {

                int actual = Integer.parseInt(Main.calc(i + " * " + j));
                Assert.assertEquals(i * j, actual);
            }
        }
    }

    @Test
    public void testDiv() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {

                int actual = Integer.parseInt(Main.calc(i + " / " + j));
                Assert.assertEquals(i / j, actual);
            }
        }
    }

    @Test
    public void testBadInput_ToRoman() {
        try {
            Main.toRoman(0);
            Assert.fail("roman numbers don't have 0");
        } catch (Exception ignore) {
        }

        try {
            Main.toRoman(-1);
            Assert.fail("roman numbers don't have negative numbers");
        } catch (Exception ignore) {
        }

        try {
            Main.toRoman(101);
            Assert.fail("a too large number");
        } catch (Exception ignore) {
        }
    }

    @Test
    public void testBadInput_ParseRoman() {
        try {
            Main.parseRoman("");
            Assert.fail("empty string");
        } catch (Exception ignore) {
        }

        try {
            Main.parseRoman("0");
            Assert.fail("isn't a roman number");
        } catch (Exception ignore) {
        }

        try {
            Main.parseRoman("XXVIIG");
            Assert.fail("isn't a roman number");
        } catch (Exception ignore) {
        }
    }

    @Test
    public void testRoman() {

        for (int i = 1; i <= 100; i++) {
            Assert.assertEquals(i, Main.parseRoman(Main.toRoman(i)));
        }
    }

    @Test
    public void testStatementPattern() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {

                Assert.assertTrue(Main.ARABIC.matcher(i + " + " + j).find());
                Assert.assertTrue(Main.ARABIC.matcher(i + " - " + j).find());
                Assert.assertTrue(Main.ARABIC.matcher(i + " * " + j).find());
                Assert.assertTrue(Main.ARABIC.matcher(i + " / " + j).find());

                Assert.assertTrue(Main.ROMAN.matcher(Main.toRoman(i) + " * " + Main.toRoman(j)).find());
                Assert.assertTrue(Main.ROMAN.matcher(Main.toRoman(i) + " - " + Main.toRoman(j)).find());
                Assert.assertTrue(Main.ROMAN.matcher(Main.toRoman(i) + " + " + Main.toRoman(j)).find());
                Assert.assertTrue(Main.ROMAN.matcher(Main.toRoman(i) + " / " + Main.toRoman(j)).find());
            }
        }
    }
}
