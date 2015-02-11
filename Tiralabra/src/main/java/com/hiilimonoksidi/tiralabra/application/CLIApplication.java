package com.hiilimonoksidi.tiralabra.application;

import com.hiilimonoksidi.tiralabra.graph.Graph;
import com.hiilimonoksidi.tiralabra.graph.Path;
import com.hiilimonoksidi.tiralabra.misc.Arguments;
import com.hiilimonoksidi.tiralabra.misc.GraphBuilder;
import com.hiilimonoksidi.tiralabra.misc.PathImageWriter;
import com.hiilimonoksidi.tiralabra.misc.Point;
import com.hiilimonoksidi.tiralabra.pathfinding.PathfindingAlgorithm;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Komentorivipohjainen ohjelma. Suorittaa eri algoritmit annetulle syötteelle
 * ja tulostaa niiden tulokset System.out:iin sekä tallentaa löydetyt polut
 * tiedostoon, jos tallennuskansio on määritelty.
 *
 * @author Janne Ruoho
 */
public final class CLIApplication {

    private File inputFile, outputFolder;
    private Point start, goal;
    private int timeout;

    private BufferedImage image;
    private Graph graph;

    public CLIApplication(Arguments arguments) {
        String inputArgument = arguments.getArgument("-i");
        String outputArgument = arguments.getArgument("-o");
        String startArgument = arguments.getArgument("-s");
        String goalArgument = arguments.getArgument("-g");
        String timeArgument = arguments.getArgument("-t");

        if (inputArgument != null && startArgument != null && goalArgument != null) {
            inputFile = new File(inputArgument);
            outputFolder = outputArgument != null ? new File(outputArgument) : null;
            timeout = parseArgumentTimeLimit(timeArgument);

            try {
                start = new Point(startArgument);
                goal = new Point(goalArgument);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }

            start();
        } else {
            System.out.println("Not enough arguments. Required: -i -s -g");
            System.exit(1);
        }
    }

    /**
     * Suorittaa ohjelman.
     */
    public void start() {
        if ((graph = createGraph()) == null) {
            return;
        }

        for (PathfindingAlgorithm.Type algorithm : PathfindingAlgorithm.Type.values()) {
            AlgorithmTester tester = testAlgorithm(algorithm);
            processResults(tester, algorithm);
        }
    }

    /**
     * Tarkistaa löysikö algoritmi polun ja tallentaa sen tiedostoon.
     *
     * @param tester Algoritmin testaaja
     * @param algorithm Algoritmi
     */
    private void processResults(AlgorithmTester tester, PathfindingAlgorithm.Type algorithm) {
        Path path = tester.getPath();
        if (path != null) {
            System.out.println("Path found!");
            System.out.printf("Path length: %.2f\n", path.getLength());
            if (outputFolder != null) {
                PathImageWriter.write(outputFolder, image, path, algorithm);
            }
        } else {
            System.out.println("No path found.");
        }
        System.out.println("----------");
    }

    /**
     * Testaa annetun algoritmin.
     *
     * @param algorithm Algoritmi, jota testataan.
     * @return Testausolion.
     */
    private AlgorithmTester testAlgorithm(PathfindingAlgorithm.Type algorithm) {
        System.out.println("Running " + algorithm.toString() + "...");

        AlgorithmTester tester = new AlgorithmTester(algorithm.getInstance(), graph, start, goal);
        try {
            tester.start(timeout);
        } catch (AlgorithmTimeoutException ex) {
            System.out.println("Time limit exceeded.");
        }

        System.out.printf("Time elapsed: %.0f ms\n", tester.getTimeElapsed());
        return tester;
    }

    /**
     * Lukee syötekuvan ja luo siitä verkon.
     */
    private Graph createGraph() {
        try {
            System.out.println("Initializing...");
            image = ImageIO.read(inputFile);
            return GraphBuilder.createFromImage(image);
        } catch (IOException ex) {
            System.out.println("Can't read input file.");
            return null;
        } finally {
            System.out.println("----------");
        }
    }

    private int parseArgumentTimeLimit(String timelimit) {
        try {
            return timelimit != null ? Integer.parseInt(timelimit) : 0;
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}
