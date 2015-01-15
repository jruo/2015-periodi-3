package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.graph.Graph;
import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;
import com.hiilimonoksidi.tiralabra.misc.Point;

public abstract class PathfindingAlgorithm {

    protected Graph graph;

    public PathfindingAlgorithm(Graph graph) {
        this.graph = graph;
    }

    public abstract Path search(Point start, Point end);

    protected Path reconstructPath(Node goal) {
        Path path = new Path();
        path.addPoint(goal.getLocation());

        Node parent = goal.getParent();
        while (parent != null) {
            path.addPoint(parent.getLocation());
            parent = parent.getParent();
        }

        return path;
    }
}
