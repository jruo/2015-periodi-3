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
    
    /**
     * Onko tämä suunta pysty- tai vaakasuuntainen.
     * 
     * @return Tosi/epätosi
     */
    public boolean isOrthogonal() {
        return dx == 0 || dy == 0;
    }
    
    /**
     * Onko tämä suunta vinottain.
     * 
     * @return Tosi/epätosi
     */
    public boolean isDiagonal() {
        return !isOrthogonal();
    }

    /**
     * Palauttaa suunnan, joka vastaa suuntavektoria [x2-x1, y2-y1]. Jos
     * suuntavektori ei ole yhdensuuntainen minkään suunnan kanssa, saattaa
     * palauttaa oikean tai sitten väärän tuloksen.
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return Vastaava suunta
     */
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
