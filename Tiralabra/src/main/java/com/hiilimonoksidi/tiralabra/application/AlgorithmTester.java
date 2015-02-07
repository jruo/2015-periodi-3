package com.hiilimonoksidi.tiralabra.application;

import com.hiilimonoksidi.tiralabra.graph.Graph;
import com.hiilimonoksidi.tiralabra.graph.Path;
import com.hiilimonoksidi.tiralabra.misc.Point;
import com.hiilimonoksidi.tiralabra.misc.Timer;
import com.hiilimonoksidi.tiralabra.pathfinding.PathfindingAlgorithm;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Testaaja algoritmille.
 *
 * @author Janne Ruoho
 */
public class AlgorithmTester {

    private final PathfindingAlgorithm algorithm;
    private final Graph graph;
    private final Point start, goal;
    private final Timer timer;
    private Path path;

    public AlgorithmTester(PathfindingAlgorithm algorithm, Graph graph, Point start, Point goal) {
        this.algorithm = algorithm;
        this.graph = graph;
        this.start = start;
        this.goal = goal;
        timer = new Timer();
    }

    /**
     * Käynnistää testauksen.
     *
     * @param timeout Aika sekunteina, kuinka kauan odotetaan ennen kuin etsintä
     * pysäytetään. 0 = ei rajoitusta.
     * @throws
     * com.hiilimonoksidi.tiralabra.application.AlgorithmTimeoutException
     */
    public void start(int timeout) throws AlgorithmTimeoutException {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    algorithm.init(graph, start, goal);
                    path = algorithm.search();
                }
            });

            timer.start();
            thread.start();
            thread.join(timeout * 1000);
            timer.stop();

            if (thread.isAlive()) {
                algorithm.stop();
                thread.join();
                throw new AlgorithmTimeoutException();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(AlgorithmTester.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    /**
     * Käynnistää testauksen, joka käy algoritmin läpi askel askeleelta odottaen
     * joka askeleen välillä kontrollerin määrittämän ajan ja palauttaa sille
     * käsittellyt ja käsittelyssä olevat solmut. Metodi ei mittaa aikaa eikä
     * blockaa suoritusta. Algoritmin päätyttyä palauttaa polun kontrollerille.
     *
     * @param stepController Kontrolleri
     */
    public void start(final StepController stepController) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                algorithm.init(graph, start, goal);
                while (algorithm.step()) {
                    stepController.setOpenNodes(algorithm.getOpenNodes());
                    stepController.setClosedNodes(algorithm.getClosedNodes());
                    try {
                        Thread.sleep(stepController.getDelay());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AlgorithmTester.class.getName()).log(Level.SEVERE, null, ex);
                        System.exit(1);
                    }
                }
                stepController.setPath(algorithm.getPath());
            }
        });
        thread.start();
    }

    public Path getPath() {
        return path;
    }

    public double getTimeElapsed() {
        return timer.getTime();
    }
}
