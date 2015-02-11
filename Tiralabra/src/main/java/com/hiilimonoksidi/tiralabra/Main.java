package com.hiilimonoksidi.tiralabra;

import com.hiilimonoksidi.tiralabra.application.CLIApplication;
import com.hiilimonoksidi.tiralabra.application.GUIApplication;
import com.hiilimonoksidi.tiralabra.misc.Arguments;

/**
 * Käynnistää ohjelman.
 *
 * @author Janne Ruoho
 */
public class Main {

    public static void main(String[] args) {
        Arguments arguments = new Arguments(args);

        if (arguments.getLength() == 0) {
            new GUIApplication();
        } else {
            new CLIApplication(arguments);
        }
    }
}
