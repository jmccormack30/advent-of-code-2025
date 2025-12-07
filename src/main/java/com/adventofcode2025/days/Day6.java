package com.adventofcode2025.days;

import com.adventofcode2025.util.TextParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day6 {

    public static String processPart1() {
        List<String> lines = TextParser.getLines("day6_input.txt");

        Map<Integer, List<String>> indexProblemMap = new HashMap<>();

        for (String line : lines) {
            String[] arr = line.split("\\s+");

            for (int i = 0; i < arr.length; i++) {
                indexProblemMap.computeIfAbsent(i, k -> new ArrayList<>()).add(arr[i]);
            }
        }

        long sum = 0;

        for (List<String> list : indexProblemMap.values()) {
            sum += getTotal(list);
        }

        return String.valueOf(sum);
    }

    public static String processPart2() {
        List<String> lines = TextParser.getLinesNoTrim("day6_input.txt");
        normalizeStringLengths(lines);

        Map<Integer, List<String>> indexProblemMap = getPart2ProblemMap(lines);

        long sum = 0;

        for (Map.Entry<Integer, List<String>> entry : indexProblemMap.entrySet()) {
            List<String> problem = entry.getValue();
            sum += getTotalPart2(problem);
        }

        return String.valueOf(sum);
    }

    private static long getTotalPart2(List<String> problem) {
        List<Long> numbers = getTranslatedNumbers(problem);
        String operation = problem.getLast();

        long total;
        if ("*".equals(operation)) {
            total = 1;
        }
        else if ("+".equals(operation)) {
            total = 0;
        }
        else {
            throw new IllegalStateException("Operation is invalid: " + operation);
        }

        for (long num : numbers) {
            if ("*".equals(operation)) {
                total *= num;
            }
            else {
                total += num;
            }
        }

        return total;
    }

    private static List<Long> getTranslatedNumbers(List<String> problem) {
        Map<Integer, StringBuilder> numberMap = new HashMap<>();

        for (int i = 0; i < problem.size() - 1; i++) {
            String numberStr = problem.get(i);

            char[] arr = numberStr.toCharArray();

            for (int j = 0; j < arr.length; j++) {
                char c = arr[j];

                if (!Character.isWhitespace(c)) {
                    numberMap.computeIfAbsent(j, k -> new StringBuilder()).append(c);
                }
            }
        }

        List<Long> list = new ArrayList<>();

        for (StringBuilder numberStr : numberMap.values()) {
            list.add(Long.parseLong(numberStr.toString()));
        }

        return list;
    }

    private static void normalizeStringLengths(List<String> lines) {
        int maxLength = lines.stream().mapToInt(String::length).max().orElse(0);

        for (int i = 0; i < lines.size(); i++) {
            String original = lines.get(i);
            int needed = maxLength - original.length();

            if (needed > 0) {
                String newLine = (original + " ".repeat(needed));
                lines.set(i, newLine);
            }
        }
    }

    private static Map<Integer, List<String>> getPart2ProblemMap(List<String> lines) {
        Map<Integer, List<String>> indexProblemMap = new HashMap<>();

        char[][] matrix = new char[lines.size() - 1][lines.getFirst().length()];

        for (int i = 0; i < lines.size() - 1; i++) {
            char[] charArr = lines.get(i).toCharArray();
            System.out.println(Arrays.toString(charArr));
            matrix[i] = charArr;
        }

        for (char[] rowArr : matrix) {
            int index = 0;
            StringBuilder number = new StringBuilder();

            for (int col = 0; col < rowArr.length; col++) {
                char c = rowArr[col];

                number.append(c);

                if (isNextColumnSpacer(matrix, col + 1)) {
                    indexProblemMap.computeIfAbsent(index, k -> new ArrayList<>()).add(number.toString());

                    number = new StringBuilder();
                    index++;
                    col++;
                }
            }
        }

        String operationsLine = lines.getLast();
        String[] operationsArr = operationsLine.split("\\s+");

        for (int i = 0; i < operationsArr.length; i++) {
            indexProblemMap.get(i).add(operationsArr[i]);
        }

        return indexProblemMap;
    }

    private static boolean isNextColumnSpacer(char[][] matrix, int col) {
        int numCols = matrix[0].length;

        if (col >= numCols) {
            return true;
        }

        for (char[] row : matrix) {
            if (!Character.isWhitespace(row[col])) {
                return false;
            }
        }

        return true;
    }

    private static long getTotal(List<String> list) {
        long total;
        String operation = list.getLast();

        if ("*".equals(operation)) {
            total = 1;
        }
        else if ("+".equals(operation)) {
            total = 0;
        }
        else {
            throw new IllegalStateException("Operation is invalid: " + operation);
        }

        for (int i = 0; i < list.size() - 1; i++) {
            long number = Long.parseLong(list.get(i));

            if ("*".equals(operation)) {
                total *= number;
            }
            else {
                total += number;
            }
        }

        return total;
    }
}
