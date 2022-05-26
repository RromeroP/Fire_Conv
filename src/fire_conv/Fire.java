/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fire_conv;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author Rex
 */
public class Fire implements Runnable {

    private final Fire_Conv fire_conv;

    BufferedImage image;

    private int[][] mapaTemperatura;
    private int[][] mapaTemperatura2;

    private final double[][] ponderationMatrix = {{0, 0.1, 0}, {0.2, 0.5, 0.8}, {2.1, 1.8, 1.5}};
    //QUE COÑO ES ESTO   
    private final double pondDivisor = 695; //QUE COÑO ES ESTO

    private final int coolingTemp = 10;

    private int sparksFromCol;
    private int sparksToCol;
    private int sparksProb;

    private int coolFromCol;
    private int coolToCol;
    private int coolProb;

    private int fps = 60;
    private int skip_ticks = 300 / fps;

    public int[][] getMapaTemperatura() {
        return mapaTemperatura;
    }
    private final boolean convolateFire = false;

    private boolean running;

    public Fire(int width, int height, Fire_Conv fire_conv) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        this.fire_conv = fire_conv;
    }

    public void setMapaTemperatura(int[][] mapaTemperatura) {
        this.mapaTemperatura = mapaTemperatura;
    }

    public void setMapaTemperatura2(int[][] mapaTemperatura2) {
        this.mapaTemperatura2 = mapaTemperatura2;
    }

    private void createCoolingPoint(int x, int y, int prob) {
        int rand = new Random().nextInt(100);

        if (prob >= rand + 1) {
            this.mapaTemperatura2[x][y] = 0;
        }
    }

    private void createCoolingPoints(double fromCol, double toCol, double fromRow, int prob) {

        for (int x = (int) fromRow; x < this.mapaTemperatura2.length; x++) {
            for (int y = (int) fromCol; y < toCol; y++) {
                createCoolingPoint(x, y, prob);
            }
        }
    }

    private void createFlameImage() {
        for (int x = 0; x < mapaTemperatura.length; x++) {
            for (int y = 0; y < mapaTemperatura[x].length; y++) {
                int temperatura = mapaTemperatura[x][y];

                if (temperatura >= 128) {
                    this.image.setRGB(x, y, new Color(255, 255, 0).getRGB());
                } else if (temperatura >= 64) {
                    this.image.setRGB(x, y, new Color(255, 215, 0).getRGB());
                } else if (temperatura >= 32) {
                    this.image.setRGB(x, y, new Color(255, 0, 0).getRGB());
                } else {
                }
                //TODO
                //this.setRGB(y, x, this.myTask.getFlamePalette().getColor(temperatura).getRGB());
            }
        }
    }

    private void createSpark(int x, int y, int prob) {
        int rand = new Random().nextInt(100);

        if (prob >= rand + 1) {
            this.mapaTemperatura2[x][y] = 255;
        }
    }

    private void createSparks(int sparksFromCol, int sparksToCol, float fromRow, int prob) {

        for (int x = (int) fromRow; x < this.mapaTemperatura2.length; x++) {
            for (int y = sparksFromCol; y < sparksToCol; y++) {
                createSpark(x, y, prob);
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private void flameEvolve() {
        if (convolateFire) {
            this.searchSparksPositions();
        } else {
            this.createSparks(this.sparksFromCol, this.sparksToCol, this.mapaTemperatura2.length - 1, this.sparksProb);
        }
        this.createCoolingPoints(this.coolFromCol, this.coolToCol, this.mapaTemperatura2.length - 1, this.coolProb);
        for (int i = this.mapaTemperatura.length - 2; i > 0; i--) {
            for (int j = this.mapaTemperatura[i].length - 2; j > 0; j--) {
                int temperatura = this.temperatureEvolve(i, j);
                if (temperatura > 255) {
                    temperatura = 255;
                } else if (temperatura < 0) {
                    temperatura = 0;
                }
                this.mapaTemperatura2[i][j] = temperatura;
            }
        }
        this.createFlameImage();

        //COPIA A LA NUEVA TEMPERATURA
        this.mapaTemperatura = this.mapaTemperatura2;
    }

    private int temperatureEvolve(int y, int x) {
        double sumTemperatura = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                sumTemperatura += this.mapaTemperatura[y + (i)][x + (j)] * this.ponderationMatrix[i + 1][j + 1];
            }
        }
        return (int) (((sumTemperatura) / (pondDivisor / 100)) - coolingTemp / 10);
    }

    private void searchSparksPositions() {
        for (int x = this.fire_conv.getViewer().getConv_bg().getHeight() - 2; x > 0; x--) {
            for (int y = this.fire_conv.getViewer().getConv_bg().getWidth() - 2; y > 0; y--) {
                int pixelColor = this.fire_conv.getViewer().getConv_bg().getRGB(y, x);
                int lum = (77 * ((pixelColor >> 16) & 255) + 150 * ((pixelColor >> 8) & 255) + 29 * ((pixelColor) & 255)) >> 8;
                if (lum > 20) {
                    this.createSpark(x, y, this.sparksProb);
                    this.createCoolingPoint(x, y, this.coolProb);
                }
            }
        }
    }

    public void setRate(int rate) {
        this.fps = rate;
        this.skip_ticks = 300 / rate;
    }

    @Override
    public void run() {
        System.out.println("a1");
        if (this.mapaTemperatura != null) {
            System.out.println("a2");
            this.flameEvolve();
        }
        try {
            Thread.sleep(skip_ticks);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
