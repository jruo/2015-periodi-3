package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.datastructures.HashSet;
import com.hiilimonoksidi.tiralabra.datastructures.Queue;
import com.hiilimonoksidi.tiralabra.graph.Node;

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

        Node startNode = graph.get(start);
        closed.add(startNode);
        open.enqueue(startNode);
    }

    @Override
    public boolean searchStep() {
        Node current = open.dequeue();

        int cx = current.x;
        int cy = current.y;

        if (cx == gx && cy == gy) {
            path = reconstructPath(current);
            return true;
        }

        for (Node neighbor : graph.getNeighbors(cx, cy)) {
            if (neighbor == null || closed.contains(neighbor)) {
                continue;
            }

            neighbor.setParent(current);
            closed.add(neighbor);
            open.enqueue(neighbor);
        }

        return false;
    }

    @Override
    public boolean hasNextStep() {
        return !open.isEmpty();
    }

    @Override
    public Iterable<Node> getOpenNodes() {
        return open;
    }

    @Override
    public Iterable<Node> getClosedNodes() {
        return closed;
    }
}
