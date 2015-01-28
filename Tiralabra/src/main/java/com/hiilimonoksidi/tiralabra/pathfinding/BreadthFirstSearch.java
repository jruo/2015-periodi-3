package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

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
    private Set<Node> closed;

    @Override
    protected void init() {
        open = new LinkedList<>();
        closed = new HashSet<>();
    }

    @Override
    public Path search() {
        Node startNode = graph.get(start);
        closed.add(startNode);
        open.offer(startNode);

        int gx = goal.x;
        int gy = goal.y;

        while (!open.isEmpty() && !stopped) {
            Node current = open.poll();

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
                open.offer(neighbor);
            }
        }

        return null;
    }
}
