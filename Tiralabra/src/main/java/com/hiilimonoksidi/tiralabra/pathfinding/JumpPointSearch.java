package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;
import com.hiilimonoksidi.tiralabra.misc.Calc;
import com.hiilimonoksidi.tiralabra.misc.Direction;

public class JumpPointSearch extends AStar {

    private Direction[][] jumpDirections;

    @Override
    public void init() {
        super.init();

        jumpDirections = new Direction[graph.height][graph.width];
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
            
            if (cx == gx && cy == gy) {
                return reconstructPath(current);
            }

            for (Node neighbor : graph.getJumpableNeighbors(cx, cx, jumpDirections[cy][cx])) {
                Direction neighborDirection = Direction.getDirection(cx, cy, neighbor.x, neighbor.y);

                Node jumped = jump(neighbor.x, neighbor.y, neighborDirection);

                if (jumped != null) {
                    int jx = jumped.x;
                    int jy = jumped.y;

                    float gJumped = g[cy][cx] + Calc.dist(cx, cy, jx, jy);

                    boolean jumpedOpen = open.contains(jumped);
                    if (!jumpedOpen || gJumped < g[jy][jx]) {
                        g[jy][jx] = gJumped;
                        f[jy][jx] = gJumped + Calc.dist(jx, jy, gx, gy);
                        jumpDirections[jy][jx] = neighborDirection;
                        
                        if (!jumpedOpen) {
                            openQueue.offer(jumped);
                            open.add(jumped);
                        }
                    }
                }
            }
        }

        return null;
    }

    private Node jump(int x, int y, Direction direction) {
        return null;
    }
    
    @Override
    public Path reconstructPath(Node goal) {
        return null;
    }
}
