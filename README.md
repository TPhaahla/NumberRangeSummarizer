# Number Range Summarizer

## Overview
The `FormatRanges` class implements the `NumberRangeSummarizer` interface and provides a method to format a collection of integers into a comma-delimited string of numbers, grouping the numbers into ranges when they are sequential.

Sample Input: "1,3,6,7,8,12,13,14,15,21,22,23,24,31

Result: "1, 3, 6-8, 12-15, 21-24, 31"

### Summary

| Aspect            | Details           |
|-------------------|-------------------|
| Time Complexity   | O(n)              |
| Space Complexity  | O(n)              |
| Number of Unit Tests | 15             |

#### Time Complexity
The time complexity of `O(n)` is optimal for this problem since we need to iterate through all the elements when checking for sequential numbers.

#### Space Complexity
The space complexity is `O(n)` since we need to store the ranges. 
However, in the case that we can output the result incrementally, we could reduce peak emmory usage.
