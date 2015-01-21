package com.hiilimonoksidi.tiralabra.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class PointTest {
    
    @Test
    public void testStringConstructor() {
        Point p = new Point("10,15");
        assertEquals(10, p.x);
        assertEquals(15, p.y);
        
        try {
            p = new Point("10, 12");
            assertTrue(false);
        } catch (Exception e) {
        }
        
        try {
            p = new Point("7,,12");
            assertTrue(false);
        } catch (Exception e) {
        }
        
        try {
            p = new Point("asd");
            assertTrue(false);
        } catch (Exception e) {
        }
        
        assertEquals(10, p.x);
        assertEquals(15, p.y);
    }

    
}
