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
    public boolean searchStep() {
        Node current = openQueue.remove();

        doJumps(current);

        return finishStep(current);
    }

    /**
     * Suorittaa hypyt.
     *
     * @param current Solmu josta hypätään
     */
    private void doJumps(Node current) {
        cx = current.x;
        cy = current.y;

        for (Node neighbor : graph.getJumpableNeighbors(cx, cy, jumpDirections[cy][cx])) {
            if (neighbor == null) {
                continue;
            }

            Direction neighborDirection = Direction.getDirection(cx, cy, neighbor.x, neighbor.y);

            Node jumped = jump(neighbor.x, neighbor.y, neighborDirection);

            if (jumped != null) {
                processJump(current, jumped, neighborDirection);
            }
        }
    }

    /**
     * Käsittelee hypyn tuloksen.
     *
     * @param current Solmu josta hypättiin
     * @param jumped Solmu johon hypättiin
     * @param neighborDirection Suunta johon hypättiin
     */
    private void processJump(Node current, Node jumped, Direction neighborDirection) {
        int jx = jumped.x;
        int jy = jumped.y;

        if (!graph.isValidNode(jx, jy)) {
            return;
        }

        float jumpedDistance = distance[cy][cx] + Calc.dist(cx, cy, jx, jy);
        boolean jumpedOpen = open.contains(jumped);

        if (!jumpedOpen || jumpedDistance < distance[jy][jx]) {
            jumped.setParent(current);

            distance[jy][jx] = jumpedDistance;
            heuristicDistance[jy][jx] = jumpedDistance + Calc.dist(jx, jy, gx, gy);
            jumpDirections[jy][jx] = neighborDirection;

            if (jumpedOpen) {
                openQueue.update(jumped);
            } else {
                openQueue.add(jumped);
            }
            open.add(jumped);
        }
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
