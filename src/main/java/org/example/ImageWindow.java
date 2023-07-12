package org.example;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageWindow extends JFrame {
    private String imagePath;

    public ImageWindow(String imagePath) {
        this.imagePath = imagePath;
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            ImageIcon icon = new ImageIcon(image);

            JLabel label = new JLabel(icon);
            getContentPane().add(label);

            setTitle("Image Window");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setLocationRelativeTo(null);

            int[] mostCommonColors = getMostCommonColors(image);
            int mostCommonColor = mostCommonColors[0];
            int secondMostCommonColor = mostCommonColors[1];
            int red1 = (mostCommonColor >> 16) & 0xFF;
            int green1 = (mostCommonColor >> 8) & 0xFF;
            int blue1 = mostCommonColor & 0xFF;
            int red2 = (secondMostCommonColor >> 16) & 0xFF;
            int green2 = (secondMostCommonColor >> 8) & 0xFF;
            int blue2 = secondMostCommonColor & 0xFF;

            System.out.println("Most common color (RGB): " + red1 + ", " + green1 + ", " + blue1);
            System.out.println("Second most common color (RGB): " + red2 + ", " + green2 + ", " + blue2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[] getMostCommonColors(BufferedImage image) {
        int[] colorCount = new int[16777216]; // 256^3 possible RGB values

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;
                int rgb = (red << 16) | (green << 8) | blue;

                colorCount[rgb]++;
            }
        }

        int[] mostCommonColors = new int[2];
        int maxCount1 = 0;
        int maxCount2 = 0;

        for (int i = 0; i < colorCount.length; i++) {
            if (colorCount[i] > maxCount1) {
                mostCommonColors[1] = mostCommonColors[0];
                maxCount2 = maxCount1;

                mostCommonColors[0] = i;
                maxCount1 = colorCount[i];
            } else if (colorCount[i] > maxCount2) {
                mostCommonColors[1] = i;
                maxCount2 = colorCount[i];
            }
        }

        int mostCommonColor = mostCommonColors[0];
        int mostCommonColorCount = maxCount1;
        int secondMostCommonColor = mostCommonColors[1];
        int secondMostCommonColorCount = maxCount2;

        System.out.println("Most common color (RGB): " + getRGBString(mostCommonColor));
        System.out.println("Most common color count: " + mostCommonColorCount);
        System.out.println("Second most common color (RGB): " + getRGBString(secondMostCommonColor));
        System.out.println("Second most common color count: " + secondMostCommonColorCount);

        return mostCommonColors;
    }

    private String getRGBString(int rgb) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        return red + ", " + green + ", " + blue;
    }

    public static void main(String[] args) {
        String imagePath = "C:\\Users\\אביחי\\Downloads\\imageForTest\\src\\main\\resources\\im.jpg";
        SwingUtilities.invokeLater(() -> {
            ImageWindow window = new ImageWindow(imagePath);
            window.setVisible(true);
        });
    }
}