package com.adventofcode2025;

import com.adventofcode2025.days.Day1;
import com.adventofcode2025.days.Day2;
import com.adventofcode2025.days.Day3;
import com.adventofcode2025.days.Day4;

import com.adventofcode2025.days.Day5;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class Main {
    public static void main(String[] args) {
        process(Day.DAY5);
    }

    public static void process(Day day) {
        String part1Solution;
        String part2Solution;

        switch (day) {
            case DAY1 -> {
                part1Solution = Day1.processPart1();
                part2Solution = Day1.processPart2();
            }
            case DAY2 -> {
                part1Solution = Day2.processPart1();
                part2Solution = Day2.processPart2();
            }
            case DAY3 -> {
                part1Solution = Day3.processPart1();
                part2Solution = Day3.processPart2();
            }
            case DAY4 -> {
                part1Solution = Day4.processPart1();
                part2Solution = Day4.processPart2();
            }
            case DAY5 -> {
                part1Solution = Day5.processPart1();
                part2Solution = Day5.processPart2();
            }
            default -> throw new IllegalStateException("Unexpected day: " + day);
        }

        System.out.println(day.getLabel() + " Part 1 Solution: " + part1Solution);
        System.out.println(day.getLabel() + " Part 2 Solution: " + part2Solution);
    }

    @Getter
    @AllArgsConstructor
    public enum Day {
        DAY1("Day 1"),
        DAY2("Day 2"),
        DAY3("Day 3"),
        DAY4("Day 4"),
        DAY5("Day 5");

        private final String label;
    }
}
