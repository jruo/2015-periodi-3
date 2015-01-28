package com.hiilimonoksidi.tiralabra.graph;

import com.hiilimonoksidi.tiralabra.misc.Direction;
import com.hiilimonoksidi.tiralabra.misc.Point;

/**
 * Polku jump point searchille. Rakentaa kokonaisen polun sille annetuista
 * hyppypisteistä.
 *
 * @author Janne Ruoho
 */
public class JumpPointPath extends Path {

    /**
     * Lisää polkuun pisteen. Lisättävän pisteen tätyy olla samalla linjalla
     * viimeisimmäksi lisätyn pisteen kanssa joko vaaka- pysty tai
     * vinosuunnassa, jotta koko polku voidaan laskea. Muuten metodi jää
     * ikuiseen silmukkaan.
     *
     * @param p Lisättävä piste
     */
    @Override
    public void addPoint(Point p) {
        if (!points.isEmpty()) {
            Point last = points.get(points.size() - 1);
            Direction direction = Direction.getDirection(last.x, last.y, p.x, p.y);

            int dx = direction.dx;
            int dy = direction.dy;
            int x = last.x + dx;
            int y = last.y + dy;

            while (x != p.x || y != p.y) {
                points.add(new Point(x, y));

                x += dx;
                y += dy;
            }
        }

        points.add(p);
    }
}
