package com.adventofcode2025.Day3;

import com.adventofcode2025.util.TextParser;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Day3 {

    public static String processPart1() {
        List<String> lines = getLines();

        long sum = 0;

        for (String line : lines) {
            Integer[] bank = getBank(line);

            Set<Integer> visitedDigits = new HashSet<>();

            int firstDigit = 0;
            int secondDigit = 0;

            for (int i = 0; i < bank.length; i++) {
                int currentDigit = bank[i];

                if (currentDigit < firstDigit || i == bank.length - 1 || visitedDigits.contains(currentDigit)) {
                    continue;
                }

                visitedDigits.add(currentDigit);

                firstDigit = currentDigit;
                secondDigit = 0;

                for (int j = i + 1; j < bank.length; j++) {
                    if (bank[j] > secondDigit) secondDigit = bank[j];
                }
            }

            int maxJoltage = Integer.parseInt("" + firstDigit + secondDigit);
            sum += maxJoltage;
        }

        return String.valueOf(sum);
    }

    public static String processPart2() {
        List<String> lines = getLines();

        long sum = 0;
        final int BATTERY_LENGTH = 12;

        for (String line : lines) {
            // Keeps the keys sorted in descending order
            Map<Integer, List<Integer>> digitPositionMap = new TreeMap<>(Comparator.reverseOrder());
            Integer[] bank = getBank(line);

            for (int i = 0; i < bank.length; i++) {
                digitPositionMap.computeIfAbsent(bank[i], k -> new ArrayList<>()).add(i);
            }

            StringBuilder result = new StringBuilder();
            int length = line.length();

            for (int i = 0; i < BATTERY_LENGTH; i++) {
                int remainingDigits = BATTERY_LENGTH - i - 1;

                DigitAndIndex currentDigit = getNextDigit(digitPositionMap, remainingDigits, length);
                result.append(currentDigit.digit);

                updateDigitPositionMap(digitPositionMap, currentDigit.index);
            }

            long maxJoltage = Long.parseLong(result.toString());
            sum += maxJoltage;
        }

        return String.valueOf(sum);
    }

    /*
     * Return the biggest next available digit that will have enough remaining digits after it
     */
    private static DigitAndIndex getNextDigit(Map<Integer, List<Integer>> digitPositionMap, int remainingDigits, int totalLength) {
        for (Map.Entry<Integer, List<Integer>> entry : digitPositionMap.entrySet()) {
            int digit = entry.getKey();
            List<Integer> indices = entry.getValue();

            int firstOccurrence = indices.getFirst();
            int remaining = totalLength - firstOccurrence - 1;

            if (remaining >= remainingDigits) {
                indices.removeFirst();
                return new DigitAndIndex(digit, firstOccurrence);
            }
        }

        throw new RuntimeException("Unable to get next digit");
    }

    private static void updateDigitPositionMap(Map<Integer, List<Integer>> digitPositionMap, int prevDigitIndex) {
        digitPositionMap.entrySet().removeIf(entry -> {
            List<Integer> indices = entry.getValue();
            indices.removeIf(i -> i <= prevDigitIndex);
            return indices.isEmpty();
        });
    }

    private static Integer[] getBank(String line) {
        Integer[] bank = new Integer[line.length()];

        for (int i = 0; i < line.length(); i++) {
            bank[i] = Integer.parseInt(line.substring(i, i + 1));
        }

        return bank;
    }

    private static List<String> getLines() {
        List<String> lines = TextParser.parseInput("day3_input.txt");

        if (CollectionUtils.isEmpty(lines)) {
            throw new RuntimeException("Input is empty!");
        }

        return lines;
    }

    private record DigitAndIndex(int digit, int index) {}
}
