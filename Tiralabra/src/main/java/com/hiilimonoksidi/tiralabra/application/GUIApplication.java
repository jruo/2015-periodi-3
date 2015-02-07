package com.hiilimonoksidi.tiralabra.application;

import com.hiilimonoksidi.tiralabra.application.gui.InputPanel;
import com.hiilimonoksidi.tiralabra.application.gui.MainWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUIApplication {

    public GUIApplication() {
        setLF();
        MainWindow mainWindow = new MainWindow();
        mainWindow.setPanel(new InputPanel(mainWindow));
        mainWindow.setPanel(new InputPanel(mainWindow));
    }

    private void setLF() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
        }
    }
}
