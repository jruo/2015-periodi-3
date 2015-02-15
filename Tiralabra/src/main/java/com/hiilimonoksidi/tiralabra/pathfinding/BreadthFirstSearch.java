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
     * Käsitellyt ja käsittelyä odottavat solmut
     */
    private HashSet<Node> checked;
    
    /**
     * Käsitellyt solmut
     */
    private HashSet<Node> closed;

    @Override
    protected void init() {
        open = new Queue<>();
        checked = new HashSet<>();
        closed = new HashSet<>();

        Node startNode = graph.get(start);
        checked.add(startNode);
        open.enqueue(startNode);
    }

    @Override
    public boolean searchStep() {
        Node current = open.dequeue();
        closed.add(current);

        processNeighbors(current);

        return finishStep(current);
    }

    private void processNeighbors(Node current) {
        for (Node neighbor : graph.getNeighbors(current)) {
            if (neighbor == null || checked.contains(neighbor)) {
                continue;
            }

            neighbor.setParent(current);
            checked.add(neighbor);
            open.enqueue(neighbor);
        }
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
