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
    private float distance[][];

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

        distance = new float[height][width];

        nodes = new Heap<>(new NodeComparator());
        checked = new HashSet<>();

        distance[start.y][start.x] = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Node node = graph.get(j, i);
                if (!node.clear) {
                    continue;
                }
                if (j != start.x || i != start.y) {
                    distance[i][j] = Float.MAX_VALUE;
                }
                nodes.add(graph.get(j, i));
            }
        }
    }

    @Override
    public boolean searchStep() {
        Node current = nodes.remove();
        checked.add(current);

        processNeighbors(current);

        return finishStep(current);
    }

    /**
     * Käsittelee solmun naapurit.
     * 
     * @param current Solmu
     */
    private void processNeighbors(Node current) {
        int cx = current.x;
        int cy = current.y;

        for (Node neighbor : graph.getNeighbors(cx, cy)) {
            if (neighbor == null) {
                continue;
            }
            if (!checked.contains(neighbor)) {
                int nx = neighbor.x;
                int ny = neighbor.y;

                float dTest = distance[cy][cx] + (cx == nx || cy == ny ? 1 : SQRT_2);
                if (dTest < distance[ny][nx]) {
                    distance[ny][nx] = dTest;
                    neighbor.setParent(current);

                    nodes.update(neighbor);
                }
            }
        }
    }

    @Override
    public boolean hasNextStep() {
        return !nodes.isEmpty();
    }

    @Override
    public Iterable<Node> getOpenNodes() {
        return emptySet;
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
            float f1 = distance[o1.y][o1.x];
            float f2 = distance[o2.y][o2.x];
            return (int) Math.signum(f1 - f2);
        }
    }
}
