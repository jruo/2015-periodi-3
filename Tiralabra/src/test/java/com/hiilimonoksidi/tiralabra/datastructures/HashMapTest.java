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
public class HashMapTest {

    HashMap<String, Integer> map;
    HashMap<Integer, Object> ioMap;
    HashMap<Object, Object> ooMap;

    @Before
    public void setUp() {
        map = new HashMap<>();
        ioMap = new HashMap<>();
        ooMap = new HashMap<>();
    }

    @Test
    public void testSize() {
        assertEquals(0, map.size());
        map.put("asd", 1);
        assertEquals(1, map.size());
        map.put("asd", 2);
        assertEquals(1, map.size());
        map.put("asdasd", 3);
        assertEquals(2, map.size());
        map.put("asdasdasd", 4);
        assertEquals(3, map.size());
        map.remove("asd");
        assertEquals(2, map.size());
        map.remove("asdasd");
        assertEquals(1, map.size());
        map.remove("asdasdasd");
        assertEquals(0, map.size());
    }

    @Test
    public void testContains() {
        map.put("yksi", 1);
        map.put("kaksi", 2);
        map.put("kolme", 3);

        assertTrue(map.contains("yksi"));
        assertTrue(map.contains("kaksi"));
        assertTrue(map.contains("kolme"));
        assertFalse(map.contains("neljä"));
        
        map.remove("yksi");
        assertFalse(map.contains("yksi"));
    }

    @Test
    public void testGet() {
        Object o1 = new Object();
        Object o2 = new Object();
        
        ioMap.put(1, o1);
        ioMap.put(2, o2);
        
        assertTrue(o1 == ioMap.get(1));
        assertTrue(o2 == ioMap.get(2));
        assertFalse(o1 == ioMap.get(2));
        assertFalse(o2 == ioMap.get(1));
        
        ioMap.put(1, o2);
        
        assertTrue(o2 == ioMap.get(1));
        assertTrue(o2 == ioMap.get(2));
        assertFalse(o1 == ioMap.get(1));
        assertFalse(o1 == ioMap.get(2));
    }
    
    @Test
    public void testGet2() {
        Object k1 = new Object();
        Object k2 = new Object();
        Object v1 = new Object();
        Object v2 = new Object();
        
        ooMap.put(k1, v1);
        ooMap.put(k2, v2);
        
        assertTrue(v1 == ooMap.get(k1));
        assertTrue(v2 == ooMap.get(k2));
        assertTrue(null == ooMap.get(null));
        assertFalse(v1 == ooMap.get(k2));
        
        ooMap.put(k1, v2);
        
        assertTrue(v2 == ooMap.get(k1));
        assertTrue(v2 == ooMap.get(k2));
    }
}
