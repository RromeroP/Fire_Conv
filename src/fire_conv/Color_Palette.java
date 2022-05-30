/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fire_conv;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Rex
 */
public class Color_Palette {

    ArrayList<Color> palette = new ArrayList<>();

    Color[] real = {
        new Color(0, 0, 0, 0),
        new Color(180, 40, 0, 150),
        new Color(255, 130, 0, 175),
        new Color(250, 240, 20, 200),
        new Color(255, 255, 255, 255)
    };

    Color[] green = {
        new Color(0, 0, 0, 0),
        new Color(0, 40, 60, 150),
        new Color(30, 130, 80, 175),
        new Color(60, 240, 170, 200),
        new Color(255, 255, 255, 255)
    };

    Color[] blue = {
        new Color(0, 0, 0, 0),
        new Color(0, 30, 80, 150),
        new Color(30, 80, 130, 175),
        new Color(60, 170, 240, 200),
        new Color(255, 255, 255, 255)
    };

    Color[] purple = {
        new Color(0, 0, 0, 0),
        new Color(153, 51, 255, 150),
        new Color(178, 102, 255, 175),
        new Color(229, 204, 255, 200),
        new Color(255, 255, 255, 255)
    };

    Color[] white = {
        new Color(0, 0, 0, 0),
        new Color(100, 100, 100, 150),
        new Color(140, 140, 140, 175),
        new Color(192, 192, 192, 200),
        new Color(255, 255, 255, 255)
    };

    Color[] black = {
        new Color(0, 0, 0, 0),
        new Color(25, 25, 25, 150),
        new Color(50, 50, 50, 175),
        new Color(75, 75, 75, 200),
        new Color(100, 100, 100, 255)
    };

    Color[] colors = real;

    public Color_Palette() {
        usePalette(colors);
    }

    private void usePalette(Color[] colors) {
        for (int i = 0; i < colors.length - 1; i++) {
            interpolate(colors[i], colors[i + 1]);
        }
    }

    private void interpolate(Color start_color, Color finish_color) {
        double start_a = start_color.getAlpha();
        double start_r = start_color.getRed();
        double start_g = start_color.getGreen();
        double start_b = start_color.getBlue();

        double finish_a = finish_color.getAlpha();
        double finish_r = finish_color.getRed();
        double finish_g = finish_color.getGreen();
        double finish_b = finish_color.getBlue();

        int result_a = 0;
        int result_r = 0;
        int result_g = 0;
        int result_b = 0;

        int blends = 15;

        double blending_a;
        double blending_r;
        double blending_g;
        double blending_b;

        for (int i = 0; i <= blends; i++) {
            blending_a = (finish_a - start_a) / (blends + 1);
            blending_r = (finish_r - start_r) / (blends + 1);
            blending_g = (finish_g - start_g) / (blends + 1);
            blending_b = (finish_b - start_b) / (blends + 1);

            result_a = (int) Math.round(start_a + (blending_a * i));
            result_r = (int) Math.round(start_r + (blending_r * i));
            result_g = (int) Math.round(start_g + (blending_g * i));
            result_b = (int) Math.round(start_b + (blending_b * i));

            Color result = new Color(result_r, result_g, result_b, result_a);
            this.palette.add(result);
        }

    }

    public Color getColor(int temperature) {

        int index = Math.round(temperature / (this.colors.length + 1));
        return palette.get(index);
    }

    public void setColors(String option) {
        this.palette = new ArrayList<>();

        if (option == "Real") {
            this.colors = this.real;
        } else if (option == "Green") {
            this.colors = this.green;
        } else if (option == "Blue") {
            this.colors = this.blue;
        } else if (option == "Purple") {
            this.colors = this.purple;
        } else if (option == "White") {
            this.colors = this.white;
        } else if (option == "Black") {
            this.colors = this.black;
        }

        usePalette(colors);
    }

    public void setColors(Color[] colors_temp) {
        this.palette = new ArrayList<>();

        Color color1 = new Color(colors_temp[0].getRed(), colors_temp[0].getGreen(), colors_temp[0].getBlue(), 0);
        Color color2 = new Color(colors_temp[1].getRed(), colors_temp[1].getGreen(), colors_temp[1].getBlue(), 150);
        Color color3 = new Color(colors_temp[2].getRed(), colors_temp[2].getGreen(), colors_temp[2].getBlue(), 175);
        Color color4 = new Color(colors_temp[3].getRed(), colors_temp[3].getGreen(), colors_temp[3].getBlue(), 200);
        Color color5 = new Color(colors_temp[4].getRed(), colors_temp[4].getGreen(), colors_temp[4].getBlue(), 255);

        Color[] colors_final = {
            color1,
            color2,
            color3,
            color4,
            color5
        };
        
        usePalette(colors_final);
    }
}
