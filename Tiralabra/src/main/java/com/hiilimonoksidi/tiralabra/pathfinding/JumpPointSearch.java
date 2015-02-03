package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.graph.JumpPointPath;
import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;
import com.hiilimonoksidi.tiralabra.misc.Calc;
import com.hiilimonoksidi.tiralabra.misc.Direction;

/**
 * Jump point search, optimoitu versio A*:sta.
 *
 * @author Janne Ruoho
 */
public class JumpPointSearch extends AStar {

    /**
     * Suunta, josta saavuttiin tiettyyn solmuun
     */
    private Direction[][] jumpDirections;

    @Override
    public void init() {
        super.init();

        jumpDirections = new Direction[graph.height][graph.width];
    }

    @Override
    public Path search() {
        Node startNode = graph.get(start);
        openQueue.add(startNode);
        open.add(startNode);

        int gx = goal.x;
        int gy = goal.y;

        while (!openQueue.isEmpty()) {
            Node current = openQueue.remove();

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
                            openQueue.update(jumped);
                        } else {
                            openQueue.add(jumped);
                        }
                        open.add(jumped);
                    }
                }
            }
        }

        return null;
    }

    /**
     * Suorittaa rekursiivisesti hypyn annetuista koordinaateista annettuun
     * suuntaan.
     *
     * @param x Hypättävän kohdan x-koordinaatti
     * @param y Hypättävän kohdan y-koordinaatti
     * @param direction Hypättävä suunta
     * @return Jos hyppy löysi kiinnostavan solmun (maalin tai pakotetun
     * naapurin), palauttaa solmun jota kautta pääsee kyseiseen solmuun. Jos
     * hyppy ei löytänyt mielenkiintoista solmua (törmäsi suoraan seinään tai
     * verkon reunaan), palauttaa null.
     */
    private Node jump(int x, int y, Direction direction) {
        Node current = graph.get(x, y);

        if (x == goal.x && y == goal.y) {
            return current;
        }

        int dx = direction.dx;
        int dy = direction.dy;

        if (graph.hasForcedNeighbors(x, y, direction)) {
            return current;
        }

        boolean continueJump;

        if (dx != 0 && dy != 0) {
            Node hJump = jump(x + dx, y, Direction.getDirection(0, 0, dx, 0));
            Node vJump = jump(x, y + dy, Direction.getDirection(0, 0, 0, dy));

            if (hJump != null || vJump != null) {
                return current;
            }

            continueJump = graph.get(x + dx, y).clear || graph.get(x, y + dy).clear;
        } else {
            continueJump = graph.get(x + dx, y + dy).clear;
        }

        if (continueJump) {
            return jump(x + dx, y + dy, Direction.getDirection(0, 0, dx, dy));
        }

        return null;
    }

    @Override
    public Path reconstructPath(Node goal) {
        return reconstructPath(goal, new JumpPointPath());
    }
}
