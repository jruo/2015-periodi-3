package com.hiilimonoksidi.tiralabra.misc;

/**
 * Suunta.
 * 
 * @author Janne Ruoho
 */
public enum Direction {

    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0),
    NORTHEAST(1, -1),
    NORTHWEST(-1, -1),
    SOUTHEAST(1, 1),
    SOUTHWEST(-1, 1);

    /**
     * Suuntavektorin komponentit
     */
    public final int dx, dy;

    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
    
    public static Direction getDirection(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            return y1 < y2 ? SOUTH : NORTH;
        }
        if (y1 == y2) {
            return x1 < x2 ? EAST : WEST;
        }
        if (x1 < x2) {
            return y1 < y2 ? SOUTHEAST : NORTHEAST;
        }
        return y1 < y2 ? SOUTHWEST : NORTHWEST;
    }
}
