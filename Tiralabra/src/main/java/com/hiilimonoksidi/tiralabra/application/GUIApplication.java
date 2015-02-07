package com.hiilimonoksidi.tiralabra.application;

import com.hiilimonoksidi.tiralabra.application.gui.InputPanel;
import com.hiilimonoksidi.tiralabra.application.gui.MainWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Graafinen käyttöliittymä ohjelmalle.
 * 
 * @author Janne Ruoho
 */
public class GUIApplication {

    public GUIApplication() {
        setLF();
        MainWindow mainWindow = new MainWindow();
        mainWindow.setPanel(new InputPanel(mainWindow));
    }

    /**
     * Asettaa ikkunoiden käyttämän tyylin vastaamaan käyttöjärjestelmän tyyliä.
     */
    private void setLF() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
        }
    }
}
