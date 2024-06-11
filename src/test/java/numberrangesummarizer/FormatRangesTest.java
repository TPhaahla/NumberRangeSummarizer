package numberrangesummarizer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;

class FormatRangesTest {
    private final FormatRanges collector = new FormatRanges();

    @Test
    public void testFormatRanges_collect_ValidInput() {
        String input = "1,2,3,4,5";
        Collection<Integer> result = collector.collect(input);
        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5 }, result.toArray());
    }

    @Test
    public void testFormatRanges_collect_EmptyInput() {
        String input = "";
        Collection<Integer> result = collector.collect(input);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFormatRanges_collect_NullInput() {
        String input = null;
        Collection<Integer> result = collector.collect(input);
        assertTrue(result.isEmpty(), "The collection should be empty for null input.");
    }

    @Test
    public void testFormatRanges_collect_NegativeNumbers() {
        String input = "-6,-5,-4,-3,-2,-1";
        Collection<Integer> result = collector.collect(input);
        assertArrayEquals(new Integer[] { -6, -5, -4, -3, -2, -1 }, result.toArray());
    }

    @Test
    public void testFormRanges_collect_TrailingAndLeadingWhiteSpaces() {
        String input = "1, 2, 3 ,4 ,5,6";
        // Collection<Integer> result = collector.collect(input);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            collector.collect(input);
        });
        assertEquals("Invalid input: Input should be comma delimited with no whitespace characters.",
                exception.getMessage());
    }

    @Test
    public void testFormRanges_collect_WhiteSpaceDelimited() {
        String input = "1 2 3 4 5 6 7 8";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            collector.collect(input);
        });
        assertEquals("Invalid input: Input should be comma delimited with no whitespace characters.",
                exception.getMessage());
    }

    @Test
    public void testFormRanges_collect_MixedWhiteSpaces() {
        String input = "1,2,\t3,4\n,5,\r6";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            collector.collect(input);
        });
        assertEquals("Invalid input: Input should be comma delimited with no whitespace characters.",
                exception.getMessage());
    }

    @Test
    public void testFormRanges_collect_InvalidInput(){
        String input = "1,2and,3,4,5,6";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            collector.collect(input);
        });
        assertEquals("Invalid Number: 2and", exception.getMessage());
    }

}
