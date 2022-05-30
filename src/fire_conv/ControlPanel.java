/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fire_conv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Rubén
 */
public class ControlPanel extends JPanel implements ActionListener, ChangeListener {

    private JButton load;
    private JButton play;
    private JButton pause;
    private JButton stop;
    private JButton apply_filter;
    private JButton palette;
    private JButton color1;
    private JButton color2;
    private JButton color3;
    private JButton color4;
    private JButton color5;
    private JButton apply_custom;

    private JLabel background;
    private JLabel controls;
    private JLabel frames;
    private JLabel tolerance;
    private JLabel filters;
    private JLabel spark_label;
    private JLabel cool_label;
    private JLabel empty1;
    private JLabel empty2;
    private JLabel empty3;
    private JLabel empty4;
    private JLabel empty5;
    private JLabel fire_label;
    private JLabel color_label;
    private JLabel conv_label;

    private JSlider fps_slider;
    private JSlider tolerance_slider;

    public JCheckBox apply_conv;

    private JSpinner sparks;
    private JSpinner cool;

    private JSeparator separator1;
    private JSeparator separator2;
    private JSeparator separator3;

    String[] filter_options = {"VERTICAL", "HORIZONTAL", "SOBEL_V", "SOBEL_H", "SCHARR_V", "SCHARR_H"};
    JComboBox<String> dropdown;
    String[] filter_palette = {"Real", "Green", "Blue", "Purple", "White", "Black"};
    JComboBox<String> palette_dropdown;

    private JFileChooser fileExplorer;

    private final Fire_Conv fire_conv;
    Fire fire;

    int fire_fps;
    int fire_tolerance;

    BufferedImage image;

    Color dark = new Color(27, 27, 27);
    Color light = new Color(227, 227, 227);

    public ControlPanel(Fire_Conv PRUEBA) {
        this.fire_conv = PRUEBA;
        this.fire = fire_conv.fire;
        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        initComponents(c);
    }

    private void formatJ(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(light);
    }

    private void formatJ(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(light);
    }

    private void formatJ2(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(light);
    }

    private void formatJ2(JCheckBox box) {
        box.setFont(new Font("Arial", Font.BOLD, 12));
        box.setForeground(light);
    }

    private void formatJ3(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(light);
    }

    private void initComponents(GridBagConstraints c) {
        this.background = new JLabel("SELECT BACKGROUND");
        formatJ(background);
        this.load = new JButton("Load Image");
        formatJ(load);

        this.fire_label = new JLabel("FIRE");
        formatJ3(fire_label);

        this.separator1 = new JSeparator();
        separator1.setBackground(light);

        this.controls = new JLabel("CONTROLS");
        formatJ(controls);
        this.play = new JButton("Play");
        formatJ(play);
        this.pause = new JButton("Pause");
        formatJ(pause);
        this.stop = new JButton("Stop");
        formatJ(stop);

        this.frames = new JLabel("FIRE FRAMES");
        formatJ(frames);

        this.spark_label = new JLabel("Sparks (1-100)");
        this.sparks = new JSpinner(new SpinnerNumberModel(30, 1, 100, 1));

        this.cool_label = new JLabel("Cooling (0-3)");
        this.cool = new JSpinner(new SpinnerNumberModel(0.1, 0, 3, 0.05));

        this.color_label = new JLabel("COLORS");
        formatJ3(color_label);

        this.separator2 = new JSeparator();
        separator2.setBackground(light);

        this.palette_dropdown = new JComboBox<>(filter_palette);
        this.palette = new JButton("Apply Palette");
        formatJ(palette);

        this.color1 = new JButton("Color 1");
        this.color2 = new JButton("Color 2");
        this.color3 = new JButton("Color 3");
        this.color4 = new JButton("Color 4");
        this.color5 = new JButton("Color 5");
        this.empty1 = new JLabel();
        this.empty2 = new JLabel();
        this.empty3 = new JLabel();
        this.empty4 = new JLabel();
        this.empty5 = new JLabel();
        formatJ(color1);
        formatJ(color2);
        formatJ(color3);
        formatJ(color4);
        formatJ(color5);
        this.apply_custom = new JButton("Apply Custom Palette");
        formatJ(apply_custom);

        this.conv_label = new JLabel("CONVOLUTION");
        formatJ3(conv_label);

        this.separator3 = new JSeparator();
        separator3.setBackground(light);

        this.filters = new JLabel("EDGE FILTERS");
        formatJ(filters);
        this.dropdown = new JComboBox<>(filter_options);
        this.apply_filter = new JButton("Apply Filter");
        formatJ(apply_filter);

        this.tolerance = new JLabel("TOLERANCE");
        formatJ(tolerance);

        this.apply_conv = new JCheckBox("Apply Convolution", false);

        initSliders();

        this.positionComponent(0, 0, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), c, background);
        this.positionComponent(0, 1, 3, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, load);
        this.load.addActionListener(this);

        this.positionComponent(0, 2, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 5, 5, 5), c, fire_label);
        this.positionComponent(0, 3, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, separator1);

        this.positionComponent(0, 4, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), c, controls);
        this.positionComponent(0, 5, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, play);
        this.play.addActionListener(this);
        this.positionComponent(1, 5, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, pause);
        this.pause.addActionListener(this);
        this.positionComponent(2, 5, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, stop);
        this.stop.addActionListener(this);

        this.positionComponent(0, 6, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), c, frames);
        this.positionComponent(0, 7, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, fps_slider);
        this.fps_slider.addChangeListener(this);

        this.positionComponent(0, 8, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, sparks);
        this.sparks.addChangeListener(this);
        this.positionComponent(1, 8, 2, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, spark_label);
        formatJ2(spark_label);

        this.positionComponent(0, 9, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, cool);
        this.cool.addChangeListener(this);
        this.positionComponent(1, 9, 2, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, cool_label);
        formatJ2(cool_label);

        this.positionComponent(0, 10, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 5, 5, 5), c, color_label);
        this.positionComponent(0, 11, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, separator2);

        this.positionComponent(0, 12, 2, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, palette_dropdown);
        this.positionComponent(2, 12, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, palette);
        this.palette.addActionListener(this);

        this.positionComponent(0, 13, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), c, color1);
        this.empty1.setBackground(Color.BLACK);
        this.empty1.setOpaque(true);
        this.positionComponent(1, 13, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 0, 5), c, empty1);
        this.color1.addActionListener(this);

        this.positionComponent(0, 14, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), c, color2);
        this.empty2.setBackground(Color.BLACK);
        this.empty2.setOpaque(true);
        this.positionComponent(1, 14, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), c, empty2);
        this.color2.addActionListener(this);

        this.positionComponent(0, 15, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), c, color3);
        this.empty3.setBackground(Color.BLACK);
        this.empty3.setOpaque(true);
        this.positionComponent(1, 15, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), c, empty3);
        this.color3.addActionListener(this);

        this.positionComponent(0, 16, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), c, color4);
        this.empty4.setBackground(Color.BLACK);
        this.empty4.setOpaque(true);
        this.positionComponent(1, 16, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), c, empty4);
        this.color4.addActionListener(this);

        this.positionComponent(0, 17, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), c, color5);
        this.empty5.setBackground(Color.BLACK);
        this.empty5.setOpaque(true);
        this.positionComponent(1, 17, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), c, empty5);
        this.color5.addActionListener(this);

        this.positionComponent(0, 18, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, apply_custom);
        this.apply_custom.addActionListener(this);

        this.positionComponent(0, 19, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 5, 5, 5), c, conv_label);
        this.positionComponent(0, 20, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, separator3);

        this.positionComponent(0, 21, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), c, filters);
        this.positionComponent(0, 22, 2, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, dropdown);
        this.positionComponent(2, 22, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, apply_filter);
        this.apply_filter.addActionListener(this);

        this.positionComponent(0, 23, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), c, tolerance);
        this.positionComponent(0, 24, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, tolerance_slider);
        this.tolerance_slider.addChangeListener(this);

        this.positionComponent(0, 25, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), c, apply_conv);
        this.apply_conv.addActionListener(this);
        formatJ2(apply_conv);

    }

    private void positionComponent(int gridx, int gridy, int gridwidth, double weightx,
            double weighty, int anchor, int fill, Insets insets, GridBagConstraints c,
            Component component) {
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.weightx = weightx;
        c.weighty = weighty;
        c.anchor = anchor;
        c.fill = fill;
        c.insets = insets;
        this.add(component, c);
    }

    private void initSliders() {
        this.fps_slider = new JSlider(10, 60, 30);
        this.fps_slider.setMinorTickSpacing(5);
        this.fps_slider.setMajorTickSpacing(5);
        this.fps_slider.setPaintTicks(true);
        this.fps_slider.setPaintLabels(true);
        this.fps_slider.setForeground(light);

        this.tolerance_slider = new JSlider(50, 200, 100);
        this.tolerance_slider.setMinorTickSpacing(5);
        this.tolerance_slider.setMajorTickSpacing(25);
        this.tolerance_slider.setPaintTicks(true);
        this.tolerance_slider.setPaintLabels(true);
        this.tolerance_slider.setForeground(light);
    }

    private void addBackground(double[][] img_filter) {
        this.fileExplorer = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "gif", "png", "bmp", "tif");
        this.fileExplorer.setFileFilter(filter);

        if (this.fileExplorer.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            ImageIcon imageIcon = new ImageIcon(this.fileExplorer.getSelectedFile().getAbsolutePath());

            image = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            Graphics g = image.createGraphics();

            imageIcon.paintIcon(null, g, 0, 0);

            g.dispose();

            if (image.getHeight(null) <= 0 || image.getWidth(null) <= 0) {
                fire_conv.getViewer().setBackground(null);
            } else {

                fire_conv.getViewer().setBackground(image, img_filter);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String option = (String) dropdown.getSelectedItem();

        if (event.getActionCommand().equals("Load Image")) {
            this.addBackground(checkDropdown(option));

            this.fire.setRunning(false);
            this.fire.newThread();
            this.fire_conv.viewer.setFirst(true);
        }

        if (event.getActionCommand().equals("Play")) {
            if (this.image != null) {

                if (!this.fire.fireThread.isAlive()) {
                    this.fire.setFps(this.fire_fps);
                    this.fire.fireThread.start();
                }
                this.fire.setRunning(true);
                this.fire.setPaused(false);
            } else {
                JOptionPane.showMessageDialog(null, "Load an image first.");
            }
        }

        if (event.getActionCommand().equals("Pause")) {
            this.fire.setPaused(true);
        }

        if (event.getActionCommand().equals("Stop")) {
            this.fire.setRunning(false);
            this.fire.newThread();
            this.fire_conv.viewer.setFirst(true);
        }

        if (event.getActionCommand().equals("Apply Filter")) {
            fire_conv.getViewer().setBackground(image, checkDropdown(option));
            this.fire_conv.viewer.setFirst(true);
        }

        if (event.getActionCommand().equals("Apply Convolution")) {
            this.fire.setApply_conv(apply_conv.getModel().isSelected());
        }

        option = (String) palette_dropdown.getSelectedItem();
        if (event.getActionCommand().equals("Apply Palette")) {
            this.fire.palette.setColors(option);
        }

        if (event.getActionCommand().equals("Color 1")) {
            Color color = JColorChooser.showDialog(new JPanel(), "Elige un color", Color.BLACK);
            this.empty1.setBackground(color);
        }

        if (event.getActionCommand().equals("Color 2")) {
            Color color = JColorChooser.showDialog(new JPanel(), "Elige un color", Color.BLACK);
            this.empty2.setBackground(color);
        }

        if (event.getActionCommand().equals("Color 3")) {
            Color color = JColorChooser.showDialog(new JPanel(), "Elige un color", Color.BLACK);
            this.empty3.setBackground(color);
        }

        if (event.getActionCommand().equals("Color 4")) {
            Color color = JColorChooser.showDialog(new JPanel(), "Elige un color", Color.BLACK);
            this.empty4.setBackground(color);
        }

        if (event.getActionCommand().equals("Color 5")) {
            Color color = JColorChooser.showDialog(new JPanel(), "Elige un color", Color.BLACK);
            this.empty5.setBackground(color);
        }

        if (event.getActionCommand().equals("Apply Custom Palette")) {
            Color[] colors = {
                this.empty1.getBackground(),
                this.empty2.getBackground(),
                this.empty3.getBackground(),
                this.empty4.getBackground(),
                this.empty5.getBackground()
            };
            this.fire.palette.setColors(colors);
        }

    }

    private double[][] checkDropdown(String option) {
        double[][] VERTICAL = {{1, 0, -1}, {1, 0, -1}, {1, 0, -1}};
        switch (option) {
            case "VERTICAL":
                return VERTICAL;
            case "HORIZONTAL":
                double[][] HORIZONTAL = {{1, 1, 1}, {0, 0, 0}, {-1, -1, -1}};
                return HORIZONTAL;
            case "SOBEL_V":
                double[][] SOBEL_V = {{1, 0, -1}, {2, 0, -2}, {1, 0, -1}};
                return SOBEL_V;
            case "SOBEL_H":
                double[][] SOBEL_H = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
                return SOBEL_H;
            case "SCHARR_V":
                double[][] SCHARR_V = {{3, 0, -3}, {10, 0, -10}, {3, 0, -3}};
                return SCHARR_V;
            case "SCHARR_H":
                double[][] SCHARR_H = {{3, 10, 3}, {0, 0, 0}, {-3, -10, -3}};
                return SCHARR_H;
        }
        return VERTICAL;

    }

    @Override
    public void stateChanged(ChangeEvent event) {
        if (event.getSource() instanceof JSlider) {
            JSlider jslider = (JSlider) event.getSource();

            if (jslider == this.fps_slider) {

                this.fire_fps = fps_slider.getValue();
                this.fire.setFps(this.fire_fps);

            } else if (jslider == this.tolerance_slider) {

                this.fire_tolerance = tolerance_slider.getValue();
                this.fire.setTolerance(this.fire_tolerance);

            }
        }

        if (event.getSource() instanceof JSpinner) {
            JSpinner jspinner = (JSpinner) event.getSource();

            if (jspinner == this.sparks) {
                this.fire.setSpark_chance((int) this.sparks.getValue());

            }

            if (jspinner == this.cool) {
                this.fire.setCooling((double) this.cool.getValue());

            }
        }
    }

}
