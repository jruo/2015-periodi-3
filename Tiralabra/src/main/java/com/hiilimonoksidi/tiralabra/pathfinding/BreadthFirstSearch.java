package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.datastructures.HashSet;
import com.hiilimonoksidi.tiralabra.datastructures.Queue;
import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;

/**
 * Breadth-first search -hakualgoritmi.
 *
 * @author Janne Ruoho
 */
public class BreadthFirstSearch extends PathfindingAlgorithm {

    /**
     * Käsittelyä odottavat solmut
     */
    private Queue<Node> open;

    /**
     * Käsitellyt solmut.
     */
    private HashSet<Node> closed;

    @Override
    protected void init() {
        open = new Queue<>();
        closed = new HashSet<>();
    }

    @Override
    public Path search() {
        Node startNode = graph.get(start);
        closed.add(startNode);
        open.add(startNode);

        int gx = goal.x;
        int gy = goal.y;

        while (!open.isEmpty() && !stopped) {
            Node current = open.remove();

            int cx = current.x;
            int cy = current.y;

            if (cx == gx && cy == gy) {
                return reconstructPath(current);
            }

            for (Node neighbor : graph.getNeighbors(cx, cy)) {
                if (neighbor == null || closed.contains(neighbor)) {
                    continue;
                }

                neighbor.setParent(current);
                closed.add(neighbor);
                open.add(neighbor);
            }
        }

        return null;
    }
}
