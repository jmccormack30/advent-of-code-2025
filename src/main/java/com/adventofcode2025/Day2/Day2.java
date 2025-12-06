package com.adventofcode2025.Day2;

import com.adventofcode2025.util.TextParser;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class Day2 {

    public static String processPart1() {
        List<String> ranges = getRanges();

        long sum = 0;

        for (String range : ranges) {
            String[] arr = range.split("-");

            long start = Long.parseLong(arr[0]);
            long end = Long.parseLong(arr[1]);

            for (long id = start; id <= end; id++) {
                String idStr = String.valueOf(id);

                if (idStr.length() % 2 != 0) {
                    continue;
                }

                if (idStr.substring(0, idStr.length() / 2).equals(idStr.substring(idStr.length() / 2))) {
                    sum += id;
                }
            }
        }

        return String.valueOf(sum);
    }

    public static String processPart2() {
        List<String> ranges = getRanges();

        long sum = 0;

        for (String range : ranges) {
            String[] arr = range.split("-");

            long start = Long.parseLong(arr[0]);
            long end = Long.parseLong(arr[1]);

            for (long id = start; id <= end; id++) {
                String idStr = String.valueOf(id);

                List<Integer> factors = getFactors(idStr.length());
                boolean isInvalid;

                for (int factor : factors) {
                    String pattern = null;
                    isInvalid = true;

                    for (int i = 0; i <= idStr.length() - factor; i += factor) {
                        String substring = idStr.substring(i, i + factor);

                        if (pattern == null) {
                            pattern = substring;
                        }
                        else if (!pattern.equals(substring)) {
                            isInvalid = false;
                            break;
                        }
                    }

                    if (isInvalid) {
                        sum += id;
                        break;
                    }
                }
            }
        }

        return String.valueOf(sum);
    }

    private static List<Integer> getFactors(int number) {
        List<Integer> factors = new ArrayList<>();

        for (int i = 1; i <= number / 2; i++) {
            if (number % i == 0) {
                factors.add(i);
            }
        }

        return factors;
    }

    private static List<String> getRanges() {
        List<String> ranges = TextParser.parseCommaSeparatedLine("day2_input.txt");

        if (CollectionUtils.isEmpty(ranges)) {
            throw new RuntimeException("Input is empty!");
        }

        return ranges;
    }
}
