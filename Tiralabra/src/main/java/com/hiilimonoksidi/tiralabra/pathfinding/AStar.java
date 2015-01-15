package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.graph.Graph;
import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;
import com.hiilimonoksidi.tiralabra.misc.Calc;
import com.hiilimonoksidi.tiralabra.misc.Point;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * A*-hakualgoritmin implementaatio.
 *
 * @author Janne Ruoho
 */
public class AStar extends PathfindingAlgorithm {

    /**
     * Etäisyys tietystä solmusta alkupisteeseen.
     */
    private float[][] g;

    /**
     * Arvioitu etäisyys tietystä solmusta maaliin plus sen solmun g-arvo.
     */
    private float[][] f;

    /**
     * Joukko, joka sisältää jo tutkitut solmut.
     */
    private Set<Node> closed;

    /**
     * Joukko, joka sisältää aina samat solmut kuin openQueue. Nopeuttaa
     * sisältyvyyden tarkistusta.
     */
    private Set<Node> open;

    /**
     * Prioriteettijono, joka sisältää solmut, jotka odottavat tutkimisvuoroa.
     */
    private PriorityQueue<Node> openQueue;

    public AStar(Graph graph) {
        super(graph);
    }

    @Override
    public void init() {
        g = new float[graph.height][graph.width];
        f = new float[graph.height][graph.width];

        closed = new HashSet<>();
        open = new HashSet<>();
        openQueue = new PriorityQueue<>(new NodeComparator());
    }

    @Override
    public Path search(Point start, Point goal) {
        Node startNode = graph.get(start);
        openQueue.offer(startNode);
        open.add(startNode);

        int gx = goal.x;
        int gy = goal.y;

        while (!openQueue.isEmpty()) {
            Node current = openQueue.poll();

            open.remove(current);
            closed.add(current);

            int cx = current.x;
            int cy = current.y;

            if (cx == gx && cy == gy) {
                return reconstructPath(current);
            }

            for (Node neighbor : graph.getNeighbors(cx, cy)) {
                if (closed.contains(neighbor)) {
                    continue;
                }

                int nx = neighbor.x;
                int ny = neighbor.y;

                float gNeighbor = g[cy][cx] + Calc.dist(cx, cy, nx, ny);

                boolean neighborOpen = open.contains(neighbor);
                if (!neighborOpen || gNeighbor < g[ny][nx]) {
                    neighbor.setParent(current);

                    g[ny][nx] = gNeighbor;
                    f[ny][nx] = gNeighbor + Calc.dist(nx, ny, gx, gy);

                    if (!neighborOpen) {
                        openQueue.offer(neighbor);
                        open.add(neighbor);
                    }
                }
            }
        }

        return null;
    }

    /**
     * Vertailee kahden solmun järjestystä. Ensimmäinen solmu on se, jonka
     * kautta kulkee lyhempi matka alusta maaliin (pienempi f).
     */
    private class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            float f1 = f[o1.y][o1.x];
            float f2 = f[o2.y][o2.x];
            return (int) Math.signum(f1 - f2);
        }
    }
}
