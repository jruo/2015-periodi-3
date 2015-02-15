package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.datastructures.HashSet;
import com.hiilimonoksidi.tiralabra.datastructures.Stack;
import com.hiilimonoksidi.tiralabra.graph.Node;

/**
 * Syvyyssuuntainen hakualgoritmi. Tämä algoritmi ei useimmissa tapauksissa
 * löydä lyhintä reittiä, vaan se vaan ottaa aina askeleen eteenpäin kunnes
 * törmää umpikujaan, palaa takaisin sellaiseen pisteeseen josta voi taas
 * jatkaa, ja ennen pitkään törmää maaliin.
 *
 * @author Janne Ruoho
 */
public class DepthFirstSearch extends PathfindingAlgorithm {

    private Stack<Node> open;
    private HashSet<Node> closed;

    @Override
    protected void init() {
        open = new Stack<>();
        closed = new HashSet<>();

        open.push(graph.get(start));
    }

    @Override
    public boolean searchStep() {
        Node current = open.pop();
        closed.add(current);

        processNeighbors(current);

        return finishStep(current);
    }

    private void processNeighbors(Node current) {
        for (Node neighbor : graph.getOrthogonalNeighbors(current)) {
            if (neighbor == null) {
                continue;
            }

            if (!closed.contains(neighbor)) {
                neighbor.setParent(current);
                open.push(neighbor);
            }
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
