package com.adventofcode2025.days;

import com.adventofcode2025.util.TextParser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day7 {

    public static String processPart1() {
        List<String> lines = TextParser.getLines("day7_input.txt");

        char[][] matrix = getMatrix(lines);
        RowResult rowResult = null;

        int totalSplits = 0;

        for (int i = 0; i < matrix.length - 1; i++) {
            char[] row = matrix[i];
            updateRow(row, rowResult);

            char[] nextRow = matrix[i + 1];
            rowResult = processRow(row, nextRow);

            totalSplits += rowResult.numSplits;
        }

        return String.valueOf(totalSplits);
    }

    public static String processPart2() {
        List<String> lines = TextParser.getLines("day7_input.txt");

        char[][] matrix = getMatrix(lines);

        int rowIndex = 0;
        char[] row = matrix[rowIndex];
        int colIndex = getStartingIndex(row);

        Map<Index, Long> nodePathMap = new HashMap<>();

        long totalTimelines = getAllTimelines(matrix, rowIndex, colIndex, nodePathMap);

        return String.valueOf(totalTimelines);
    }

    private static long getAllTimelines(char[][] matrix, int rowIndex, int colIndex, Map<Index, Long> nodePathMap) {
        if (rowIndex == matrix.length - 1) {
            return 1;
        }

        Index index = new Index(rowIndex, colIndex);
        if (nodePathMap.containsKey(index)) {
            return nodePathMap.get(index);
        }

        char[] rowBelow = matrix[rowIndex + 1];
        char charBelow = rowBelow[colIndex];

        if (charBelow != '^') {
            long paths = getAllTimelines(matrix, rowIndex + 1, colIndex, nodePathMap);
            nodePathMap.put(index, paths);
            return paths;
        }
        else {
            long timeLinesLeft = 0;
            long timeLinesRight = 0;

            if (colIndex - 1 >= 0) {
                timeLinesLeft = getAllTimelines(matrix, rowIndex + 1, colIndex - 1, nodePathMap);
            }

            if (colIndex + 1 < rowBelow.length) {
                timeLinesRight = getAllTimelines(matrix, rowIndex + 1, colIndex + 1, nodePathMap);
            }

            long paths = timeLinesLeft + timeLinesRight;
            nodePathMap.put(index, paths);
            return paths;
        }
    }

    private static int getStartingIndex(char[] row) {
        for (int i = 0; i < row.length; i++) {
            if (row[i] == 'S') return i;
        }
        return -1;
    }

    private static void updateRow(char[] row, RowResult rowResult) {
        if (rowResult == null) {
            return;
        }

        rowResult.indices.forEach(index -> row[index] = '|');
    }

    private static RowResult processRow(char[] currentRow, char[] nextRow) {
        Set<Integer> indices = new HashSet<>();
        int splits = 0;

        for (int i = 0; i < currentRow.length; i++) {
            char c = currentRow[i];

            if (c != 'S' && c != '|') {
                continue;
            }

            char c2 = nextRow[i];

            if (c2 == '^') {
                splits++;

                int left = i - 1;
                int right = i + 1;

                if (left >= 0) indices.add(left);
                if (right < currentRow.length) indices.add(right);
            }
            else {
                indices.add(i);
            }
        }

        return new RowResult(splits, indices);
    }

    private static char[][] getMatrix(List<String> lines) {
        char[][] arr = new char[lines.size()][lines.getFirst().length()];

        for (int i = 0; i < lines.size(); i++) {
            arr[i] = lines.get(i).toCharArray();
        }

        return arr;
    }

    private record RowResult(int numSplits, Set<Integer> indices) {}

    private record Index(int row, int col) {}
}
