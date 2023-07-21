package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    private static File overlayInput = new File("HIER URSPRUNGS OVERLAY PFAD EINGEBEN");
    private static File fistInput = new File("PFAD DES BILDES DAS HINZUGEFÜGT WERDEN SOLL");
    private static File OUTPUT = new File("PFAD DES AUSGANGSBILDES MIT .png");

    private static int START_X_REDDIT = 1101; // Startkoordinate X des eingefügten Bildes (Fängt bei -500 an, -5 ist daher 5/ 205 wäre 705)
    private static int START_Y_REDDIT = 153; // Startkoordinate X des eingefügten Bildes (Fängt bei -500 an, -5 ist daher 5/ 205 wäre 705)
    private static int FACTOR = 3; // Anzahl Pixel im Overlay Pro r/place Pixel

    public static void main(String[] args) throws IOException {
        BufferedImage overlay = ImageIO.read(overlayInput);
        BufferedImage fist = ImageIO.read(fistInput);
        Graphics graphics = overlay.createGraphics();

        Raster fistPixels = fist.getRaster();

        int startX = START_X_REDDIT * FACTOR;
        int starty = START_Y_REDDIT * FACTOR;
        for (int x = 0; x < fistPixels.getWidth(); x++) {
            for (int y = 0; y < fistPixels.getHeight(); y++) {
                int[] pixel = new int[3];
                System.out.println(x);
                System.out.println(y);
                pixel = fistPixels.getPixel(x,y, pixel);
                int outx = startX + (x * FACTOR) + 1;
                int outy = starty + (y * FACTOR) + 1;
                graphics.setColor(getColorFromPixel(pixel));
                System.out.println(Arrays.toString(pixel));
                graphics.drawRect(outx, outy, 0, 0);
            }
        }
        ImageIO.write(overlay, "png", OUTPUT);
    }

    private static Color getColorFromPixel(int[] pixel) {
        return new Color(pixel[0], pixel[1], pixel[2]);
    }
}