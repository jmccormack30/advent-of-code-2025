package com.adventofcode2025.days;

import com.adventofcode2025.util.TextParser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Day8 {

    public static String processPart1() {
        List<String> lines = TextParser.getLines("day8_input.txt");

        List<Box> boxes = getBoxes(lines);
        Map<Double, List<BoxPair>> shortestBoxPairMap = getShortestBoxPairMap(boxes);
        Map<Box, Integer> boxCircuitMap = getBoxCircuitMap(boxes);

        int remainingConnections = 1000;

        for (Map.Entry<Double, List<BoxPair>> entry : shortestBoxPairMap.entrySet()) {
            List<BoxPair> boxPairs = entry.getValue();
            BoxPair boxPair = getBoxPair(boxPairs, boxCircuitMap);

            if (boxPair == null) {
                remainingConnections = remainingConnections - 1;
                if (remainingConnections == 0) {
                    break;
                }
                continue;
            }

            Box box1 = boxPair.box1;
            Box box2 = boxPair.box2;

            int newCircuitNumber = boxCircuitMap.get(box1);
            int oldCircuitNumber = boxCircuitMap.get(box2);

            updateCircuits(boxCircuitMap, oldCircuitNumber, newCircuitNumber);

            remainingConnections = remainingConnections - 1;
            if (remainingConnections == 0) {
                break;
            }
        }

        Map<Integer, Integer> circuitCountMap = new HashMap<>();

        for (Integer circuit : boxCircuitMap.values()) {
            circuitCountMap.merge(circuit, 1, Integer::sum);
        }

        List<Integer> top3Values = circuitCountMap.values()
                .stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .toList();

        int product = top3Values.stream()
                .reduce(1, (a, b) -> a * b);

        return String.valueOf(product);
    }

    public static String processPart2() {
        List<String> lines = TextParser.getLines("day8_input.txt");

        List<Box> boxes = getBoxes(lines);
        Map<Double, List<BoxPair>> shortestBoxPairMap = getShortestBoxPairMap(boxes);
        Map<Box, Integer> boxCircuitMap = getBoxCircuitMap(boxes);

        int x1 = 0;
        int x2 = 0;

        for (Map.Entry<Double, List<BoxPair>> entry : shortestBoxPairMap.entrySet()) {
            List<BoxPair> boxPairs = entry.getValue();
            BoxPair boxPair = getBoxPair(boxPairs, boxCircuitMap);

            if (boxPair == null) {
                continue;
            }

            Box box1 = boxPair.box1;
            Box box2 = boxPair.box2;

            int newCircuitNumber = boxCircuitMap.get(box1);
            int oldCircuitNumber = boxCircuitMap.get(box2);

            updateCircuits(boxCircuitMap, oldCircuitNumber, newCircuitNumber);

            Map<Integer, Integer> circuitCountMap = new HashMap<>();

            for (Integer circuit : boxCircuitMap.values()) {
                circuitCountMap.merge(circuit, 1, Integer::sum);
            }

            if (circuitCountMap.size() == 1) {
                x1 = box1.x;
                x2 = box2.x;
                break;
            }
        }

        long product = (long) x1 * x2;

        return String.valueOf(product);
    }

    private static void updateCircuits(Map<Box, Integer> boxCircuitMap, int oldCircuitNumber, int newCircuitNumber) {
        for (Map.Entry<Box, Integer> entry : boxCircuitMap.entrySet()) {
            if (entry.getValue().equals(oldCircuitNumber)) {
                entry.setValue(newCircuitNumber);
            }
        }
    }

    private static List<Box> getBoxes(List<String> lines) {
        List<Box> boxes = new ArrayList<>();

        for (String line : lines) {
            String[] arr = line.split(",");

            int x = Integer.parseInt(arr[0]);
            int y = Integer.parseInt(arr[1]);
            int z = Integer.parseInt(arr[2]);

            boxes.add(new Box(x, y, z));
        }

        return boxes;
    }

    private static Map<Box, Integer> getBoxCircuitMap(List<Box> boxes) {
        Map<Box, Integer> boxCircuitMap = new HashMap<>();

        for (int i = 0; i < boxes.size(); i++) {
            boxCircuitMap.put(boxes.get(i), i);
        }

        return boxCircuitMap;
    }

    private static BoxPair getBoxPair(List<BoxPair> boxPairs, Map<Box, Integer> boxCircuitMap) {
        Iterator<BoxPair> iterator = boxPairs.iterator();
        while (iterator.hasNext()) {
            BoxPair pair = iterator.next();

            Box pairBox1 = pair.box1;
            Box pairBox2 = pair.box2;

            if (!Objects.equals(boxCircuitMap.get(pairBox1), boxCircuitMap.get(pairBox2))) {
                iterator.remove();
                return pair;
            }
        }

        return null;
    }

    private static Map<Double, List<BoxPair>> getShortestBoxPairMap(List<Box> boxes) {
        Map<Double, List<BoxPair>> shortestBoxPairMap = new TreeMap<>();

        for (int i = 0; i < boxes.size(); i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                Box box1 = boxes.get(i);
                Box box2 = boxes.get(j);

                double distance = calculateStraightLineDistance(box1.x, box1.y, box1.z, box2.x, box2.y, box2.z);
                shortestBoxPairMap.computeIfAbsent(distance, k -> new ArrayList<>()).add(new BoxPair(box1, box2));
            }
        }

        return shortestBoxPairMap;
    }

    private static double calculateStraightLineDistance(int x1, int y1, int z1, int x2, int y2, int z2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }

    private record Box(int x, int y, int z) {}

    private record BoxPair(Box box1, Box box2) {}
}
