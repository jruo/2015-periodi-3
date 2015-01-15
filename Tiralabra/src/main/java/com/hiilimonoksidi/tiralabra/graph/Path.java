package com.hiilimonoksidi.tiralabra.graph;

import com.hiilimonoksidi.tiralabra.misc.Point;
import java.util.ArrayList;
import java.util.List;

public class Path {

    private final List<Point> points;

    public Path() {
        points = new ArrayList<>();
    }
    
    public void addPoint(Point p) {
        points.add(p);
    }
    
    public List<Point> getPoints() {
        return points;
    }
}