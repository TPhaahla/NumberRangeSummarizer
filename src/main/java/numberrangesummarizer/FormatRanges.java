package numberrangesummarizer;

import java.util.*;

public class FormatRanges implements NumberRangeSummarizer{
    @Override
    public Collection<Integer> collect(String input){
        Collection<Integer> numbers = new ArrayList<>();

        if (input!=null && !input.isEmpty()){
            Scanner scanner = new Scanner(input).useDelimiter(",");
            while (scanner.hasNext()){
                if(scanner.hasNextInt()){
//                    System.out.println(scanner.nextInt());
                    numbers.add(scanner.nextInt());
                }
                else {
                    System.err.println("Invalid Number: " + scanner.next());
                }
            }
            scanner.close();
        }
        return numbers;
    }

    @Override
    public String summarizeCollection(Collection<Integer> input) {

        if (input== null || input.isEmpty()) {
            return "";
        }

        List<Integer> numbers = new ArrayList<>(input);

        List<String> summary = new ArrayList<>();
        int first_pointer = numbers.getFirst();
        int second_pointer = first_pointer;

        for(int i=1; i<numbers.size(); i++){
            int current_reference = numbers.get(i);

            if(current_reference == second_pointer + 1) {
                second_pointer = current_reference;
            }
            else {
                addRange(summary, first_pointer, second_pointer);
                first_pointer = current_reference;
                second_pointer = first_pointer;
            }
        }
        return summary.toString();
    }

    private void addRange(List<String> summary, int start, int end){
        if(start==end){
            summary.add(String.valueOf(start));
        }
        else{
            summary.add(String.valueOf(start) + "-" + String.valueOf(end));
        }
    }


}
