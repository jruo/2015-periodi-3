package com.hiilimonoksidi.tiralabra.graph;

import com.hiilimonoksidi.tiralabra.misc.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Polku, joka koostuu pisteistä. Käytetään reitinensintäalgoritmien tuloksen
 * palauttamiseen
 *
 * @author Janne Ruoho
 */
public class Path {

    private final float SQRT_2 = 1.41421356237f;
    private final List<Point> points;
    private float length = -1;

    public Path() {
        points = new ArrayList<>();
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public List<Point> getPoints() {
        return points;
    }

    /**
     * Palauttaa polun pituuden.
     * 
     * @return Polun pituus
     */
    public float getLength() {
        if (length == -1) {
            float len = 0;
            for (int i = 1; i < points.size(); i++) {
                Point p1 = points.get(i);
                Point p2 = points.get(i - 1);
                len += p1.x == p2.x || p1.y == p2.y ? 1 : SQRT_2;
            }
            length = len;
        }
        return length;
    }
}
