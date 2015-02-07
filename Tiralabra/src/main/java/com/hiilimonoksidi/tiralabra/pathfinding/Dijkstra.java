package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.datastructures.HashSet;
import com.hiilimonoksidi.tiralabra.datastructures.Heap;
import com.hiilimonoksidi.tiralabra.graph.Node;
import java.util.Comparator;

/**
 * Dijkstran algoritmi.
 *
 * @author Janne Ruoho
 */
public class Dijkstra extends PathfindingAlgorithm {

    /**
     * Etäisyys lähtöpisteestä tiettyyn solmuun.
     */
    private float d[][];

    /**
     * Prioriteettijono, joka sisältää kaikki verkon solmut
     * paremmuusjärjestyksessä (Pienin d-arvo ensin).
     */
    private Heap<Node> nodes;

    /**
     * Joukko, joka sisältää kaikki läpi käydyt solmut.
     */
    private HashSet<Node> checked;

    @Override
    public void init() {
        int width = graph.width;
        int height = graph.height;

        d = new float[height][width];

        nodes = new Heap<>(new NodeComparator());
        checked = new HashSet<>();

        d[start.y][start.x] = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Node node = graph.get(j, i);
                if (!node.clear) {
                    continue;
                }
                if (j != start.x || i != start.y) {
                    d[i][j] = Float.MAX_VALUE;
                }
                nodes.add(graph.get(j, i));
            }
        }
    }

    @Override
    public boolean step() {
        Node current = nodes.remove();
        checked.add(current);

        int cx = current.x;
        int cy = current.y;

        if (cx == gx && cy == gy) {
            path = reconstructPath(current);
            return true;
        }

        for (Node neighbor : graph.getNeighbors(current)) {
            if (neighbor == null) {
                continue;
            }
            if (!checked.contains(neighbor)) {
                int nx = neighbor.x;
                int ny = neighbor.y;

                float dTest = d[cy][cx] + (cx == nx || cy == ny ? 1 : SQRT_2);
                if (dTest < d[ny][nx]) {
                    d[ny][nx] = dTest;
                    neighbor.setParent(current);

                    nodes.update(neighbor);
                }
            }
        }

        return false;
    }

    @Override
    public boolean hasNextStep() {
        return !nodes.isEmpty();
    }

    @Override
    public Iterable<Node> getOpenNodes() {
        throw new UnsupportedOperationException(); // TODO: implement
    }

    @Override
    public Iterable<Node> getClosedNodes() {
        return checked;
    }

    /**
     * Vertailee kahden solmun järjestystä. Ensimmäinen solmu on se, joka on
     * lähempänä lähtöpistettä (pienempi d).
     */
    private class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            float f1 = d[o1.y][o1.x];
            float f2 = d[o2.y][o2.x];
            return (int) Math.signum(f1 - f2);
        }
    }
}
