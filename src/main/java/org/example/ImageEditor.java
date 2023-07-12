package org.example;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.image.RescaleOp;
import java.io.File;
import java.util.Random;

public class ImageEditor extends JFrame {
    private BufferedImage originalImage;
    private BufferedImage editedImage;
    private JLabel imageLabel;
    private JSlider brightnessSlider;
    private JButton rotateButton;
    private JButton changeColorButton;

    public ImageEditor() {
        super("Image Editor");

        // Load the original image
        try {
            originalImage = ImageIO.read(new File("C:\\Users\\אביחי\\Downloads\\imageForTest\\src\\main\\resources\\im.jpg"));
            editedImage = originalImage;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create GUI components
        imageLabel = new JLabel(new ImageIcon(editedImage));
        brightnessSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        rotateButton = new JButton("Rotate");
        changeColorButton = new JButton("Change Color");

        // Set up event listeners
        brightnessSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int brightnessValue = brightnessSlider.getValue();
                editedImage = changeBrightness(originalImage, brightnessValue);
                imageLabel.setIcon(new ImageIcon(editedImage));
            }
        });

        rotateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editedImage = rotateImage(editedImage, 90);
                imageLabel.setIcon(new ImageIcon(editedImage));
            }
        });

        changeColorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editedImage = changeRandomColor(editedImage);
                imageLabel.setIcon(new ImageIcon(editedImage));
            }
        });

        // Set up the layout
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("Brightness:"));
        controlPanel.add(brightnessSlider);
        controlPanel.add(rotateButton);
        controlPanel.add(changeColorButton);

        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private BufferedImage rotateImage(BufferedImage image, double angle) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotatedImage = new BufferedImage(height, width, image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.rotate(Math.toRadians(angle), height / 2, height / 2);
        g2d.drawImage(image, (height - width) / 2, (height - width) / 2, null);
        g2d.dispose();

        return rotatedImage;
    }

    private BufferedImage changeBrightness(BufferedImage image, int brightnessValue) {
        RescaleOp rescaleOp = new RescaleOp(1.0f, brightnessValue, null);
        BufferedImage brightenedImage = rescaleOp.filter(image, null);

        return brightenedImage;
    }

    private BufferedImage changeRandomColor(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        BufferedImage coloredImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = coloredImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.setColor(new Color(red, green, blue));
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();

        return coloredImage;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ImageEditor();
            }
        });
    }
}
