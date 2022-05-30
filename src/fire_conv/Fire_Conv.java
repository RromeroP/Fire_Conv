/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package fire_conv;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author Rubén
 */
public class Fire_Conv extends JFrame {

    /**
     * @param args the command line arguments //
     */
    ControlPanel controlPanel;
    Viewer viewer;
    Fire fire;

    public static void main(String[] args) {
        // TODO code application logic here    
        Color dark = new Color(27, 27, 27);
        Color light = new Color(227, 227, 227);

        for (LookAndFeelInfo look_feel : UIManager.getInstalledLookAndFeels()) {
            if (look_feel.getName() == "Nimbus") {
                try {
                    UIManager.setLookAndFeel(look_feel.getClassName());
                    UIManager.put("Slider.tickColor", light);
                    UIManager.put("control", dark);
                    UIManager.put("nimbusBase", dark);
                    UIManager.put("OptionPane.messageForeground", light);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        Fire_Conv PRUEBA = new Fire_Conv();
    }

    private void addPanels() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.001;
        c.weighty = 1;
        this.add(this.controlPanel, c);
        c.weightx = 0.999;
        c.gridx = 1;
        this.add(this.viewer, c);
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public Viewer getViewer() {
        return viewer;
    }

    public Fire_Conv() {
        initComponents();
        this.createFire();
    }

    private void createFire() {
    }

    private void initComponents() {
        this.setSize(800, 800);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.viewer = new Viewer(this);
        this.fire = new Fire(this);
        this.controlPanel = new ControlPanel(this);
        this.addPanels();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
