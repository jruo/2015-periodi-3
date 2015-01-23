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

            for (Node neighbor : graph.getJumpableNeighbors(cx, cy, jumpDirections[cy][cx])) {
                if (neighbor == null) {
                    continue;
                }

                Direction neighborDirection = Direction.getDirection(cx, cy, neighbor.x, neighbor.y);

                Node jumped = jump(neighbor.x, neighbor.y, neighborDirection);

                if (jumped != null) {
                    int jx = jumped.x;
                    int jy = jumped.y;

                    float gJumped = g[cy][cx] + Calc.dist(cx, cy, jx, jy);

                    boolean jumpedOpen = open.contains(jumped);
                    if (!jumpedOpen || gJumped < g[jy][jx]) {
                        jumped.setParent(current);

                        g[jy][jx] = gJumped;
                        f[jy][jx] = gJumped + Calc.dist(jx, jy, gx, gy);
                        jumpDirections[jy][jx] = neighborDirection;

                        if (jumpedOpen) {
                            openQueue.remove(jumped);
                        }
                        openQueue.offer(jumped);
                        open.add(jumped);
                    }
                }
            }
        }

        return null;
    }

    private Node jump(int x, int y, Direction direction) {
        Node current = graph.get(x, y);
        
        if (x == goal.x && y == goal.y) {
            return current;
        }

        int dx = direction.dx;
        int dy = direction.dy;

        if (dx == 0 || dy == 0) { // Pysty/vaakasuuntainen hyppy
            if (dx == 0) {
                // Forced neighbor?
                if ((graph.get(x + 1, y + dy).clear && !graph.get(x + 1, y).clear)
                    || graph.get(x - 1, y + dy).clear && !graph.get(x - 1, y).clear) {
                    return current;
                }
            } else {
                // Forced neighbor?
                if ((graph.get(x + dx, y + 1).clear && !graph.get(x, y + 1).clear)
                    || graph.get(x + dx, y - 1).clear && !graph.get(x, y - 1).clear) {
                    return current;
                }
            }

            // Jatketaan hyppyä
            if (graph.get(x + dx, y + dy).clear) {
                return jump(x + dx, y + dy, Direction.getDirection(0, 0, dx, dy));
            }

        } else { // Diagonaalinen hyppy

            // Forced neighbor?
            if ((graph.get(x - dx, y + dy).clear && !graph.get(x - dx, y).clear)
                || graph.get(x + dx, y - dx).clear && !graph.get(x, y - dy).clear) {
                return current;
            }

            // Hypätään vaakaan ja pystyyn
            Node hJump = jump(x + dx, y, Direction.getDirection(0, 0, dx, 0));
            Node vJump = jump(x, y + dy, Direction.getDirection(0, 0, 0, dy));

            // Jos vaaka/pystyhypyt löysivät jotain, tämä solmu on mielenkiintoinen
            if (hJump != null || vJump != null) {
                return current;
            }

            // Jatketaan hyppyä
            if (graph.get(x + dx, y).clear || graph.get(x, y + dy).clear) {
                return jump(x + dx, y + dy, Direction.getDirection(0, 0, dx, dy));
            }
        }

        // Törmättiin seinään
        return null;
    }

    @Override
    public Path reconstructPath(Node goal) {
        return super.reconstructPath(goal);
    }
}
