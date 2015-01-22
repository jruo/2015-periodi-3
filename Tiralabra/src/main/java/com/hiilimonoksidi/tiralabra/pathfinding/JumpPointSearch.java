package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;

public class JumpPointSearch extends AStar {

    @Override
    public void init() {
        super.init();
    }

    @Override
    public Path search() {
        Node startNode = graph.get(start);
        openQueue.offer(startNode);
        open.add(startNode);

        int gx = goal.x;
        int gy = goal.y;

        while (!openQueue.isEmpty()) {
            Node current = openQueue.poll();
            
            int cx = current.x;
            int cy = current.y;
        }

        return null;
    }
    
}
