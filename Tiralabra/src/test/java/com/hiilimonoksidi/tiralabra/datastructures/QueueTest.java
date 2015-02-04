package com.hiilimonoksidi.tiralabra.datastructures;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class QueueTest {

    Queue<String> q;

    @Before
    public void setUp() {
        q = new Queue<>();
    }

    @Test
    public void test() {
        q.enqueue("Eka");
        q.enqueue("Toka");
        q.enqueue("Kolmas");
        q.enqueue("Neljäs");
        q.enqueue("Viides");

        assertEquals("Eka", q.dequeue());
        assertEquals("Toka", q.dequeue());
        assertEquals("Kolmas", q.dequeue());

        q.enqueue("Kuudes");
        q.enqueue("Seitsemäs");

        assertEquals("Neljäs", q.dequeue());
        assertEquals("Viides", q.dequeue());

        q.enqueue("Kahdeksas");

        assertEquals("Kuudes", q.dequeue());
        assertEquals("Seitsemäs", q.dequeue());
        assertEquals("Kahdeksas", q.dequeue());
        assertEquals(null, q.dequeue());
    }

    @Test
    public void testSize() {
        assertEquals(0, q.size());
        q.enqueue("asd");
        assertEquals(1, q.size());
        q.dequeue();
        assertEquals(0, q.size());
        q.enqueue("asd");
        q.enqueue("asd");
        q.enqueue("asd");
        assertEquals(3, q.size());
    }

}
