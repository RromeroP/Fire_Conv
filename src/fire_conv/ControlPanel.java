/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fire_conv;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
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

    private JLabel background;
    private JLabel controls;
    private JLabel frames;
    private JLabel filters;

    private JSlider slider;

    String[] filter_options = {"VERTICAL", "HORIZONTAL", "SOBEL_V", "SOBEL_H", "SCHARR_V", "SCHARR_H"};
    JComboBox<String> dropdown;

    private JFileChooser fileExplorer;

    private final Fire_Conv fire_conv;
    Fire fire;

    int fire_fps;

    BufferedImage image;

    public ControlPanel(Fire_Conv PRUEBA) {
        this.fire_conv = PRUEBA;
        this.fire = fire_conv.fire;
        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        initComponents(c);

    }

    private void initComponents(GridBagConstraints c) {
        this.background = new JLabel("SELECT BACKGROUND");
        this.load = new JButton("Load Image");

        this.controls = new JLabel("CONTROLS");
        this.play = new JButton("Play");
        this.pause = new JButton("Pause");
        this.stop = new JButton("Stop");

        this.frames = new JLabel("FIRE FRAMES");

        this.filters = new JLabel("EDGE FILTERS");
        this.dropdown = new JComboBox<>(filter_options);
        this.apply_filter = new JButton("Apply");

        initSliders();

        this.positionComponent(0, 0, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), c, background);
        this.positionComponent(0, 1, 3, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, load);
        this.load.addActionListener(this);

        this.positionComponent(0, 2, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), c, controls);
        this.positionComponent(0, 3, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, play);
        this.play.addActionListener(this);
        this.positionComponent(1, 3, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, pause);
        this.pause.addActionListener(this);
        this.positionComponent(2, 3, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, stop);
        this.stop.addActionListener(this);

        this.positionComponent(0, 4, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), c, frames);
        this.positionComponent(0, 5, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), c, slider);
        this.slider.addChangeListener(this);

        this.positionComponent(0, 6, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), c, filters);
        this.positionComponent(0, 7, 2, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, dropdown);
        this.positionComponent(2, 7, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), c, apply_filter);
        this.apply_filter.addActionListener(this);

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
        this.slider = new JSlider(10, 60, 30);
        this.slider.setMinorTickSpacing(5);
        this.slider.setMajorTickSpacing(5);
        this.slider.setPaintTicks(true);
        this.slider.setPaintLabels(true);
        this.slider.setForeground(Color.BLACK);
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
                /*fire_conv.getViewer().setBackground(null);*/
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
        }

        if (event.getActionCommand().equals("Play")) {
            if (!this.fire.fireThread.isAlive()) {
                this.fire.setFps(this.fire_fps);
                this.fire.fireThread.start();
            }
            this.fire.setRunning(true);
            this.fire.setPaused(false);
        }

        if (event.getActionCommand().equals("Pause")) {
            this.fire.setPaused(true);
        }

        if (event.getActionCommand().equals("Stop")) {
            this.fire.setRunning(false);
            this.fire.newThread();
        }

        if (event.getActionCommand().equals("Apply")) {
            fire_conv.getViewer().setBackground(image, checkDropdown(option));
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
            if (jslider == this.slider) {
                this.fire_fps = slider.getValue();
                this.fire.setFps(this.fire_fps);
            }
        }
    }

}
