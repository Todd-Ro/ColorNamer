package com.company;

public class Main {

    public static void main(String[] args) {
        int[] cyan = {0, 255, 255};
        System.out.println("Cyan has similar brightness to " + ColorNamer.nameClosestGrayscaleColor(cyan));
        int[] navy = {0, 0, 128};
        System.out.println("Navy has similar brightness to " + ColorNamer.nameClosestGrayscaleColor(navy));
        int[] magenta = {255, 0, 255};
        System.out.println("Magenta has similar brightness to " + ColorNamer.nameClosestGrayscaleColor(magenta));

        int[] mid = {128, 128, 128};
        System.out.println(ColorNamer.elementLuminance(128)); //Less than 0.5 due to nonlinear luminance
        System.out.println(ColorNamer.relativeLuminance(mid));

        System.out.println("128, 128, 128 has the following contrast colors: ");
        ColorNamer.printDarkerContrastColors(mid);
        ColorNamer.printLighterContrastColors(mid);
        System.out.println();

        System.out.println("Magenta has the following contrast colors: ");
        ColorNamer.printDarkerContrastColors(magenta);
        ColorNamer.printLighterContrastColors(magenta);
        System.out.println();

        System.out.println();



        double veryHighBandLowEndRatio = Math.pow(21, 252.0/253);
        System.out.println(veryHighBandLowEndRatio);
        double veryHighBandLowEndLum = ColorNamer.getRelativeLuminanceFromContRatioWithBlack(veryHighBandLowEndRatio);
        System.out.println(veryHighBandLowEndLum);
        System.out.println(CountColorsInBrightnessRange.countColorsAboveBrightnessThreshold(veryHighBandLowEndLum));
        double almostAsHighBand = Math.pow(21, 251.0/253);
        double almostAsHighLum = ColorNamer.getRelativeLuminanceFromContRatioWithBlack(almostAsHighBand);
        System.out.println(CountColorsInBrightnessRange.countColorsAboveBrightnessThreshold(almostAsHighLum));
    }
}
