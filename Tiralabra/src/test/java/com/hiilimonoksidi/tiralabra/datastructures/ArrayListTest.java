package com.hiilimonoksidi.tiralabra.datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class ArrayListTest {

    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test
    public void testAdd() {
        assertEquals(0, list.size());
        list.add("1");
        assertEquals(1, list.size());
        list.add("2");
        assertEquals(2, list.size());
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        assertEquals(12, list.size());
    }

    @Test
    public void testGet() {
        list.add("1");
        list.add("2");
        list.add("3");

        assertEquals("1", list.get(0));
        assertEquals("2", list.get(1));
        assertEquals("3", list.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFail1() {
        list.add("1");
        list.get(2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFail2() {
        list.add("1");
        list.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFail3() {
        list.get(0);
    }

    @Test
    public void testRemove1() {
        list.add("1");
        list.add("2");
        list.remove("1");
        assertEquals(1, list.size());
        assertEquals("2", list.get(0));
        list.add("3");
        assertEquals(2, list.size());
        assertEquals("2", list.get(0));
        assertEquals("3", list.get(1));
        list.add("4");
        list.remove("3");
        assertEquals(2, list.size());
        assertEquals("2", list.get(0));
        assertEquals("4", list.get(1));
    }
    
    @Test
    public void testRemove2() {
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        
        list.remove("5");
        assertEquals(14, list.size());
        assertEquals("4", list.get(3));
        assertEquals("6", list.get(4));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.add("1");
        assertFalse(list.isEmpty());
        list.add("2");
        list.remove("1");
        list.remove("2");
        assertTrue(list.isEmpty());
    }
}
