package com.hiilimonoksidi.tiralabra.graph;

import com.hiilimonoksidi.tiralabra.misc.Point;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class PathTest {

    @Test
    public void testGetLength() {
        Path p = new Path();
        p.addPoint(new Point(0, 0));
        p.addPoint(new Point(0, 1));
        p.addPoint(new Point(0, 2));
        p.addPoint(new Point(0, 3));
        p.addPoint(new Point(1, 4));
        p.addPoint(new Point(2, 3));
        p.addPoint(new Point(3, 3));

        assertEquals(6.82842712475f, p.getLength(), 0.000001f);
    }
}
