package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class ColorNamer {

    static int[] blackValues = {0, 0, 0};
    static Pair<int[], String> black = new Pair<int[], String> (blackValues, "Black");
    static int[] whiteValues = {255, 255, 255};
    static Pair<int[], String> white = new Pair<int[], String> (whiteValues, "White");
    static int[] dimGrayValues = {105, 105, 105};
    static Pair<int[], String> dimGray = new Pair<int[], String> (dimGrayValues, "DimGray");
    static int[] grayValues = {128, 128, 128};
    static Pair<int[], String> gray = new Pair<int[], String> (grayValues, "Gray");
    static int[] lightGrayValues = {211, 211, 211};
    static Pair<int[], String> lightGray = new Pair<int[], String> (lightGrayValues, "LightGray");
    static int[] whiteSmokeValues = {245, 245, 245};
    static Pair<int[], String> whiteSmoke = new Pair<int[], String> (whiteSmokeValues, "WhiteSmoke");
    static final ArrayList<Pair<int[], String>> grayscaleColors = new ArrayList<>(
            Arrays.asList(black, dimGray, gray, lightGray, whiteSmoke, white));


    public static double hspBrightness(int[] rgb) {
        int R = rgb[0];
        int G = rgb[1];
        int B = rgb[2];
        return Math.sqrt((0.299 * R*R + 0.587 * G*G + 0.114 * B*B));
    }


    public static String nameClosestGrayscaleColor(int[] rgb) {
        double thisBrightness = hspBrightness(rgb);
        String closest = "startingBounds";
        double smallestDifference = 65025;
        for (Pair<int[], String> color : grayscaleColors) {
            double difference = Math.abs(thisBrightness - hspBrightness(color.getKey()));
            if (difference <= smallestDifference) {
                smallestDifference = difference;
                closest = color.getValue();
            }
        }
        return closest;
    }



}
