package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.datastructures.HashSet;
import com.hiilimonoksidi.tiralabra.datastructures.Heap;
import com.hiilimonoksidi.tiralabra.graph.Node;
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
    protected float[][] distance;

    /**
     * Arvioitu etäisyys tietystä solmusta maaliin plus sen solmun g-arvo.
     */
    protected float[][] heuristicDistance;

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
        distance = new float[graph.height][graph.width];
        heuristicDistance = new float[graph.height][graph.width];

        closed = new HashSet<>();
        open = new HashSet<>();
        openQueue = new Heap<>(new NodeComparator());

        Node startNode = graph.get(start);
        openQueue.add(startNode);
        open.add(startNode);
    }

    @Override
    public boolean searchStep() {
        Node current = openQueue.remove();
        open.remove(current);
        closed.add(current);
        
        processNeighbors(current);

        return finishStep(current);
    }

    private void processNeighbors(Node current) {
        int cx = current.x;
        int cy = current.y;
        
        for (Node neighbor : graph.getNeighbors(cx, cy)) {
            if (neighbor == null || closed.contains(neighbor)) {
                continue;
            }

            int nx = neighbor.x;
            int ny = neighbor.y;

            float neighborDistance = distance[cy][cx] + (cx == nx || cy == ny ? 1 : SQRT_2);

            boolean neighborOpen = open.contains(neighbor);
            if (!neighborOpen || neighborDistance < distance[ny][nx]) {
                neighbor.setParent(current);

                distance[ny][nx] = neighborDistance;
                heuristicDistance[ny][nx] = neighborDistance + Calc.dist(nx, ny, gx, gy);

                if (neighborOpen) {
                    openQueue.update(neighbor);
                } else {
                    openQueue.add(neighbor);
                }
                open.add(neighbor);
            }
        }
    }

    @Override
    public boolean hasNextStep() {
        return !openQueue.isEmpty();
    }

    @Override
    public Iterable<Node> getOpenNodes() {
        return open;
    }

    @Override
    public Iterable<Node> getClosedNodes() {
        return closed;
    }

    /**
     * Vertailee kahden solmun järjestystä. Ensimmäinen solmu on se, jonka
     * kautta kulkee lyhempi matka alusta maaliin (pienempi f).
     */
    class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node node1, Node node2) {
            float d1 = heuristicDistance[node1.y][node1.x];
            float d2 = heuristicDistance[node2.y][node2.x];
            return (int) Math.signum(d1 - d2);
        }
    }
}
