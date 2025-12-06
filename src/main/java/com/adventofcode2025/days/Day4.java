package com.adventofcode2025.days;

import com.adventofcode2025.util.TextParser;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

public class Day4 {

    public static String processPart1() {
        Character[][] arr = TextParser.getCharArray("day4_input.txt");

        int numRows = arr.length;
        int numCols = arr[0].length;

        int sum = 0;

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            Character[] row = arr[rowIndex];

            for (int colIndex = 0; colIndex < numCols; colIndex++) {
                char c = row[colIndex];

                if (c != '@') {
                    continue;
                }

                if (getNumAdjacentRolls(arr, numRows, numCols, new Index(rowIndex, colIndex)) < 4) {
                    sum++;
                }
            }
        }

        return String.valueOf(sum);
    }

    public static String processPart2() {
        Character[][] arr = TextParser.getCharArray("day4_input.txt");

        int numRows = arr.length;
        int numCols = arr[0].length;

        int sum = 0;
        int removeableRolls = Integer.MAX_VALUE;

        while (removeableRolls > 0) {
            removeableRolls = 0;
            Set<Index> removeIndices = new HashSet<>();

            for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
                Character[] row = arr[rowIndex];

                for (int colIndex = 0; colIndex < numCols; colIndex++) {
                    Index currentIndex = new Index(rowIndex, colIndex);

                    char c = row[colIndex];

                    if (c != '@') {
                        continue;
                    }

                    if (getNumAdjacentRolls(arr, numRows, numCols, currentIndex) < 4) {
                        removeableRolls++;
                        removeIndices.add(currentIndex);
                    }
                }
            }

            if (removeableRolls > 0 && CollectionUtils.isNotEmpty(removeIndices)) {
                sum += removeableRolls;
                removeRolls(arr, removeIndices);
            }
        }

        return String.valueOf(sum);
    }

    private static void removeRolls(Character[][] arr, Set<Index> removeIndices) {
        for (Index index : removeIndices) {
            arr[index.row][index.col] = '.';
        }
    }

    /*
     * Returns the total number of rolls in the 8 adjacent positions
     */
    private static int getNumAdjacentRolls(Character[][] arr, int numRows, int numCols, Index index) {
        int total = 0;

        int rowIndex = index.row;
        int colIndex = index.col;

        for (int r = rowIndex - 1; r <= rowIndex + 1; r++) {
            for (int c = colIndex - 1; c <= colIndex + 1; c++) {
                if (!(r == rowIndex && c == colIndex) && r >= 0 && r < numRows && c >= 0 && c < numCols) {
                    total += isCharacterRoll(arr, new Index(r, c));
                }
            }
        }

        return total;
    }

    /*
     * Returns 1 if the character is a roll, otherwise returns 0
     */
    private static int isCharacterRoll(Character[][] arr, Index index) {
        char c = arr[index.row][index.col];
        return (c == '@') ? 1 : 0;
    }

    private record Index(int row, int col) {}
}
