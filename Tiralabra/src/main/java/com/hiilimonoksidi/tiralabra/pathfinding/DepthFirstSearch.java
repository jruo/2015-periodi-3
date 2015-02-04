package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.datastructures.HashSet;
import com.hiilimonoksidi.tiralabra.datastructures.Stack;
import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;

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
    }

    @Override
    public Path search() {
        open.push(graph.get(start));

        while (!open.isEmpty() && !stopped) {
            Node current = open.pop();
            closed.add(current);

            int cx = current.x;
            int cy = current.y;

            if (cx == gx && cy == gy) {
                return reconstructPath(current);
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
        }

        return null;
    }
}
