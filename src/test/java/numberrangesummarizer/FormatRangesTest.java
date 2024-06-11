package numberrangesummarizer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.ArrayList;
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

    @Test
    public void testFormRanges_summarizeCollection_EmptyList(){
        Collection<Integer> input = new ArrayList<>();
        String summary = collector.summarizeCollection(input);
        String expectedResult = "";
        assertEquals(expectedResult, summary);

    }

    @Test
    public void testFormRanges_summarizeCollection_SingleNumber(){
        Collection<Integer> input = new ArrayList<>();
        input.add(3);
        String summary = collector.summarizeCollection(input);
        String expectedResult = "3";
        assertEquals(expectedResult, summary);

    }

    @Test
    public void testFormRanges_summarizeCollection_NonSequentialNumbers(){
        Collection<Integer> input = new ArrayList<>(Arrays.asList(1,3,6,7,8,12,13,14,15,21,22,23,24,31));
        String summary = collector.summarizeCollection(input);
        String expectedResult = "1, 3, 6-8, 12-15, 21-24, 31";
        assertEquals(expectedResult, summary);
    }

    @Test
    public void testFormRanges_summarizeCollection_SequentialNumbers(){
        Collection<Integer> input = new ArrayList<>(Arrays.asList(3,4,5,6,7,8,9,10,11,12,13,14,15,16,17));
        String summary = collector.summarizeCollection(input);
        String expectedResult = "3-17";
        assertEquals(expectedResult, summary);
    }

    @Test
    public void testFormRanges_summarizeCollection_UnsortedInput(){
        Collection<Integer> input = new ArrayList<>(Arrays.asList(24,3,7,6,15,8,14,13,12,1,23,31,22,21));
        String summary = collector.summarizeCollection(input);
        String expectedResult = "1, 3, 6-8, 12-15, 21-24, 31";
        assertEquals(expectedResult, summary);
    }

    @Test
    public void testFormRanges_summarizeCollection_MixedNumbers(){
        Collection<Integer> input = new ArrayList<>(Arrays.asList(24,-3,7,-6,-15,-8,14,-13,12,1,-23,31,22,21));
        String summary = collector.summarizeCollection(input);
        String expectedResult = "1, 3, 6-8, 12-15, 21-24, 31";
        assertEquals(expectedResult, summary);
    }

    @Test
    public void testFormRanges_summarizeCollection_Duplicates(){
        Collection<Integer> input = new ArrayList<>(Arrays.asList(1,3,3,3,6,7,7,7,8,12,13,14,14,14,15,21,22,23,24,31,32,33,33,33,40));
        String summary = collector.summarizeCollection(input);
        String expectedResult = "1, 3, 6-8, 12-15, 21-24, 31-33, 40";
        assertEquals(expectedResult, summary);
    }

}
