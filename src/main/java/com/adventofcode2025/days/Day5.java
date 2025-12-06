package com.adventofcode2025.days;

import com.adventofcode2025.util.TextParser;

import java.util.ArrayList;
import java.util.List;

public class Day5 {

    public static String processPart1() {
        List<String> lines = TextParser.getLines("day5_input.txt");
        InputData data = getInputData(lines);

        List<Range> ranges = getRanges(data.ranges);

        int total = 0;

        for (String idStr : data.ingredients) {
            long id = Long.parseLong(idStr);

            for (Range range : ranges) {
                if (id >= range.lowerBound && id <= range.upperBound) {
                    total++;
                    break;
                }
            }
        }

        return String.valueOf(total);
    }

    public static String processPart2() {
        List<String> lines = TextParser.getLines("day5_input.txt");
        InputData data = getInputData(lines);

        List<Range> ranges = getRanges(data.ranges);
        List<Range> visitedRanges = new ArrayList<>();

        long total = 0;

        for (Range range : ranges) {
            long lowerBound = range.lowerBound;
            long upperBound = range.upperBound;

            boolean skipRange = false;

            for (Range visitedRange : visitedRanges) {
                if (lowerBound >= visitedRange.lowerBound && upperBound <= visitedRange.upperBound) {
                    skipRange = true;
                    break;
                }

                if (lowerBound >= visitedRange.lowerBound && lowerBound <= visitedRange.upperBound) {
                    lowerBound = visitedRange.upperBound + 1;
                }

                if (upperBound <= visitedRange.upperBound && upperBound >= visitedRange.lowerBound) {
                    upperBound = visitedRange.lowerBound - 1;
                }
            }

            if (skipRange) {
                continue;
            }

            visitedRanges.add(new Range(lowerBound, upperBound));

            total += (upperBound - lowerBound + 1);
        }

        return String.valueOf(total);
    }

    private static List<Range> getRanges(List<String> rangeStrs) {
        List<Range> ranges = new ArrayList<>();

        for (String rangeStr : rangeStrs) {
            String[] arr = rangeStr.split("-");

            long lowerBounds = Long.parseLong(arr[0]);
            long upperBounds = Long.parseLong(arr[1]);

            ranges.add(new Range(lowerBounds, upperBounds));
        }

        return ranges;
    }

    private static InputData getInputData(List<String> lines) {
        List<String> ranges = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();

        for (String line : lines) {
            if (line.contains("-")) {
                ranges.add(line);
            }
            else {
                ingredients.add(line);
            }
        }

        return new InputData(ranges, ingredients);
    }

    private record InputData(List<String> ranges, List<String> ingredients) {}

    private record Range(long lowerBound, long upperBound) {}
}
