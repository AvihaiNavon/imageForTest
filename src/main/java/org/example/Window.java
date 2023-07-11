package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.net.URL;
import java.util.Random;
import java.util.stream.IntStream;

public class Window extends JFrame {
    private float brightness;

    private BufferedImage originalImage;
    private BufferedImage processedImage;
    JPanel buttonPanel;
    JLabel label;


    public void Window1() {
        brightness = 1.0f;

        try {

            URL jetImage = getClass().getClassLoader().getResource("JET.jpg"); //לשמור את התמונה במשתנה, לשמור את התמונה בתקיית resources
            if (jetImage != null) {
                BufferedImage bufferedImage = ImageIO.read(jetImage);// מחלקה שמאפשרת לגשת למידע של הפיקלסלים כמו לרשת (גריד)
                //BufferedImage bufferedImage = miror(ImageIO.read(jetImage));// פונקציה שהופכת את התמונה במקבליות
                // BufferedImage bufferedImage = shuffle(ImageIO.read(jetImage));// פונקציה שעושה ערבוב בצבעים
                //BufferedImage bufferedImage = miror(shuffle(ImageIO.read(jetImage))); // חיבור שני הפונקציות הקודמות
                //BufferedImage bufferedImage =  blackToRed(ImageIO.read(jetImage)); //צובע את השחור לאדום
                //BufferedImage bufferedImage =  negative(ImageIO.read(jetImage)); // נגטיב של תמונה
                //BufferedImage bufferedImage =  BlackAndWhite(ImageIO.read(jetImage));// תמונה שחור לבן
                //BufferedImage bufferedImage =  borders(ImageIO.read(jetImage)); //יצירת גבולות
                //BufferedImage bufferedImage = gradient();
                //BufferedImage bufferedImage =  reduceBrightness(ImageIO.read(jetImage));



                this.setSize(bufferedImage.getWidth(), bufferedImage.getHeight());// קביעת גודל החלון בהתאם לגודל התמונה
                JLabel label = new JLabel(new ImageIcon(bufferedImage));// הכנסת התמונה לליבול
                this.add(label);// הוספת הליבול של התמונה
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int button = e.getButton();

                        if (button == MouseEvent.BUTTON1) { // מקש שמאל
                            // הפחת את הבהירות
                            brightness -= 0.1f;
                        } else if (button == MouseEvent.BUTTON3) { // מקש ימין
                            // הוסף לבהירות
                            brightness += 0.1f;
                        }

                        // תחדש את התמונה עם הבהירות החדשה
                        BufferedImage adjustedImage = adjustBrightness(bufferedImage, brightness);

                        // עדכן את התמונה בתווית
                        label.setIcon(new ImageIcon(adjustedImage));
                    }
                });


                // getNumberColor(bufferedImage,bufferedImage.getWidth()/2,bufferedImage.getHeight()/2);// השגת מרכיבי צבע תמונה לפי מיקום המרכז
                //whereWhite(bufferedImage);// מוצא את המקומים שקיים הצבע הלבן *מוחלט* בלבד
                //whereRedish(bufferedImage);//  מוצא את המקומים שקיים *הגוון* אדום
                //whereBlackish(bufferedImage);// מוצא את המקומים שקיים *הגוון* שחור
            } else {
                System.out.println("Cannot find!");// אם לא קיימת תמונה
            }

            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.setVisible(true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public Window() {
        try {

            URL jetImage = getClass().getClassLoader().getResource("JET.jpg");
            if (jetImage != null) {
                originalImage = ImageIO.read(jetImage);
                processedImage = originalImage;


                this.setSize(originalImage.getWidth(), originalImage.getHeight());
                 label = new JLabel(new ImageIcon(processedImage));
                this.add(label);
                buttonPanel = new JPanel();
                addButton();

            } else {
                System.out.println("Cannot find image!");
            }

            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.setVisible(true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void addButton(){
        JButton mirrorButton = new JButton("Mirror");
        mirrorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processedImage = miror(originalImage);
                updateImage(processedImage);
            }
        });
        buttonPanel.add(mirrorButton);

        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processedImage = shuffle(originalImage);
                updateImage(processedImage);
            }
        });
        buttonPanel.add(shuffleButton);

        JButton blackToRedButton = new JButton("Black to Red");
        blackToRedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processedImage = blackToRed(originalImage);
                updateImage(processedImage);
            }
        });
        buttonPanel.add(blackToRedButton);

        JButton negativeButton = new JButton("Negative");
        negativeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processedImage = negative(originalImage);
                updateImage(processedImage);
            }
        });
        buttonPanel.add(negativeButton);

        JButton blackAndWhiteButton = new JButton("Black and White");
        blackAndWhiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processedImage = BlackAndWhite(originalImage);
                updateImage(processedImage);
            }
        });
        buttonPanel.add(blackAndWhiteButton);

        JButton bordersButton = new JButton("Borders");
        bordersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processedImage = borders(originalImage);
                updateImage(processedImage);
            }
        });
        buttonPanel.add(bordersButton);

        JButton gradientButton = new JButton("Gradient");
        gradientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processedImage = gradient();
                updateImage(processedImage);
            }
        });
        buttonPanel.add(gradientButton);

        JButton reduceBrightnessButton = new JButton("Reduce Brightness");
        reduceBrightnessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processedImage = reduceBrightness(originalImage);
                updateImage(processedImage);
            }
        });
        buttonPanel.add(reduceBrightnessButton);
        this.add(buttonPanel, BorderLayout.SOUTH);


    }

    private void updateImage(BufferedImage image) {
        label.setVisible(false);
        label = new JLabel(new ImageIcon(image));
        this.getContentPane().removeAll();
        this.getContentPane().add(label);
        label.setVisible(true);
        this.revalidate();
        this.addButton();
        this.repaint();
    }

    private void getNumberColor(BufferedImage bufferedImage, int x, int y) {
        int color = bufferedImage.getRGB(x, y);//הצבע שנימצא בפיקסל מסויים
        Color colorObject = new Color(color);
        System.out.println("RED: " + colorObject.getRed());
        System.out.println("GREEN: " + colorObject.getGreen());
        System.out.println("BLUE: " + colorObject.getBlue());
    }

    private void whereWhite(BufferedImage bufferedImage) {
        IntStream.range(0, bufferedImage.getWidth()).forEach(x -> { //עובר על כל העמודות
            IntStream.range(0, bufferedImage.getHeight()).forEach(y -> { //עובר על כל השורות
                int color = bufferedImage.getRGB(x, y);
                Color colorObject = new Color(color);
                if (colorObject.getRed() == 255 && colorObject.getBlue() == 255 && colorObject.getGreen() == 255) {
                    System.out.println("X = " + x + " Y = " + y);
                }
            });
        });
    }

    private void whereRedish(BufferedImage bufferedImage) {
        IntStream.range(0, bufferedImage.getWidth()).forEach(x -> { //עובר על כל העמודות
            IntStream.range(0, bufferedImage.getHeight()).forEach(y -> { //עובר על כל השורות
                int color = bufferedImage.getRGB(x, y);
                Color colorObject = new Color(color);
                if (colorObject.getRed() > 180 && colorObject.getBlue() < 30 && colorObject.getGreen() < 30) {
                    System.out.printf("(%d, %d)\n", x, y);
                }
            });
        });
    }

    private void whereBlackish(BufferedImage bufferedImage) {
        IntStream.range(0, bufferedImage.getWidth()).forEach(x -> { //עובר על כל העמודות
            IntStream.range(0, bufferedImage.getHeight()).forEach(y -> { //עובר על כל השורות
                int color = bufferedImage.getRGB(x, y);
                Color colorObject = new Color(color);
                if (colorObject.getRed() + colorObject.getBlue() + colorObject.getGreen() < 20) {
                    System.out.printf("(%d, %d)\n", x, y);
                }
            });
        });
    }

    private BufferedImage miror(BufferedImage original) {
        BufferedImage process = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB); // יוצר גרף חדש עם אותו גודל כמו המקורי
        //BufferedImage.TYPE_INT_RGB מידות שקיפות וצבעים
        for (int i = 0; i < original.getHeight() - 1; i++) { //עובר על שורות
            for (int j = 0; j < original.getWidth() - 1; j++) { // עובר על עמודות
                process.setRGB(i, j, original.getRGB(original.getWidth() - i - 1, j));// מכניס בגרף חדש את הנקודה מהגרף המקורי במקבליות
            }
        }
        return process;


    }

    private BufferedImage shuffle(BufferedImage original) {
        BufferedImage process = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB); // יוצר גרף חדש עם אותו גודל כמו המקורי
        for (int i = 0; i < original.getHeight() - 1; i++) { //עובר על שורות
            for (int j = 0; j < original.getWidth() - 1; j++) { // עובר על עמודות
                Color color = new Color(original.getRGB(i, j));// לקחת את הצבע מהנקודה הנוכחית ולהכניס למשתנה צבע
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                Color newColor = new Color(green, blue, red);// לבנות משתנה צבע חדש ולערבב בין המקומות במקום RGB מבצע GBR
                process.setRGB(i, j, newColor.getRGB());
            }
        }
        return process;
    }

    private BufferedImage blackToRed(BufferedImage original) {
        BufferedImage process = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB); // יוצר גרף חדש עם אותו גודל כמו המקורי
        for (int i = 0; i < original.getHeight() - 1; i++) { //עובר על שורות
            for (int j = 0; j < original.getWidth() - 1; j++) { // עובר על עמודות
                Color color = new Color(original.getRGB(i, j));// לקחת את הצבע מהנקודה הנוכחית ולהכניס למשתנה צבע
                if (color.getRed() < 90 && color.getBlue() < 90 && color.getGreen() < 90) {
                    Color newColor = new Color(200, 20, 20);
                    process.setRGB(i, j, newColor.getRGB());
                } else {
                    process.setRGB(i, j, color.getRGB());
                }
            }

        }
        return process;

    }

    private BufferedImage negative(BufferedImage original) {
        BufferedImage process = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB); // יוצר גרף חדש עם אותו גודל כמו המקורי
        for (int i = 0; i < original.getHeight() - 1; i++) { //עובר על שורות
            for (int j = 0; j < original.getWidth() - 1; j++) { // עובר על עמודות
                Color color = new Color(original.getRGB(i, j));// לקחת את הצבע מהנקודה הנוכחית ולהכניס למשתנה צבע
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                Color newColor = new Color(255 - red, 255 - green, 255 - blue);
                process.setRGB(i, j, newColor.getRGB());
            }
        }
        return process;
    }

    private BufferedImage BlackAndWhite(BufferedImage original) {
        // (128,128,128) גווני אפור
        BufferedImage process = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB); // יוצר גרף חדש עם אותו גודל כמו המקורי
        for (int i = 0; i < original.getHeight() - 1; i++) { //עובר על שורות
            for (int j = 0; j < original.getWidth() - 1; j++) { // עובר על עמודות
                Color color = new Color(original.getRGB(i, j));// לקחת את הצבע מהנקודה הנוכחית ולהכניס למשתנה צבע
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int average = (red + green + blue) / 3; // לוקח את ההמוצע של הצבעים ונותן גוון אפור ובכך יוצר את האפקט של שחור לבן לפי חוזק
                Color newColor = new Color(average, average, average);
                process.setRGB(i, j, newColor.getRGB());
            }
        }
        return process;
    }

    private BufferedImage borders(BufferedImage original) {
        BufferedImage process = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 1; x < original.getHeight() - 2; x++) { //עובר על שורות, מתחיל מ 1 כי רוצה לבדוק שכנים
            for (int y = 1; y < original.getWidth() - 2; y++) { // עובר על עמודות, מתחיל מ 1 כי רוצה לבדוק שכנים
                Color colorPixel = new Color(original.getRGB(x, y));// לקחת את הצבע מהנקודה הנוכחית ולהכניס למשתנה צבע
                Color southPixel = new Color(original.getRGB(x, y + 1));// הפיקסל מעליו
                Color nortPixel = new Color(original.getRGB(x, y - 1));//הפיקסל מתחתיו
                Color eastPixel = new Color(original.getRGB(x - 1, y));// הפיקסל משמאלו
                Color westPixel = new Color(original.getRGB(x + 1, y));// הפיקסל מימינו
                Color color = null;
                if (isDifferent(colorPixel, southPixel) |
                        isDifferent(colorPixel, nortPixel) ||
                        isDifferent(colorPixel, eastPixel) ||
                        isDifferent(colorPixel, westPixel)) { // בודק אם קעעם פיקסל צמוד ששונה בצבעו
                    color = new Color(0, 0, 0); // לצבוע לשחור
                } else {
                    color = new Color(255, 255, 255); // לצבוע ללבן
                }
                process.setRGB(x, y, color.getRGB());
            }
        }
        return process;
    }

    private boolean isDifferent(Color color1, Color color2) {
        boolean different = false;
        if (Math.abs(color1.getRed() - color2.getRed()) > 30) { // בודק אם שונה בגוון צבעים
            different = true;
        } else if (Math.abs(color1.getGreen() - color2.getGreen()) > 30) { // בודק אם שונה בגוון צבעים
            different = true;
        } else if (Math.abs(color1.getBlue() - color2.getBlue()) > 30) { // בודק אם שונה בגוון צבעים
            different = true;
        }
//        int rgb1=color1.getRed()+color1.getGreen()+color1.getBlue();
//        int rgb2=color2.getRed()+color1.getGreen()+color1.getBlue();
        return different;

    } //פונקציית עזר ל borders

    private BufferedImage gradient() {
        Random random=new Random();
        BufferedImage process = new BufferedImage(750, 750, BufferedImage.TYPE_INT_RGB); // יוצר מסך שחור בגודל מוגדר
        int minRed=random.nextInt(50);
        int maxRed=minRed+random.nextInt(200);
        int minGreen=random.nextInt(50);
        int maxGreen=minGreen+random.nextInt(200);
        int redDiff=maxRed-minRed;
        int greenDiff=maxGreen-minGreen;

        int chunksRed=process.getWidth()/redDiff;
        int chunksGreen=process.getWidth()/greenDiff;

        IntStream.range(0, process.getWidth()).forEach(x -> { //עובר על כל העמודות
            IntStream.range(0, process.getHeight()).forEach(y -> { //עובר על כל השורות
                //process.setRGB(x, y, new Color(x / 2, y / 2, (x / 2 + y / 2) / 2).getRGB());
                // יוצר הדרגתיות בציר הx של הצבע האדום ובציר ה y של הצבע הירוק והצבע הכחול שילוב של שניהם באלכסון צובע בהדרגתיות
                int currentChunkRed=x/(chunksRed+1);
                int currentChunkGreen=y/(chunksGreen+1);
                Color color=new Color(minRed+currentChunkRed,minGreen+currentChunkGreen,(minRed+currentChunkRed)+(minGreen+currentChunkGreen)/2);
                process.setRGB(x, y,color.getRGB() );
            });


        });
        return process;

    } // עבודת כיתה הדרגתיות צבעים
    public  BufferedImage reduceBrightness(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));

                // קבלת ערכי הצבעים הרגילים (RGB)
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                // מורידים את ערכי הצבעים לחצי (50%)
                int newRed = red / 2;
                int newGreen = green / 2;
                int newBlue = blue / 2;

                Color newColor = new Color(newRed, newGreen, newBlue);
                result.setRGB(x, y, newColor.getRGB());
            }
        }

        return result;
    } // הורדת בהירות בחצי
    private BufferedImage adjustBrightness(BufferedImage image, float brightness) {
        RescaleOp rescaleOp = new RescaleOp(brightness, 0, null);
        BufferedImage adjustedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        rescaleOp.filter(image, adjustedImage);
        return adjustedImage;
    }



}