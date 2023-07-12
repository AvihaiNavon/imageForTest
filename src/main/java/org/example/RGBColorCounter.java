package org.example;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
//מה הצבע הכי חזק
public class RGBColorCounter {
    public static void main(String[] args) {
        File imageFile = new File("C:\\Users\\אביחי\\Downloads\\imageForTest\\src\\main\\resources\\im.jpg"); // שנה את הנתיב לנתיב הנכון של התמונה
        BufferedImage image = null;

        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (image != null) {
            Map<Integer, Integer> colorCountMap = getRGBColorCounts(image);
            int mostCommonColor = getMostCommonColor(colorCountMap);
            int secondMostCommonColor = getSecondMostCommonColor(colorCountMap);

            System.out.println("Most common color RGB value: " + getRGBString(mostCommonColor));
            System.out.println("Second most common color RGB value: " + getRGBString(secondMostCommonColor));
        }
    }

    public static Map<Integer, Integer> getRGBColorCounts(BufferedImage image) {
        Map<Integer, Integer> colorCountMap = new HashMap<>();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                colorCountMap.put(rgb, colorCountMap.getOrDefault(rgb, 0) + 1);
            }
        }

        return colorCountMap;
    }

    public static int getMostCommonColor(Map<Integer, Integer> colorCountMap) {
        int mostCommonColor = 0;
        int maxCount = 0;

        for (Map.Entry<Integer, Integer> entry : colorCountMap.entrySet()) {
            int count = entry.getValue();

            if (count > maxCount) {
                maxCount = count;
                mostCommonColor = entry.getKey();
            }
        }

        return mostCommonColor;
    }

    public static int getSecondMostCommonColor(Map<Integer, Integer> colorCountMap) {
        int mostCommonColor = getMostCommonColor(colorCountMap);
        int secondMostCommonColor = 0;
        int maxCount = 0;

        for (Map.Entry<Integer, Integer> entry : colorCountMap.entrySet()) {
            int count = entry.getValue();
            int color = entry.getKey();

            if (count > maxCount && color != mostCommonColor) {
                maxCount = count;
                secondMostCommonColor = color;
            }
        }

        return secondMostCommonColor;
    }

    public static String getRGBString(int rgb) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        return red + "-" + green + "-"+blue;
}
}