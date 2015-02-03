package com.hiilimonoksidi.tiralabra.datastructures;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class QueueTest {

    Queue<String> q;

    public QueueTest() {
        q = new Queue<>();
    }

    @Test
    public void test() {
        q.add("Eka");
        q.add("Toka");
        q.add("Kolmas");
        q.add("Neljäs");
        q.add("Viides");

        assertEquals("Eka", q.remove());
        assertEquals("Toka", q.remove());
        assertEquals("Kolmas", q.remove());

        q.add("Kuudes");
        q.add("Seitsemäs");

        assertEquals("Neljäs", q.remove());
        assertEquals("Viides", q.remove());

        q.add("Kahdeksas");

        assertEquals("Kuudes", q.remove());
        assertEquals("Seitsemäs", q.remove());
        assertEquals("Kahdeksas", q.remove());
        assertEquals(null, q.remove());
    }

    @Test
    public void testSize() {
        assertEquals(0, q.size());
        q.add("asd");
        assertEquals(1, q.size());
        q.remove();
        assertEquals(0, q.size());
        q.add("asd");
        q.add("asd");
        q.add("asd");
        assertEquals(3, q.size());
    }

}
