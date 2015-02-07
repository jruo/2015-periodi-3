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
    public boolean step() {
        Node current = open.pop();
        closed.add(current);

        int cx = current.x;
        int cy = current.y;

        if (cx == gx && cy == gy) {
            path = reconstructPath(current);
            return true;
        }

        for (Node neighbor : graph.getOrthogonalNeighbors(cx, cy)) {
            if (neighbor == null) {
                continue;
            }

            if (!closed.contains(neighbor)) {
                neighbor.setParent(current);
                open.push(neighbor);
            }
        }

        return false;
    }

    @Override
    public boolean hasNextStep() {
        return !open.isEmpty();
    }

    @Override
    public Iterable<Node> getOpenNodes() {
        throw new UnsupportedOperationException(); // TODO: implement
    }

    @Override
    public Iterable<Node> getClosedNodes() {
        return closed;
    }

}
