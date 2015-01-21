package com.hiilimonoksidi.tiralabra.misc;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class CalcTest {

    @Test
    public void testDist() {
        assertEquals(1, Calc.dist(0, 0, 0, 1), 0);
        assertEquals(2, Calc.dist(0, 0, 2, 0), 0);
        assertEquals(1.41421356237f, Calc.dist(0, 0, 1, 1), 0.0000001f);
        assertEquals(3 * 1.41421356237f, Calc.dist(0, 0, 3, 3), 0.0000001f);
        assertEquals(3 * 1.41421356237f, Calc.dist(new Point(0, 0), new Point(3, 3)), 0.0000001f);
    }

}
