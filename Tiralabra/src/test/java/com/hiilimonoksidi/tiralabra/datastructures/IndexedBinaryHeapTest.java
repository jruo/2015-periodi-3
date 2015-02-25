package com.hiilimonoksidi.tiralabra.datastructures;

import java.util.Comparator;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class IndexedBinaryHeapTest {
    
    Heap<Integer> heap;
    
    @Before
    public void setUp() {
        heap = new IndexedBinaryHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
    }

    @Test
    public void testAdd() {
    }

    @Test
    public void testIsEmpty() {
    }

    @Test
    public void testRemove_0args() {
    }

    @Test
    public void testRemove_GenericType() {
    }

    @Test
    public void testSize() {
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testIterator() {
    }
    
}
