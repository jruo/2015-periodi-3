package com.hiilimonoksidi.tiralabra.graph;

import com.hiilimonoksidi.tiralabra.misc.Point;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class JumpPointPathTest {

    @Test
    public void test() {
        Path p = new JumpPointPath();
        p.addPoint(new Point(0, 0));
        p.addPoint(new Point(4, 0));
        p.addPoint(new Point(7, 3));
        p.addPoint(new Point(12, 3));
        p.addPoint(new Point(13, 2));
        p.addPoint(new Point(13, -4));

        assertEquals(20.6568542495f, p.getLength(), 0.00001f);
    }
    
}
