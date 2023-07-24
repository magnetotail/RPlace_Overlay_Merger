package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    private static File overlayInput = new File("D:\\dev\\java\\place-overlay\\img\\overlay_original.png");
    private static File fistInput = new File("D:\\dev\\java\\place-overlay\\img\\current.png");
//    private static File fistInput = new File("D:\\dev\\java\\place-overlay\\img\\Faust_fck_fpo_neu_pride_Herzen_erweitert.png");
    private static File imageTwo = new File("");
    private static File OUTPUT = new File("D:\\dev\\java\\place-overlay\\img\\twitch.tv-dekarldent.png");

    private static int START_X_REDDIT = 1600; // Startkoordinate X des eingefügten Bildes (Fängt bei -500 an, -5 ist daher 5/ 205 wäre 705)
    private static int START_Y_REDDIT = 652; // Startkoordinate X des eingefügten Bildes (Fängt bei -500 an, -5 ist daher 5/ 205 wäre 705)
    private static int START_X_2 = 0;
    private static int START_Y_2 = 0;
    private static int FACTOR = 3; // Anzahl Pixel im Overlay Pro r/place Pixel
    private static int IMAGE_WIDTH = 6000;
    private static int IMAGE_HEIGHT = 4500;

    public static void main(String[] args) throws IOException {
//        BufferedImage overlay = ImageIO.read(overlayInput);
        BufferedImage fist = ImageIO.read(fistInput);

        BufferedImage overlay = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = overlay.createGraphics();

        Raster fistPixels = fist.getRaster();

        int startX = START_X_REDDIT * FACTOR;
        int starty = START_Y_REDDIT * FACTOR;
        System.out.println("Breite: " + fistPixels.getWidth()+ " Höhe: " + fistPixels.getHeight());
        addImage(graphics, fistPixels, startX, starty);

        if(imageTwo.exists()) {
            startX = START_X_2 * FACTOR;
            starty = START_Y_2 * FACTOR;
            BufferedImage bITwo = ImageIO.read(imageTwo);
            graphics = bITwo.createGraphics();
            fistPixels = bITwo.getRaster();
            addImage(graphics, fistPixels, startX, starty);
        }

        ImageIO.write(overlay, "png", OUTPUT);
    }

    private static void addImage(Graphics graphics, Raster fistPixels, int startX, int starty) {
        for (int x = 0; x < fistPixels.getWidth(); x++) {
            for (int y = 0; y < fistPixels.getHeight(); y++) {
                System.out.println(x);
                System.out.println(y);
                int[] pixel = fistPixels.getPixel(x,y, (int[]) null);
                int outx = startX + (x * FACTOR) + 1;
                int outy = starty + (y * FACTOR) + 1;
                graphics.setColor(getColorFromPixel(pixel));
                System.out.println(Arrays.toString(pixel));
                graphics.drawRect(outx, outy, 0,0);
            }
        }
    }

    private static Color getColorFromPixel(int[] pixel) {
        return new Color(pixel[0], pixel[1], pixel[2], pixel[3]);
    }
}