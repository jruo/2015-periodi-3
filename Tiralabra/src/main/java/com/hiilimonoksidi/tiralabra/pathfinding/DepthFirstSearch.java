package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.datastructures.HashSet;
import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;
import java.util.Stack;

/**
 * Syvyyssuuntainen hakualgoritmi. Tämä algoritmi ei useimmissa tapauksissa
 * löydä lyhintä reittiä, vaan se vaan ottaa aina askeleen eteenpäin kunnes
 * törmää umpikujaan, palaa takaisin sellaiseen pisteeseen josta voi taas
 * jatkaa, ja ennen pitkään törmää maaliin.
 *
 * @author Janne Ruoho
 */
public class DepthFirstSearch extends PathfindingAlgorithm {

    private Stack<Node> stack;
    private HashSet<Node> closed;

    @Override
    protected void init() {
        stack = new Stack<>();
        closed = new HashSet<>();
    }

    @Override
    public Path search() {
        stack.push(graph.get(start));

        while (!stack.isEmpty() && !stopped) {
            Node current = stack.pop();
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
                    stack.push(neighbor);
                }
            }
        }

        return null;
    }
}
