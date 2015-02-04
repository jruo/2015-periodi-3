package com.hiilimonoksidi.tiralabra.datastructures;

import java.util.Comparator;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class HeapTest {

    Heap<Integer> heap;

    @Before
    public void setUp() {
        heap = new Heap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
    }

    @Test
    public void testSize() {
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int rint = r.nextInt(100);
            assertEquals(i, heap.size());
            heap.add(rint);
            assertEquals(i + 1, heap.size());
        }
        for (int i = 1000; i > 0; i--) {
            assertEquals(i, heap.size());
            heap.remove();
            assertEquals(i - 1, heap.size());
        }
    }
    
    @Test
    public void testOrderManual() {
        heap.add(40);
        heap.add(33);
        heap.add(7);
        heap.add(12);
        heap.add(1);
        heap.add(4);
        heap.add(26);
        heap.add(36);
        heap.add(9);
        heap.add(17);
        heap.add(34);
        
        assertEquals(1, heap.remove(), 0);
        assertEquals(4, heap.remove(), 0);
        assertEquals(7, heap.remove(), 0);
        assertEquals(9, heap.remove(), 0);
        assertEquals(12, heap.remove(), 0);
        assertEquals(17, heap.remove(), 0);
        assertEquals(26, heap.remove(), 0);
        assertEquals(33, heap.remove(), 0);
        assertEquals(34, heap.remove(), 0);
        assertEquals(36, heap.remove(), 0);
        assertEquals(40, heap.remove(), 0);
    }

    @Test
    public void testOrderAuto() {
        Random r = new Random();

        for (int i = 0; i < 1000; i++) {
            int elements = 200 + r.nextInt(5000);
            int maxElement = 1 + r.nextInt(1000000);
            for (int j = 0; j < elements; j++) {
                int rint = r.nextInt(maxElement);
                heap.add(rint);
            }

            int last = -1;
            while (true) {
                Integer root = heap.remove();
                if (root == null) {
                    break;
                }
                assertTrue(last <= root);
                last = root;
            }
        }
    }
    
    @Test
    public void testOrderWhenRemovingNonRootElements() {
        Random r = new Random();

        ArrayList<Integer> randomElementsToRemove = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int elements = 200 + r.nextInt(1000);
            int maxElement = 1 + r.nextInt(5000);
            for (int j = 0; j < elements; j++) {
                int rint = r.nextInt(maxElement);
                heap.add(rint);
                
                if (r.nextInt(20) < 5) {
                    randomElementsToRemove.add(rint);
                }
            }
            
            for (int j = 0; j < randomElementsToRemove.size(); j++) {
                heap.remove(randomElementsToRemove.get(j));
            }

            int last = -1;
            while (true) {
                Integer root = heap.remove();
                if (root == null) {
                    break;
                }
                assertTrue(last <= root);
                last = root;
            }
        }
    }
}
