package numberrangesummarizer;

import java.util.*;

/**
 * @author Tshiamo
 * A class which implements the NumberRangeSummarizer interface.
 */
public class FormatRanges implements NumberRangeSummarizer {

    /**
     * Formats a string of comma-delimited integers into a collection of integer elements
     * @param input a string of comma delimited integers
     * @return a collection of integers
     */
    @Override
    public Collection<Integer> collect(String input) {
        Collection<Integer> numbers = new ArrayList<>();

        if (input != null && !input.isEmpty()) {
            if (input.matches(".*\\s+.*") || input.contains("\t")) {
                throw new IllegalArgumentException(
                        "Invalid input: Input should be comma delimited with no whitespace characters.");
            }

            Scanner scanner = new Scanner(input).useDelimiter(",");
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    numbers.add(scanner.nextInt());
                } else {
                    throw new IllegalArgumentException("Invalid Number: " + scanner.next());
                }
            }
            scanner.close();
        }
        return numbers;
    }

    /**
     * Formats a collection of integers into a comma-delimited string of numbers, grouping the numbers into ranges when they are sequential
     * @param input a collection of integers to be processed in order to determine the ranges
     * @return a string representing the formatted ranges of numbers separated by commas, or an empty string if the input is empty or null
     */
    @Override
    public String summarizeCollection(Collection<Integer> input) {

        if (input == null || input.isEmpty()) {
            return "";
        }

        // Use HashSet to remove duplicates
        Set<Integer> unique_input = new HashSet<>(input);
        List<Integer> numbers = new ArrayList<>(unique_input);

        // Presort the list
        Collections.sort(numbers);

        List<String> summary = new ArrayList<>();

        int first_pointer = numbers.getFirst();
        int second_pointer = first_pointer;

        for (int i = 1; i < numbers.size(); i++) {
            int current_reference = numbers.get(i);

            if (current_reference == second_pointer + 1) {
                second_pointer = current_reference;
            } else {
                addRange(summary, first_pointer, second_pointer);
                first_pointer = current_reference;
                second_pointer = first_pointer;
            }
        }
        addRange(summary, first_pointer, second_pointer);
        return String.join(", ", summary);
    }

    /**
     * Appends the range or a single number to the summary list of formatted ranges.
     * @param summary the list to append the ranges to
     * @param start the start of the range
     * @param end the end of the range
     */
    private void addRange(List<String> summary, int start, int end) {
        if (start == end) {
            summary.add(String.valueOf(start));
        } else {
            summary.add(String.valueOf(start) + "-" + String.valueOf(end));
        }
    }

}
