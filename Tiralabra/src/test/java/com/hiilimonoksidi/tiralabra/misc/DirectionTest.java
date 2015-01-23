package com.hiilimonoksidi.tiralabra.misc;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class DirectionTest {

    @Test
    public void testGetDirection() {
        assertEquals(Direction.getDirection(0, 0, 1, 0), Direction.EAST);
        assertEquals(Direction.getDirection(0, 0, 10, 0), Direction.EAST);
        assertEquals(Direction.getDirection(-5, 0, 4, 0), Direction.EAST);
        assertEquals(Direction.getDirection(0, 0, 0, 1), Direction.SOUTH);
        assertEquals(Direction.getDirection(0, 0, -1, 0), Direction.WEST);
        assertEquals(Direction.getDirection(0, 0, 0, -1), Direction.NORTH);
        assertEquals(Direction.getDirection(0, 0, 1, 1), Direction.SOUTHEAST);
        assertEquals(Direction.getDirection(0, 0, 1, -1), Direction.NORTHEAST);
        assertEquals(Direction.getDirection(0, 0, -1, -1), Direction.NORTHWEST);
        assertEquals(Direction.getDirection(0, 0, -1, 1), Direction.SOUTHWEST);
    }
    
}
