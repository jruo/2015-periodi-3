package com.hiilimonoksidi.tiralabra.misc;

/**
 * Piste.
 *
 * @author Janne Ruoho
 */
public class Point {

    /**
     * Pisteen sijainti
     */
    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Luo uuden Point-olion merkkijonosta. Merkkijonon on oltava muotoa "x,y".
     *
     * @param location Pisteen sijainti
     */
    public Point(String location) {
        int comma = location.indexOf(",");
        String s1 = location.substring(0, comma);
        String s2 = location.substring(comma + 1, location.length());
        try {
            x = Integer.parseInt(s1);
            y = Integer.parseInt(s2);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid coordinates: " + location);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.x;
        hash = 11 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }
}
