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

public class AStar extends PathfindingAlgorithm {

    private float[][] g; // Etäisyys alusta tiettyyn solmuun
    private float[][] f; // Arvioitu etäisyys tietystä soulmusta maaliin plus sen solmun g
    private Set<Node> closed;
    private Set<Node> open;
    private PriorityQueue<Node> openQueue;

    public AStar(Graph graph) {
        super(graph);

        g = new float[graph.height][graph.width];
        f = new float[graph.height][graph.width];
        
        closed = new HashSet<>();
        open = new HashSet<>(); // Nopeuttaa sisältyvyyden tarkistusta
        openQueue = new PriorityQueue<>(new NodeComparator());
    }

    @Override
    public Path search(Point start, Point goal) {
        Node startNode = graph.get(start);
        openQueue.offer(startNode); // Lisätään alku tutkittavien solmujen joukkoon
        open.add(startNode);
        
        int gx = goal.x;
        int gy = goal.y;

        while (!openQueue.isEmpty()) { // Kunnes ei enää tutkittavaa
            Node current = openQueue.poll();
            
            open.remove(current);
            closed.add(current);

            int cx = current.x;
            int cy = current.y;

            if (cx == gx && cy == gy) { // Nykyisen x ja y == maalin x ja y
                return reconstructPath(current);
            }

            for (Node neighbor : graph.getNeighbors(cx, cy)) { // Tutkitaan naapurit
                if (closed.contains(neighbor)) {
                    continue; // Naapuri jo tutkittu
                }

                int nx = neighbor.x;
                int ny = neighbor.y;

                float gNeighbor = g[cy][cx] + Calc.dist(cx, cy, nx, ny); // g(naapuri) = g(tämä) + dist(tämä, naapuri)

                boolean neighborOpen = open.contains(neighbor);
                if (!neighborOpen || gNeighbor < g[ny][nx]) { // Jos naapuria ei jo tutkita, tai tunnetaan parempi reitti
                    neighbor.setParent(current);

                    g[ny][nx] = gNeighbor; // Päivitä naapurin g
                    f[ny][nx] = gNeighbor + Calc.dist(nx, ny, gx, gy); // Ja naapurin f

                    if (!neighborOpen) {
                        openQueue.offer(neighbor);
                        open.add(neighbor);
                    }
                }
            }
        }

        return null;
    }

    private class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            float f1 = f[o1.y][o1.x];
            float f2 = f[o2.y][o2.x];
            return (int) Math.signum(f1 - f2);
        }
    }
}
