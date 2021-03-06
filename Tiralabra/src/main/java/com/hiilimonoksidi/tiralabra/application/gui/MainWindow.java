package com.hiilimonoksidi.tiralabra.application.gui;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JFrame;

/**
 * Graafisen käyttöliittymän pääikkuna.
 * 
 * @author Janne Ruoho
 */
public class MainWindow extends JFrame {
    
    private Component panel;

    public MainWindow() {
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Vaihtaa ikkunan paneelin toiseen.
     * 
     * @param component Uusi paneeli
     */
    public void setPanel(Component component) {
        Rectangle bounds = getBounds();
        if (panel != null) {
            getContentPane().remove(panel);
        }
        getContentPane().add(component);
        validate();
        pack();
        panel = component;
        resizeWindow(bounds);
    }
    
    /**
     * Muuttaa ikkunan koon niin, että se pysyy keskitetysti samassa paikassa.
     * 
     * @param previousBounds Vanha sijainti ja koko
     */
    public void resizeWindow(Rectangle previousBounds) {
        int previousCenterX = (int) previousBounds.getCenterX();
        int previousCenterY = (int) previousBounds.getCenterY();
        
        Rectangle currentBounds = getBounds();
        int currentCenterX = (int) currentBounds.getCenterX();
        int currentCenterY = (int) currentBounds.getCenterY();
        
        Point location = getLocation();
        location.translate(previousCenterX - currentCenterX, previousCenterY - currentCenterY);
        setLocation(location);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tiralabra");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(700, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
