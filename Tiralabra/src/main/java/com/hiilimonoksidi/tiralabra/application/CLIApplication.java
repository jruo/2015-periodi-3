package com.hiilimonoksidi.tiralabra.application;

import com.hiilimonoksidi.tiralabra.graph.Graph;
import com.hiilimonoksidi.tiralabra.graph.Path;
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
public class CLIApplication {

    private final File inputFile, outputFolder;
    private final Point start, goal;
    private final int timeout;

    private BufferedImage image;
    private Graph graph;

    public CLIApplication(File inputFile, File outputFile, Point start, Point goal, int timeout) {
        this.inputFile = inputFile;
        this.outputFolder = outputFile;
        this.start = start;
        this.goal = goal;
        this.timeout = timeout;
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
            System.out.println("Path length: " + path.getLength());
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
        tester.start(timeout);

        System.out.println("Time elapsed: " + tester.getTimeElapsed() + " ms");
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
}
