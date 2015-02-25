package com.hiilimonoksidi.tiralabra.datastructures;

import com.hiilimonoksidi.tiralabra.datastructures.IndexedBinaryHeap.Entry;
import java.util.Comparator;
import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class IndexedBinaryHeapTest {
    
    IndexedBinaryHeap<Integer> heap;
    
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
    public void test() {
        for (int i = 0; i < 100; i++) {
            heap.add((int) (Math.random() * 100));
        }
        
        Iterator<Entry<Integer>> iterator = heap.entryIterator();
        for (int i = 1; iterator.hasNext(); i++) {
            assertEquals(i, iterator.next().index, 0);
        }
    }
    
}
