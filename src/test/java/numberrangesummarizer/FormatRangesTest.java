package numberrangesummarizer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Tshiamo
 * Unit Tests for the FormatRanges class
 */
class FormatRangesTest {
    private final FormatRanges collector = new FormatRanges();

    /**
     * Tests the collect method with valid input. The test asserts that the resulting collection contains exactly the comma delimited elements in the input string.
     */
    @Test
    public void testFormatRanges_collect_ValidInput() {
        String input = "1,2,3,4,5";
        Collection<Integer> result = collector.collect(input);
        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5 }, result.toArray());
    }

    /**
     * Tests the collect method with empty string input. The test asserts that the resulting collection is empty by checking if it's length is zero.
     */
    @Test
    public void testFormatRanges_collect_EmptyInput() {
        String input = "";
        Collection<Integer> result = collector.collect(input);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * Tests that if the input is null, then the resulting collection is empty.
     */
    @Test
    public void testFormatRanges_collect_NullInput() {
        String input = null;
        Collection<Integer> result = collector.collect(input);
        assertTrue(result.isEmpty(), "The collection should be empty for null input.");
    }

    /**
     * Tests that the collect method is able to process negative integer values by asserting that the resulting collection contains exactly, the comma delimited elements in the input string.
     */
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

    /**
     * Tests the collect method with mixed whitespace characters in the input.
     * The input contains a tab, newline and carriage return, which should result in an IllegalArgumentException being thrown.
     */
    @Test
    public void testFormRanges_collect_MixedWhiteSpaces() {
        String input = "1,2,\t3,4\n,5,\r6";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            collector.collect(input);
        });
        assertEquals("Invalid input: Input should be comma delimited with no whitespace characters.",
                exception.getMessage());
    }

    /**
     * Tests the collect method with mixed whitespace characters in the input.
     * The input contains string characters, which should result in an IllegalArgumentException being thrown.
     */
    @Test
    public void testFormRanges_collect_InvalidInput(){
        String input = "1,2and,3,4,5,6";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            collector.collect(input);
        });
        assertEquals("Invalid Number: 2and", exception.getMessage());
    }

    /**
     * Tests the summarizeCollection method with an empty list as input and asserts that the result is an empty string.
     */
    @Test
    public void testFormRanges_summarizeCollection_EmptyList(){
        Collection<Integer> input = new ArrayList<>();
        String summary = collector.summarizeCollection(input);
        String expectedResult = "";
        assertEquals(expectedResult, summary);

    }

    /**
     * Tests the summarizeCollection method with a single number and asserts that this does not return a range but rather a single element as a string.
     */
    @Test
    public void testFormRanges_summarizeCollection_SingleNumber(){
        Collection<Integer> input = new ArrayList<>();
        input.add(3);
        String summary = collector.summarizeCollection(input);
        String expectedResult = "3";
        assertEquals(expectedResult, summary);

    }

    /**
     * Tests the summarizeCollection method with a sorted list of non-sequential integers.
     */
    @Test
    public void testFormRanges_summarizeCollection_NonSequentialNumbers(){
        Collection<Integer> input = new ArrayList<>(Arrays.asList(1,3,6,7,8,12,13,14,15,21,22,23,24,31));
        String summary = collector.summarizeCollection(input);
        String expectedResult = "1, 3, 6-8, 12-15, 21-24, 31";
        assertEquals(expectedResult, summary);
    }

    /**
     * Tests the summarizeCollection method with input as a list of sequential numbers and asserts that this results in only one range being returned.
     */
    @Test
    public void testFormRanges_summarizeCollection_SequentialNumbers(){
        Collection<Integer> input = new ArrayList<>(Arrays.asList(3,4,5,6,7,8,9,10,11,12,13,14,15,16,17));
        String summary = collector.summarizeCollection(input);
        String expectedResult = "3-17";
        assertEquals(expectedResult, summary);
    }

    /**
     * Tests the summarizeCollection method with input as a list of unsorted integers.
     */
    @Test
    public void testFormRanges_summarizeCollection_UnsortedInput(){
        Collection<Integer> input = new ArrayList<>(Arrays.asList(24,3,7,6,15,8,14,13,12,1,23,31,22,21));
        String summary = collector.summarizeCollection(input);
        String expectedResult = "1, 3, 6-8, 12-15, 21-24, 31";
        assertEquals(expectedResult, summary);
    }

    /**
     * Tests the summarizeCollection method with input as a list with both negative and positive integers.
     */
    @Test
    public void testFormRanges_summarizeCollection_MixedNumbers(){
        Collection<Integer> input = new ArrayList<>(Arrays.asList(24,-3,7,-6,-15,-8,14,-13,12,1,-23,31,22,21));
        String summary = collector.summarizeCollection(input);
        String expectedResult = "-23, -15, -13, -8, -6, -3, 1, 7, 12, 14, 21-22, 24, 31";
        assertEquals(expectedResult, summary);
    }

    /**
     * Tests the summarizeCollection method with input as a list that contains duplicate integers.
     */
    @Test
    public void testFormRanges_summarizeCollection_Duplicates(){
        Collection<Integer> input = new ArrayList<>(Arrays.asList(1,3,3,3,6,7,7,7,8,12,13,14,14,14,15,21,22,23,24,31,32,33,33,33,40));
        String summary = collector.summarizeCollection(input);
        String expectedResult = "1, 3, 6-8, 12-15, 21-24, 31-33, 40";
        assertEquals(expectedResult, summary);
    }

}
