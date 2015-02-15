package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.datastructures.HashSet;
import com.hiilimonoksidi.tiralabra.graph.Graph;
import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;
import com.hiilimonoksidi.tiralabra.misc.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Yliluokka reitinensintäalgoritmeille.
 *
 * @author Janne Ruoho
 */
public abstract class PathfindingAlgorithm {

    protected final float SQRT_2 = 1.41421356237f;

    /**
     * Tyhjä joukko
     */
    protected final HashSet<Node> emptySet = new HashSet<>();

    /**
     * Verkko, josta etsitään reitti
     */
    protected Graph graph;

    /**
     * Reitinetsinnän aloitus- ja lopetuspisteet
     */
    protected Point start, goal;

    /**
     * Lopetuspisteen koordinaatit
     */
    protected int gx, gy;

    /**
     * Löydetty polku
     */
    protected Path path;

    /**
     * Sisältää tiedon, onko algoritmi pysäytetty
     */
    private boolean stopped;

    /**
     * Keskeyttää algoritmin toiminnan.
     */
    public void stop() {
        stopped = true;
    }

    /**
     * Alustaa algoritmin. Kutsuttava ennen search-metodin kutsumista.
     *
     * @param graph Verkko
     * @param start Reitin aloituspiste
     * @param goal Reitin lopetuspiste
     */
    public final void init(Graph graph, Point start, Point goal) {
        this.graph = new Graph(graph);
        this.start = start;
        this.goal = goal;
        gx = goal.x;
        gy = goal.y;
        stopped = false;
        init();
    }

    /**
     * Alustaa algoritmin käyttämät tietorakenteet.
     */
    protected abstract void init();

    /**
     * Suorittaa yhden askeleen reitinesinnässä
     *
     * @return Palauttaa tosi, jos tämä askel päätyi maaliin. Muussa tapauksessa
     * epätosi.
     */
    protected abstract boolean searchStep();

    /**
     * Tarkistaa pystyykö algoritmin seuraavan askeleen suorittamaan.
     *
     * @return Tosi jos pystyy, epätosi muuten
     */
    protected abstract boolean hasNextStep();

    /**
     * Palauttaa algoritmin jo tutkimat solmut.
     *
     * @return Tutkitut solmut
     */
    public abstract Iterable<Node> getClosedNodes();

    /**
     * Palauttaa solmut, jota algoritmi tutkii.
     *
     * @return Tutkittavat solmut
     */
    public abstract Iterable<Node> getOpenNodes();

    /**
     * Aloittaa reitinetsinnän.
     *
     * @return Polun aloituspisteestä lopetuspisteeseen, jos reitti on olemassa.
     * Jos reittiä ei ole, palauttaa null.
     */
    public Path search() {
        while (step()) {
        }
        return path;
    }

    /**
     * Liikkuu yhden askeleen eteenpäin.
     *
     * @return Tosi jos askel onnistui, epätosi jos ei onnistunut tai etsintä
     * loppui.
     */
    public boolean step() {
        return !stopped && hasNextStep() && !searchStep();
    }

    /**
     * Tarkistaa askeleen lopuksi, löytyikö maali. Jos maali löytyi, rakentaa
     * polun.
     *
     * @param node Askeleessa käsitelty solmu
     * @return Tosi jos maali löytyi, false muutoin.
     */
    protected boolean finishStep(Node node) {
        if (node.x == gx && node.y == gy) {
            reconstructPath(node);
            return true;
        }
        return false;
    }

    /**
     * Palauttaa löydetyn polun.
     *
     * @return Polku
     */
    public Path getPath() {
        return path;
    }

    /**
     * Rakentaa polun annetusta solmusta taaksepäin aloituspisteen solmuun
     * seuraamalla solmujen edeltäjiä.
     *
     * @param goal Solmu, josta polkua lähdetään rakentamaan
     * @return Polku annetusta solmusta aloituspisteen solmuun.
     */
    protected Path reconstructPath(Node goal) {
        return reconstructPath(goal, new Path());
    }

    protected Path reconstructPath(Node goal, Path path) {
        boolean reachedStart = false;

        path.addPoint(goal.getLocation());
        Node parent = goal.getParent();
        while (parent != null) {
            if (parent.x == start.x && parent.y == start.y) {
                reachedStart = true;
            }
            path.addPoint(parent.getLocation());
            parent = parent.getParent();
        }

        if (reachedStart) {
            return path;
        } else {
            return null;
        }
    }

    /**
     * Algoritmit.
     */
    public enum Type {

        A_STAR(AStar.class, "A*"),
        DIJKSTRA(Dijkstra.class, "Dijkstra"),
        JUMP_POINT_SEARCH(JumpPointSearch.class, "Jump point search"),
        BREADTH_FIRST_SEARCH(BreadthFirstSearch.class, "Breadth-first search"),
        DEPTH_FIRST_SEARCH(DepthFirstSearch.class, "Depth-first search");

        private final Class<? extends PathfindingAlgorithm> clazz;
        private final String name;

        private Type(Class<? extends PathfindingAlgorithm> clazz, String name) {
            this.clazz = clazz;
            this.name = name;
        }

        /**
         * Luo algoritmista olion.
         *
         * @return Algoritmi
         */
        public PathfindingAlgorithm getInstance() {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Type.class.getName()).log(Level.SEVERE, null, ex);
                throw null;
            }
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
