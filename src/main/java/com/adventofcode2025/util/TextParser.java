package com.adventofcode2025.util;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextParser {

    public static List<String> parseCommaSeparatedLine(String fileName) {
        try {
            InputStream resourceStream = TextParser.class.getResourceAsStream("/" + fileName);
            if (resourceStream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(resourceStream, StandardCharsets.UTF_8));
                String line = br.readLine();

                if (line != null && br.readLine() == null) {
                    return new ArrayList<>(Arrays.asList(line.split(",")));
                }
                else {
                    System.err.println("Not a single line file.");
                    return null;
                }
            }
            else {
                System.err.println("Resource not found: " + fileName);
                return null;
            }
        }
        catch (Exception e) {
            System.err.println("Failed to load file: " + fileName);
            return null;
        }
    }

    public static List<String> parseInput(String fileName) {
        try {
            InputStream resourceStream = TextParser.class.getResourceAsStream("/" + fileName);
            if (resourceStream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(resourceStream, StandardCharsets.UTF_8));
                return getLines(br);
            }
            else {
                System.err.println("Resource not found: " + fileName);
                return null;
            }
        }
        catch (Exception e) {
            System.err.println("Failed to load file: " + fileName);
            return null;
        }
    }

    private static List<String> getLines(BufferedReader br) throws Exception {
        List<String> lines = new ArrayList<>();

        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();

            if (StringUtils.isEmpty(line)) {
                continue;
            }

            lines.add(line);
        }

        return lines;
    }
}
