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

    public static double elementLuminance(int R8bit) {
        if (R8bit <= 10) {
            return R8bit / 3294.6; // For very low values, linear luminance at RsRGB / 12.92
        }
        else {
            double RsRGB = R8bit / 255.0;
            return (Math.pow(((RsRGB + 0.055) / 1.055), 2.4)); // Luminance rises faster than square of the sRGB element
        }
    }

    public static double relativeLuminance(int[] RGB) {
        int R8bit = RGB[0];
        int G8bit = RGB[1];
        int B8bit = RGB[2];
        double R = elementLuminance(R8bit);
        double G = elementLuminance(G8bit);
        double B = elementLuminance(B8bit);
        return 0.2126*R + 0.7152*G + 0.0722*B;
    }

    public static double contrastRatio(int[] RGB1, int[] RGB2) {
        double lum1 = relativeLuminance(RGB1);
        double lum2 = relativeLuminance(RGB2);
        double greater = lum1;
        double lesser;
        if (lum2 > lum1) {
            greater = lum2;
            lesser = lum1;
        }
        else {
            lesser = lum2;
        }
        return ((greater + 0.05) / (lesser + 0.05));
    }

    protected static int getTargetRGBDarker(double lum, double ratio) {
        double targetLum = ((lum + .05) / ratio) - .05;
        double targetFrac;
        if (targetLum < (663.0 / 16384)) {
            targetFrac = targetLum * 12.92; // For when the RGB values will end up being 10 or less
        }
        else {
            double deExp = Math.pow(targetLum, (1/2.4));
            targetFrac = (deExp * 1.055) - 0.055;
        }
        return (int) Math.floor(targetFrac * 255);
    }

    public static void printDarkerContrastColors(int[] RGB) {
        //Helps find colors that meet accessible standards for adequate contrast between font color and background
        double lum = relativeLuminance(RGB);
        if (lum >= .1) {
            int targetRGB = getTargetRGBDarker(lum, 3.0);
            System.out.println("A grayscale color that is darker than this " +
                    "and has a contrast ratio of 3 or greater with this color is " +
                    "[" + targetRGB + ", " + targetRGB + ", " + targetRGB + "]");
        }
        if (lum >= .175) {
            int targetRGB = getTargetRGBDarker(lum, 4.5);
            System.out.println("A grayscale color that is darker than this " +
                    "and has a contrast ratio of 4.5 or greater with this color is " +
                    "[" + targetRGB + ", " + targetRGB + ", " + targetRGB + "]");
        }
        if (lum >= .3) {
            int targetRGB = getTargetRGBDarker(lum, 7.0);
            System.out.println("A grayscale color that is darker than this " +
                    "and has a contrast ratio of 7 or greater with this color is " +
                    "[" + targetRGB + ", " + targetRGB + ", " + targetRGB + "]");
        }
    }

    protected static int getTargetRGBBrighter(double lum, double ratio) {
        double targetLum = ((lum + .05) * ratio) - .05;
        double targetFrac;
        if (targetLum < (663.0 / 16384)) {
            targetFrac = targetLum * 12.92;
        }
        else {
            double deExp = Math.pow(targetLum, (1/2.4));
            targetFrac = (deExp * 1.055) - 0.055;
        }
        return (int) Math.ceil(targetFrac * 255);
    }

    public static void printLighterContrastColors(int[] RGB) {
        double lum = relativeLuminance(RGB);
        if (lum <= .3) {
            int targetRGB = getTargetRGBBrighter(lum, 3.0);
            System.out.println("A grayscale color that is brighter than this " +
                    "and has a contrast ratio of 3 or greater with this color is " +
                    "[" + targetRGB + ", " + targetRGB + ", " + targetRGB + "]");
        }
        if (lum <= (11.0/60.0)) {
            int targetRGB = getTargetRGBBrighter(lum, 4.5);
            System.out.println("A grayscale color that is brighter than this " +
                    "and has a contrast ratio of 4.5 or greater with this color is " +
                    "[" + targetRGB + ", " + targetRGB + ", " + targetRGB + "]");
        }
        if (lum <= .1) {
            int targetRGB = getTargetRGBBrighter(lum, 7.0);
            System.out.println("A grayscale color that is brighter than this " +
                    "and has a contrast ratio of 7 or greater with this color is"  +
                    "[" + targetRGB + ", " + targetRGB + ", " + targetRGB + "]");
        }
    }

    public static double getRelativeLuminanceFromContRatioWithBlack(double contrastRatio) {
        return contrastRatio*.05-.05;
    }

    /*
    Midpoint luminance:
    Of two colors, brighter and darker, with brighter having relative luminance greater than or
    equal to darker, take the contrastRatio.
    Then, take the square root of that contrast ratio. That is the desired contrast ratio (Dcr)
    that brighter and darker should each have with midpoint color.
    Let darkerLum be the relative luminance of darker.
    Take (darkerLum+.05)*Dcr-.05. This is the relative luminance of the midpoint color.
    You can now find colors that match the hue of each color but have midpoint brightness.
    For example, if you darken (34, 177, 76) to its midpoint contrast ratio with (136, 0, 21), it's (23, 123, 53).
    The other color, adjusted to essentially equal midpoint luminance, becomes (215, 0, 32).
    (Brightness match is better and hue match worse with (22, 123, 53) and (215, 0, 38).)
     */


}
