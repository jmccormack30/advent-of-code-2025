package com.adventofcode2025.Day1;

import com.adventofcode2025.util.TextParser;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class Day1 {

    public static String processPart1() {
        List<String> lines = getLines();

        int dialNumber = 50;
        int timesAtZero = 0;

        for (String line : lines) {
            String direction = line.substring(0, 1);
            String numberStr = line.substring(1);

            int number = Integer.parseInt(numberStr);
            number = number % 100;

            if (direction.equals("R")) {
                dialNumber += number;
            }
            else {
                dialNumber -= number;
            }

            if (dialNumber < 0) {
                dialNumber = 100 - Math.abs(dialNumber);
            }
            else if (dialNumber > 99) {
                dialNumber = dialNumber % 100;
            }

            if (dialNumber == 0) timesAtZero++;
        }

        return String.valueOf(timesAtZero);
    }

    public static String processPart2() {
        List<String> lines = getLines();

        int dialNumber = 50;
        int timesPassingZero = 0;

        for (String line : lines) {
            String direction = line.substring(0, 1);
            String numberStr = line.substring(1);

            int number = Integer.parseInt(numberStr);

            int fullRotations = number / 100;
            timesPassingZero += fullRotations;

            number = number % 100;

            int oldDialNumber = dialNumber;

            if (direction.equals("R")) {
                dialNumber += number;
            }
            else {
                dialNumber -= number;
            }

            if (dialNumber > 100 || (oldDialNumber > 0 && dialNumber < 0)) timesPassingZero++;

            if (dialNumber < 0) {
                dialNumber = 100 - Math.abs(dialNumber);
            }
            else if (dialNumber > 99) {
                dialNumber = dialNumber % 100;
            }

            if (dialNumber == 0) timesPassingZero++;
        }

        return String.valueOf(timesPassingZero);
    }

    private static List<String> getLines() {
        List<String> lines = TextParser.parseInput("day1_input.txt");

        if (CollectionUtils.isEmpty(lines)) {
            throw new RuntimeException("Input text lines are empty!");
        }

        return lines;
    }
}
