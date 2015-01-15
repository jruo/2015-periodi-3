package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.graph.Graph;
import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;
import com.hiilimonoksidi.tiralabra.misc.Point;

/**
 * Yliluokka reitinensintäalgoritmeille.
 *
 * @author Janne Ruoho
 */
public abstract class PathfindingAlgorithm {

    /**
     * Verkko, josta etsitään reitti
     */
    protected Graph graph;

    public PathfindingAlgorithm(Graph graph) {
        this.graph = new Graph(graph);
    }

    /**
     * Alustaa algoritmin tarvitsevat rakenteet. Kutsuttava ennen search-metodin
     * kutsumista
     */
    public abstract void init();

    /**
     * Aloittaa reitinetsinnän.
     *
     * @param start Reitin aloituspiste
     * @param end Reitin lopetuspiste
     * @return Polun aloituspisteestä lopetuspisteeseen, jos reitti on olemassa.
     * Jos reittiä ei ole, palauttaa null.
     */
    public abstract Path search(Point start, Point end);

    /**
     * Rakentaa polun annetusta solmusta taaksepäin aloituspisteen solmuun
     * seuraamalla solmujen edeltäjiä.
     *
     * @param goal Solmu, josta polkua lähdetään rakentamaan
     * @return Polku annetusta solmusta aloituspisteen solmuun.
     */
    protected Path reconstructPath(Node goal) {
        Path path = new Path();
        path.addPoint(goal.getLocation());

        Node parent = goal.getParent();
        while (parent != null) {
            path.addPoint(parent.getLocation());
            parent = parent.getParent();
        }

        return path;
    }
}
