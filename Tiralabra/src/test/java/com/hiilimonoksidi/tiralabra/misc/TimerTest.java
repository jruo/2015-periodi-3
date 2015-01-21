package com.hiilimonoksidi.tiralabra.misc;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class TimerTest {
    
    public TimerTest() {
    }

    @Test
    public void testTimer() throws InterruptedException {
        Timer t = new Timer();
        t.start();
        Thread.sleep(200);
        t.stop();
        
        assertTrue(t.getTime() > 150);
        assertTrue(t.getTime() < 250);
    }
}
