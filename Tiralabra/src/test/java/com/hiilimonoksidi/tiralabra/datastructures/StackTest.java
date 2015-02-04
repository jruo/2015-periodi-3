package com.hiilimonoksidi.tiralabra.datastructures;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class StackTest {
    
    Stack<String> stack;
    
    @Before
    public void setUp() {
        stack = new Stack<>();
    }

    @Test
    public void test() {
        assertEquals(null, stack.pop());
        
        stack.push("Eka");
        stack.push("Toka");
        stack.push("Kolmas");
        stack.push("Neljäs");
        
        assertEquals("Neljäs", stack.pop());
        assertEquals("Kolmas", stack.pop());
        
        stack.push("Viides");
        stack.push("Kuudes");
        
        assertEquals("Kuudes", stack.pop());
        assertEquals("Viides", stack.pop());
        assertEquals("Toka", stack.pop());
        assertEquals("Eka", stack.pop());
        assertEquals(null, stack.pop());
    }
}
