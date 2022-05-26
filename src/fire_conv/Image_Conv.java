/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fire_conv;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam
 */
public class Image_Conv {

    /**
     * @param args the command line arguments
     */
    BufferedImage img;
    BufferedImage new_img;

    double[][] filter;

    public Image_Conv(BufferedImage img, double[][] new_filter) {
        this.filter = new_filter;
        double[][][] image = transformImageToArray(img);
        double[][] convolvedPixels = applyConvolution(img.getWidth(),
                img.getHeight(), image, filter);

        try {
            this.new_img = createImageFromConvolutionMatrix(img, convolvedPixels);
        } catch (IOException ex) {
            Logger.getLogger(Image_Conv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setFilter(double[][] filter) {
        this.filter = filter;
    }

    public BufferedImage getNew_img() {
        return new_img;
    }

    private double[][][] transformImageToArray(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        double[][][] image = new double[3][height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = new Color(bufferedImage.getRGB(j, i));
                image[0][i][j] = color.getRed();
                image[1][i][j] = color.getGreen();
                image[2][i][j] = color.getBlue();
            }
        }

        return image;
    }

    private double[][] applyConvolution(int width, int height, double[][][] image, double[][] filter) {
        Convolution convolution = new Convolution();

        double[][] redConv = convolution.convolutionType2(image[0], height, width, filter, 3, 3, 1);
        double[][] greenConv = convolution.convolutionType2(image[1], height, width, filter, 3, 3, 1);
        double[][] blueConv = convolution.convolutionType2(image[2], height, width, filter, 3, 3, 1);
        double[][] finalConv = new double[redConv.length][redConv[0].length];

        for (int i = 0; i < redConv.length; i++) {
            for (int j = 0; j < redConv[i].length; j++) {
                finalConv[i][j] = redConv[i][j] + greenConv[i][j] + blueConv[i][j];
            }
        }

        return finalConv;
    }

    private BufferedImage createImageFromConvolutionMatrix(BufferedImage originalImage, double[][] imageRGB) throws IOException {
        BufferedImage writeBackImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < imageRGB.length; i++) {
            for (int j = 0; j < imageRGB[i].length; j++) {
                Color color = new Color(fixOutOfRangeRGBValues(imageRGB[i][j]),
                        fixOutOfRangeRGBValues(imageRGB[i][j]),
                        fixOutOfRangeRGBValues(imageRGB[i][j]));
                writeBackImage.setRGB(j, i, color.getRGB());
            }
        }
        return writeBackImage;
    }

    private int fixOutOfRangeRGBValues(double value) {
        if (value < 0.0) {
            value = -value;
        }
        if (value > 255) {
            return 255;
        } else {
            return (int) value;
        }
    }

}
