package com.hiilimonoksidi.tiralabra.graph;

import com.hiilimonoksidi.tiralabra.misc.Direction;
import com.hiilimonoksidi.tiralabra.misc.Point;

public class JumpPointPath extends Path {

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
