/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fire_conv;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Rex
 */
public final class Fire {

    //THREAD
    boolean running;
    boolean paused;

    int fps;
    Thread fireThread;

    BufferedImage image;

    public Fire(Fire_Conv PRUEBA) {
        this.PRUEBA = PRUEBA;
        newThread();
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void newThread() {
        fireThread = new Thread(() -> {
            this.image = PRUEBA.viewer.getConv_bg();

            this.WIDTH = image.getWidth();
            this.HEIGHT = image.getHeight();

            this.temperature = new int[WIDTH][HEIGHT];

            this.flame_i = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

            this.image_buffer = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            this.buffer = ((DataBufferByte) flame_i.getRaster().getDataBuffer()).getData();

            while (running) {
                try {
                    if (!paused) {
                        //FIRE HERE
                        imageSparks();
                        flameEvolve();
                    }
                    try {
                        Thread.sleep(1000 / fps);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                } catch (Exception ex) {
                }
            }

            try {
                this.flame_i = ImageIO.read(new File("src\\images\\blackBG.png"));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    //FIRE
    Fire_Conv PRUEBA;

    int WIDTH;
    int HEIGHT;
    int[][] temperature;

    BufferedImage flame_i;
    byte[] buffer;
    byte[] image_buffer;

    float cooling;
    int spark_chance = 50;

    Random rand = new Random();

    public BufferedImage getFlame_i() {
        return flame_i;
    }

    public void setCooling(float cooling) {
        this.cooling = cooling;
    }

    public void setSpark_chance(int spark_chance) {
        this.spark_chance = spark_chance;
    }

    private void calcular(int[][] temperature) {

        int up;
        int left;
        int right;
        int down;

        int[][] temp = new int[temperature.length][temperature[0].length];

        for (int i = 1; i < temperature.length - 1; i++) {
            for (int j = 1; j < temperature[0].length - 1; j++) {
                up = temperature[i - 1][j];
                left = temperature[i][j - 1];
                right = temperature[i][j + 1];
                down = temperature[i + 1][j];

                int avg = (up + left + right + down) / 4;

                cooling = 0.1f;

                avg -= cooling;

                if (avg < 0) {
                    avg = 0;
                }

                temp[i][j - 1] = avg;

            }
        }

        System.arraycopy(temp, 0, temperature, 0, temp.length);

    }

    public void flameEvolve() {
        calcular(this.temperature);

        final int pixelLength = 4;
        for (int pixel = 0, row = 0, col = 0;
                pixel + 3 < this.buffer.length; pixel += pixelLength) {

            //Aqui iria la paleta de colores
            if (temperature[col][row] < 150) {
                buffer[pixel] = (byte) temperature[col][row]; // alpha
            } else {
                buffer[pixel] = (byte) 255; // alpha
            }
            
            buffer[pixel + 1] = (byte) 0; // blue
            buffer[pixel + 2] = (byte) 0; // green
            buffer[pixel + 3] = (byte) temperature[col][row]; // red

            //Usamos esto para encontrar las colummnas y lines en el array
            col++;
            if (col == this.WIDTH) {

                col = 0;
                row++;
            }
        }
    }

    public void createSparks() {

        final int pixelLength = 4;
        for (int pixel = 0, row = 0, col = 0;
                pixel + 3 < this.buffer.length; pixel += pixelLength) {

            if (row == this.HEIGHT - 2) {

                int random = rand.nextInt(100);

                if (random < spark_chance) {
                    temperature[col][row] = 255;

                }
            }

            //Usamos esto para encontrar las colummnas y lines en el array
            col++;
            if (col == this.WIDTH) {

                col = 0;
                row++;
            }
        }
    }

    public void imageSparks() {
        int pixel_value;

        final int pixelLength = 4;
        for (int pixel = 0, row = 0, col = 0;
                pixel + 3 < this.image_buffer.length; pixel += pixelLength) {

            pixel_value = (image_buffer[pixel + 1] + image_buffer[pixel + 2] + image_buffer[pixel + 3]) / 3;

            if (pixel_value >= 100) {
                int random = rand.nextInt(100);

                if (random < spark_chance) {

                    temperature[col][row] = 255;

                }
            }

            //Usamos esto para encontrar las colummnas y lines en el array
            col++;
            if (col == this.WIDTH) {

                col = 0;
                row++;
            }
        }
    }
}
