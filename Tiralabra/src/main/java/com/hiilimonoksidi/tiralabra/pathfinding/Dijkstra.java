package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;
import com.hiilimonoksidi.tiralabra.misc.Calc;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

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
     * Jono, joka sisältää kaikki verkon solmut paremmuusjärjestyksessä (Pienin
     * d-arvo ensin).
     */
    private PriorityQueue<Node> nodes;

    /**
     * Joukko, joka sisältää kaikki läpi käydyt solmut.
     */
    private Set<Node> checked;

    @Override
    public void init() {
        d = new float[graph.height][graph.width];

        nodes = new PriorityQueue<>(new NodeComparator());
        checked = new HashSet<>();

        d[start.y][start.x] = 0;
        for (int i = 0; i < graph.height; i++) {
            for (int j = 0; j < graph.width; j++) {
                Node node = graph.get(j, i);
                if (!node.clear) {
                    continue;
                }
                if (j != start.x || i != start.y) {
                    d[i][j] = Float.MAX_VALUE;
                }
                nodes.offer(graph.get(j, i));
            }
        }
    }

    @Override
    public Path search() {
        int gx = goal.x;
        int gy = goal.y;

        while (!nodes.isEmpty()) {
            Node current = nodes.poll();
            checked.add(current);

            int cx = current.x;
            int cy = current.y;

            if (cx == gx && cy == gy) {
                return reconstructPath(current);
            }

            for (Node neighbor : graph.getNeighbors(current)) {
                if (!checked.contains(neighbor)) {
                    int nx = neighbor.x;
                    int ny = neighbor.y;

                    float dTest = d[cy][cx] + Calc.dist(cx, cy, nx, ny);
                    if (dTest < d[ny][nx]) {
                        d[ny][nx] = dTest;
                        neighbor.setParent(current);

                        // Päivitetään jono
                        nodes.remove(neighbor);
                        nodes.add(neighbor);
                    }
                }
            }
        }

        return null;
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
