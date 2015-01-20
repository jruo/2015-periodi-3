package com.hiilimonoksidi.tiralabra;

import com.hiilimonoksidi.tiralabra.application.CLIApplication;
import com.hiilimonoksidi.tiralabra.misc.Arguments;
import com.hiilimonoksidi.tiralabra.misc.Point;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        Arguments arguments = new Arguments(args);

        if (arguments.length() == 0) {
            // new GUIApplication()?
        } else {
            String inputFile = arguments.getArgument("-i");
            String outputFolder = arguments.getArgument("-o");
            String startPoint = arguments.getArgument("-s");
            String goalPoint = arguments.getArgument("-g");
            String timeLimit = arguments.getArgument("-t");

            if (inputFile != null && startPoint != null && goalPoint != null) {
                File input = new File(inputFile);
                File output = outputFolder != null ? new File(outputFolder) : null;
                int timeout = parseTimeLimit(timeLimit);

                Point start, goal;
                try {
                    start = new Point(startPoint);
                    goal = new Point(goalPoint);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    return;
                }

                new CLIApplication(input, output, start, goal, timeout).start();
            } else {
                System.out.println("Not enough arguments. Required: -i -s -g");
            }
        }
    }

    private static int parseTimeLimit(String timelimit) {
        try {
            return timelimit != null ? Integer.parseInt(timelimit) : 0;
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}
