package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.datastructures.HashSet;
import com.hiilimonoksidi.tiralabra.datastructures.Heap;
import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;
import com.hiilimonoksidi.tiralabra.misc.Calc;
import java.util.Comparator;

/**
 * A*-hakualgoritmi.
 *
 * @author Janne Ruoho
 */
public class AStar extends PathfindingAlgorithm {

    /**
     * Etäisyys tietystä solmusta alkupisteeseen.
     */
    protected float[][] g;

    /**
     * Arvioitu etäisyys tietystä solmusta maaliin plus sen solmun g-arvo.
     */
    protected float[][] f;

    /**
     * Joukko, joka sisältää jo tutkitut solmut.
     */
    protected HashSet<Node> closed;

    /**
     * Joukko, joka sisältää aina samat solmut kuin openQueue. Nopeuttaa
     * sisältyvyyden tarkistusta.
     */
    protected HashSet<Node> open;

    /**
     * Prioriteettijono, joka sisältää solmut, jotka odottavat tutkimisvuoroa.
     */
    protected Heap<Node> openQueue;

    @Override
    public void init() {
        g = new float[graph.height][graph.width];
        f = new float[graph.height][graph.width];

        closed = new HashSet<>();
        open = new HashSet<>();
        openQueue = new Heap<>(new NodeComparator());
    }

    @Override
    public Path search() {
        Node startNode = graph.get(start);
        openQueue.add(startNode);
        open.add(startNode);

        int gx = goal.x;
        int gy = goal.y;

        while (!openQueue.isEmpty() && !stopped) {
            Node current = openQueue.remove();

            open.remove(current);
            closed.add(current);

            int cx = current.x;
            int cy = current.y;

            if (cx == gx && cy == gy) {
                return reconstructPath(current);
            }

            for (Node neighbor : graph.getNeighbors(cx, cy)) {
                if (neighbor == null || closed.contains(neighbor)) {
                    continue;
                }

                int nx = neighbor.x;
                int ny = neighbor.y;

                float gNeighbor = g[cy][cx] + (cx == nx || cy == ny ? 1 : SQRT_2);

                boolean neighborOpen = open.contains(neighbor);
                if (!neighborOpen || gNeighbor < g[ny][nx]) {
                    neighbor.setParent(current);

                    g[ny][nx] = gNeighbor;
                    f[ny][nx] = gNeighbor + Calc.dist(nx, ny, gx, gy);

                    if (neighborOpen) {
                        openQueue.update(neighbor);
                    } else {
                        openQueue.add(neighbor);
                    }
                    open.add(neighbor);
                }
            }
        }

        return null;
    }

    /**
     * Vertailee kahden solmun järjestystä. Ensimmäinen solmu on se, jonka
     * kautta kulkee lyhempi matka alusta maaliin (pienempi f).
     */
    class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            float f1 = f[o1.y][o1.x];
            float f2 = f[o2.y][o2.x];
            return (int) Math.signum(f1 - f2);
        }
    }
}
