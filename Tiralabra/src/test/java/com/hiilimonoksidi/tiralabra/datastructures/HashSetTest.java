package com.hiilimonoksidi.tiralabra.datastructures;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class HashSetTest {

    HashSet<String> stringSet;

    @Before
    public void setUp() {
        stringSet = new HashSet<>();
    }

    @Test
    public void test() {
        stringSet.add("eka");
        stringSet.add("toka");
        assertTrue(stringSet.contains("eka"));
        assertTrue(stringSet.contains("toka"));
        stringSet.remove("eka");
        stringSet.add("kolmas");
        stringSet.add("kolmas");
        stringSet.remove("kolmas");
        assertFalse(stringSet.contains("kolmas"));
        assertFalse(stringSet.contains("eka"));
    }

    @Test
    public void testGrowing() {
        HashSet<String> smallSet = new HashSet<>(4);
        smallSet.add("eka");
        smallSet.add("toka");
        smallSet.add("kolmas");
        smallSet.add("neljäs");

        assertEquals(4, smallSet.size());

        smallSet.remove("eka");
        smallSet.remove("toka");
        smallSet.remove("kolmas");
        smallSet.remove("neljäs");

        assertEquals(0, smallSet.size());

        assertFalse(smallSet.contains("eka"));
        assertFalse(smallSet.contains("toka"));
        assertFalse(smallSet.contains("kolmas"));
        assertFalse(smallSet.contains("neljäs"));

        smallSet.add("eka");
        smallSet.add("toka");
        smallSet.add("kolmas");
        smallSet.add("neljäs");
        smallSet.add("viides");
        smallSet.add("kuudes");
        smallSet.add("seitsemäs");
        smallSet.add("kahdeksas");
        smallSet.add("abc");
        smallSet.add("def");
        smallSet.add("ghi");
        smallSet.add("jkl");
        smallSet.add("mno");
        smallSet.add("pqr");
        smallSet.add("stu");
        smallSet.add("vwx");
        smallSet.add("yzå");

        smallSet.remove("seitsemäs");
        smallSet.remove("kolmas");
        smallSet.remove("jkl");

        assertEquals(14, smallSet.size());

        assertFalse(smallSet.contains("seitsemäs"));
        assertFalse(smallSet.contains("kolmas"));
        assertFalse(smallSet.contains("jkl"));
    }

    @Test
    public void testIterator() {
        Random random = new Random();

        // Testaa muutaman kerran eri arvoilla
        for (int test = 0; test < 1000; test++) {

            // Täytä testInts ja setti samoilla satunnaisilla luvuilla.
            int[] testInts = new int[random.nextInt(1000)];
            HashSet<Integer> set = new HashSet<>(50 + random.nextInt(1500));
            for (int i = 0; i < testInts.length; i++) {
                int rand;
                do {
                    rand = random.nextInt(100000);
                } while (set.contains(rand));

                testInts[i] = rand;
                set.add(rand);
            }

            // Testaa iteraattori lisäämällä jokainen alkio listaan
            List<Integer> list = new java.util.ArrayList<>();
            Iterator<Integer> iter = set.iterator();
            while (iter.hasNext()) {
                list.add(iter.next());
            }

            // Tarkistetaan että iteraattori palautti jokaisen alkion
            Collections.sort(list);
            Arrays.sort(testInts);
            for (int i = 0; i < testInts.length; i++) {
                assertEquals(testInts[i], list.get(i), 0);
            }
        }
    }
    
    @Test
    public void testEquality() {
        HashSet<TestElement> set = new HashSet<>();
        
        TestElement e1 = new TestElement(1);
        TestElement e2 = new TestElement(2);
        TestElement e3 = new TestElement(3);
        
        TestElement e12 = new TestElement(1);
        
        set.add(e1);
        set.add(e2);
        set.add(e3);
        
        assertFalse(e1 == e12);
        assertTrue(e1.equals(e12));
        
        assertTrue(e1 == set.get(e12)); // Täsmälleen sama olio?
        assertTrue(e1 == set.get(e1));
        
        assertFalse(e2 == set.get(e12));
        assertFalse(e3 == set.get(e12));
        
        set.remove(e12);
        assertFalse(set.contains(e1));
        assertFalse(set.contains(e12));
    }
    
    private static class TestElement {
        
        int something;

        public TestElement(int something) {
            this.something = something;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + this.something;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TestElement other = (TestElement) obj;
            return this.something == other.something;
        }
    }
}
