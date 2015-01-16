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
    
    /**
     * Reitinetsinnän aloitus- ja lopetuspisteet
     */
    protected Point start, goal;

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
        init();
    }
    
    /**
     * Alustaa algoritmin käyttämät tietorakenteet.
     */
    protected abstract void init();

    /**
     * Aloittaa reitinetsinnän.
     *
     * @return Polun aloituspisteestä lopetuspisteeseen, jos reitti on olemassa.
     * Jos reittiä ei ole, palauttaa null.
     */
    public abstract Path search();

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
