package com.hiilimonoksidi.tiralabra.misc;

/**
 * Apuluokka tiettyjen laskutoimitusten suorittamiseen.
 *
 * @author Janne Ruoho
 */
public class Calc {

    /**
     * Laskee etäisyyden kahden pisteen välillä.
     *
     * @param p1 Piste 1
     * @param p2 Piste 2
     * @return Etäisyys pisteiden välillä
     */
    public static float dist(Point p1, Point p2) {
        return dist(p1.x, p1.y, p2.x, p2.y);
    }

    public static float dist(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
