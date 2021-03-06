package fire_conv;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Rubén
 */
public final class Fire {

    boolean running;
    boolean paused;

    int fps;
    Thread fireThread;

    BufferedImage image;
    BufferedImage default_img;

    ;
    public Fire(Fire_Conv PRUEBA) {
        this.PRUEBA = PRUEBA;
        try {
            this.default_img = ImageIO.read(new File("src\\images\\blackBG.png"));
        } catch (IOException ex) {
            System.out.println(ex);
        }
        this.palette = new Color_Palette();
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

            this.image_buffer = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

            this.flame_i = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
            this.buffer = ((DataBufferByte) flame_i.getRaster().getDataBuffer()).getData();

            while (running) {
                try {
                    if (!paused) {
                        //FIRE HERE
                        if (apply_conv) {
                            this.image = PRUEBA.viewer.getConv_bg();
                            imageSparks();
                        } else {
                            createSparks();
                        }
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
            System.out.println("a");
            this.flame_i = this.default_img;
        });

    }
    //FIRE
    Fire_Conv PRUEBA;
    Color_Palette palette;

    int WIDTH;
    int HEIGHT;
    int[][] temperature;

    BufferedImage flame_i;
    byte[] buffer;
    byte[] image_buffer;

    float cooling = 0.1f;
    int spark_chance = 30;

    Random rand = new Random();

    int tolerance = 100;

    boolean apply_conv = false;

    public void setApply_conv(boolean apply_conv) {
        if (this.flame_i != null) {

            this.flame_i = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
            this.apply_conv = apply_conv;
        } else {
            this.PRUEBA.controlPanel.apply_conv.setSelected(false);
            JOptionPane.showMessageDialog(null, "Load an image first.");
        }
    }

    public BufferedImage getFlame_i() {
        return flame_i;
    }

    public void setCooling(double cooling) {

        this.cooling = (float) cooling;
    }

    public void setSpark_chance(int spark_chance) {
        this.spark_chance = spark_chance;
    }

    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
    }

    public void resetTemperature() {
        this.temperature = new int[WIDTH][HEIGHT];
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

        for (int x = 0; x < temperature.length; x++) {
            for (int y = 0; y < temperature[0].length; y++) {
                flame_i.setRGB(x, y, palette.getColor(temperature[x][y]).getRGB());

            }
        }
    }

    public void createSparks() {

        for (int x = 0; x < temperature.length; x++) {
            for (int y = 0; y < temperature[0].length; y++) {

                if (y == this.HEIGHT - 2) {
                    int random = rand.nextInt(100);

                    if (random <= spark_chance) {

                        temperature[x][y] = 255;

                    }
                }
            }
        }
    }

    public void imageSparks() {
        Color pixel_color;

        for (int x = 0; x < temperature.length; x++) {
            for (int y = 0; y < temperature[0].length; y++) {

                pixel_color = new Color(image.getRGB(x, y), true);

                if (pixel_color.getRed() + pixel_color.getGreen() + pixel_color.getBlue() / 3 >= tolerance) {

                    int random = rand.nextInt(100);

                    if (random <= spark_chance) {

                        temperature[x][y] = 255;

                    }
                }
            }
        }
    }
}
