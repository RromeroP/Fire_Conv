/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fire_conv;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Rubén
 */
public class Viewer extends Canvas {

    BufferedImage conv_bg;

    BufferedImage bg;
    double[][] filter;

    Thread viewerThread;

    boolean running = false;

    public Viewer() {
        this.setBackground(Color.BLACK);

        viewerThread = new Thread(() -> {
            while (running) {
                try {
                    setBackground(bg, filter);

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                } catch (Exception ex) {
                }
            }
        });
    }

    public BufferedImage getConv_bg() {
        return conv_bg;
    }

    public void setBackground(BufferedImage img_bg, double[][] img_filter) {
        this.bg = img_bg;
        this.filter = img_filter;

        if (!running) {
            this.running = true;
            viewerThread.start();
        }

        if (bg != null) {
            //Main Image
            this.getGraphics().drawImage(bg, 0, (int) (this.getHeight() * 0.33), this.getWidth(), (int) (this.getHeight() * 0.67), null);
            //Original Image
            this.getGraphics().drawImage(bg, 0, 0, (int) (this.getWidth() * 0.33), (int) (this.getHeight() * 0.33), null);
            //Conv Image
            conv_bg = new Image_Conv(bg, filter).getNew_img();
            this.getGraphics().drawImage(conv_bg, (int) (this.getWidth() * 0.33), 0, (int) (this.getWidth() * 0.33), (int) (this.getHeight() * 0.33), null);
            //Fire

            this.getGraphics().drawImage(conv_bg, (int) (this.getWidth() * 0.67), 0, (int) (this.getWidth() * 0.33), (int) (this.getHeight() * 0.33), null);
        }
    }

}
