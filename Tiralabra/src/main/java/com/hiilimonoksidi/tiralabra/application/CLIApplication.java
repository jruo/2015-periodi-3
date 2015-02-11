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
    private boolean lessOutput;

    private BufferedImage image;
    private Graph graph;

    public CLIApplication(Arguments arguments) {
        String inputArgument = arguments.getArgument("-i");
        String outputArgument = arguments.getArgument("-o");
        String startArgument = arguments.getArgument("-s");
        String goalArgument = arguments.getArgument("-g");
        String timeArgument = arguments.getArgument("-t");
        String countArgument = arguments.getArgument("-c");
        String algoArgument = arguments.getArgument("-a");
        lessOutput = arguments.hasArgument("--less");

        if (inputArgument != null && startArgument != null && goalArgument != null) {
            inputFile = new File(inputArgument);
            outputFolder = outputArgument != null ? new File(outputArgument) : null;
            timeout = parseStringToInt(timeArgument, 0);

            try {
                start = new Point(startArgument);
                goal = new Point(goalArgument);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }

            createGraph();

            int count = parseStringToInt(countArgument, 1);

            for (int i = 0; i < count; i++) {
                if (algoArgument == null) {
                    testAllAlgorithms();
                } else {
                    PathfindingAlgorithm.Type algorithm = parseAlgorithm(algoArgument);
                    testOneAlgorithm(algorithm);
                }
            }

        } else {
            print("Not enough arguments. Required: -i -s -g", true);
            System.exit(1);
        }
    }

    /**
     * Testaa kaikki algoritmit
     */
    private void testAllAlgorithms() {
        for (PathfindingAlgorithm.Type algorithm : PathfindingAlgorithm.Type.values()) {
            testOneAlgorithm(algorithm);
        }
    }

    /**
     * Testaa yhden algoritmin.
     *
     * @param algorithm Testattava algoritmi
     */
    private void testOneAlgorithm(PathfindingAlgorithm.Type algorithm) {
        AlgorithmTester tester = testAlgorithm(algorithm);
        processResults(tester, algorithm);
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
            print("Path found!");
            printf("Path length: %.2f", false, path.getLength());
            if (outputFolder != null) {
                if (PathImageWriter.write(outputFolder, image, path, algorithm)) {
                    print("Saved output image to " + outputFolder.getAbsoluteFile());
                }
            }
        } else {
            print("No path found.");
        }
        print("----------");

    }

    /**
     * Testaa annetun algoritmin.
     *
     * @param algorithm Algoritmi, jota testataan.
     * @return Testausolion.
     */
    private AlgorithmTester testAlgorithm(PathfindingAlgorithm.Type algorithm) {
        print("Running " + algorithm.toString() + "...");

        AlgorithmTester tester = new AlgorithmTester(algorithm.getInstance(), graph, start, goal);
        try {
            tester.start(timeout);
        } catch (AlgorithmTimeoutException ex) {
            print("Time limit exceeded.", true);
        }

        printf("%s%.0f ms", true, lessOutput ? "" : "Time elapsed: ", tester.getTimeElapsed());

        return tester;
    }

    /**
     * Lukee syötekuvan ja luo siitä verkon.
     */
    private void createGraph() {
        try {
            print("Initializing...");
            image = ImageIO.read(inputFile);
            graph = GraphBuilder.createFromImage(image);
        } catch (IOException ex) {
            print("Can't read input file.", true);
            System.exit(1);
        } finally {
            print("----------");
        }
    }

    /**
     * Muuttaa merkkijonon numeroksi.
     *
     * @param string Merkkijono
     * @param def Jos merkkijono ei ole validi numero, palauttaa tämän
     * @return Numero
     */
    private int parseStringToInt(String string, int def) {
        try {
            return string != null ? Integer.parseInt(string) : def;
        } catch (NumberFormatException ex) {
            return def;
        }
    }

    private PathfindingAlgorithm.Type parseAlgorithm(String name) {
        try {
            return PathfindingAlgorithm.Type.valueOf(name);
        } catch (IllegalArgumentException ex) {
            print("Invalid algorithm: " + name, true);
            System.exit(1);
            return null;
        }
    }

    /**
     * Tulostaa rivin System.outiin, ellei vähemmän tulosteen tila ole käytössä.
     *
     * @param line Tulostettava rivi
     */
    private void print(String line) {
        print(line, false);
    }

    /**
     * Tulostaa rivin System.outiin. Jos vähemmän tulosteen tila on käytössä,
     * toinen parametri määrää tulostetaanko rivi siitä huolimatta.
     *
     * @param line Tulostettava rivi
     * @param important Tulostetaanko vähemmän tulosteen tilasta riippumatta
     */
    private void print(String line, boolean important) {
        if (!lessOutput || important) {
            System.out.println(line);
        }
    }

    /**
     * Tulostaa muotoillun rivin System.outiin. Jos vähemmän syötteen tila on
     * käytössä, toinen parametri määrää tulostetaanko rivi siitä huolimatta.
     *
     * @param line Tulostettava rivi
     * @param important Tulostetaanko vähemmän tulosteen tilasta riippumatta
     * @param args Muotoilun argumentit
     */
    private void printf(String line, boolean important, Object... args) {
        print(String.format(line, args), important);
    }
}
