package com.hiilimonoksidi.tiralabra.datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class LinkedListTest {

    LinkedList<String> list;

    @Before
    public void setUp() {
        list = new LinkedList<>();
    }

    @Test
    public void testAdd() {
        assertEquals(0, list.size());
        list.add("Eka");
        assertEquals(1, list.size());
        list.add("Toka");
        assertEquals(2, list.size());
        list.add("Kolmas");

        assertEquals("Eka", list.get(0));
        assertEquals("Toka", list.get(1));
        assertEquals("Kolmas", list.get(2));
        assertEquals(null, list.get(3));
    }

    @Test
    public void testRemoveTail() {
        list.add("Eka");
        list.add("Toka");
        list.add("Kolmas");

        assertEquals("Kolmas", list.removeTail());
        assertEquals("Toka", list.removeTail());
        assertEquals("Eka", list.removeTail());
        assertEquals(null, list.removeTail());
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testRemoveHead() {
        list.add("Eka");
        list.add("Toka");
        list.add("Kolmas");

        assertEquals("Eka", list.removeHead());
        assertEquals("Toka", list.removeHead());
        assertEquals("Kolmas", list.removeHead());
        assertEquals(null, list.removeHead());
    }

    @Test
    public void testRemove() {
        list.add("Eka");
        list.add("Toka");
        list.add("Kolmas");
        list.add("Neljäs");
        list.add("Viides");
        list.add("Kuudes");

        list.remove("Kolmas");
        assertEquals("Eka", list.get(0));
        assertEquals("Toka", list.get(1));
        assertEquals("Neljäs", list.get(2));
        assertEquals("Viides", list.get(3));
        assertEquals("Kuudes", list.get(4));
        assertEquals(null, list.get(5));

        list.remove("Viides");
        assertEquals("Eka", list.get(0));
        assertEquals("Toka", list.get(1));
        assertEquals("Neljäs", list.get(2));
        assertEquals("Kuudes", list.get(3));
        assertEquals(null, list.get(4));

        list.remove("Kuudes");
        assertEquals("Eka", list.get(0));
        assertEquals("Toka", list.get(1));
        assertEquals("Neljäs", list.get(2));
        assertEquals(null, list.get(3));

        list.remove("Toka");
        assertEquals("Eka", list.get(0));
        assertEquals("Neljäs", list.get(1));
        assertEquals(null, list.get(2));

        list.remove("Eka");
        assertEquals("Neljäs", list.get(0));
        assertEquals(null, list.get(1));

        list.remove("Eka");
        assertEquals("Neljäs", list.get(0));
        assertEquals(null, list.get(1));

        list.remove("Neljäs");
        assertEquals(null, list.get(0));

        assertTrue(list.isEmpty());
    }

    @Test
    public void testIterator() {
        LinkedList<Integer> intList = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            intList.add(i);
        }
        
        int i = 0;
        for (Integer j : intList) {
            assertEquals(i++, j, 0);
        }
        
        assertEquals(20, i);
    }

}
