package com.company;

public class Main {

    public static void main(String[] args) {
        int[] cyan = {0, 255, 255};
        System.out.println("Cyan has similar brightness to " + ColorNamer.nameClosestGrayscaleColor(cyan));
        int[] navy = {0, 0, 128};
        System.out.println("Navy has similar brightness to " + ColorNamer.nameClosestGrayscaleColor(navy));


    }
}
