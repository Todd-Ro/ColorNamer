package com.company;

public class CountColorsInBrightnessRange {

    static double luminanceOfRed255Green255Blue11() {
        return 0.2126 + 0.7152 + 0.0722*ColorNamer.elementLuminance(11);
    }

    static double RsRGBFromElementLuminanceWhereIntAbove10(double elementLuminance) {
        double unPowered = Math.pow(elementLuminance,(1/2.4));
        double minRsThisColor = unPowered*1.055 - .055;
        return minRsThisColor;
    }

    public static int countColorsAboveBrightnessThreshold(double bound) {
        /*input a relative luminance amount and this will output the total number of sRGB colors with a luminance of at
        least that amount.
         */
        int count = 0;

        if (bound >= luminanceOfRed255Green255Blue11()) {
            //If the bound is this high, no color value can be lower than 11, even if the other two are maxed.
            double maxRedBlueSumLuminance = 0.2126 + 0.0722; // Equals .2848, which is 1 - .7152
            double minGreenContrib = bound - maxRedBlueSumLuminance;
            double minGreenElementLuminance = minGreenContrib / .7152;
            // What is RsGreen when ((RsGreen + .055)/1.055)^2.4 == minGreenElementLuminance?
            double minRsGreen = RsRGBFromElementLuminanceWhereIntAbove10(minGreenElementLuminance);
            long minGreenInt = Math.round(Math.ceil(minRsGreen*255));
            Long i = minGreenInt;
            while (i <= 255) {
                //Evaluate what happens when the green value from 0 to 255 is equal to i
                double greenElementLuminance = .7152*ColorNamer.elementLuminance(i.intValue());
                double minRedContrib = bound - greenElementLuminance - .0722;
                // Red must contribute at least minRedContrib luminance for the luminance to be above the bound
                double minRedElementLuminance = minRedContrib / .2126;
                double minRsRed = RsRGBFromElementLuminanceWhereIntAbove10(minRedElementLuminance);
                long minRedInt = Math.round(Math.ceil(minRsRed*255));
                Long j = minRedInt;
                while (j <= 255) {
                    double redElementLuminance = .2126*ColorNamer.elementLuminance(j.intValue());
                    double minBlueContrib = bound - greenElementLuminance - redElementLuminance;
                    double minBlueElementLuminance = minBlueContrib / .0722;
                    double minRsBlue = RsRGBFromElementLuminanceWhereIntAbove10(minBlueElementLuminance);
                    long minBlueInt = Math.round(Math.ceil(minRsBlue*255));
                    count += (255 - minBlueInt + 1);
                    j++;
                }

                i++;
            }
        }
        else {
            System.out.println("countColorsAboveBrightnessThreshold is not finished for bounds this low");
        }

        return count;
    }
}
