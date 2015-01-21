package com.hiilimonoksidi.tiralabra.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class ArgumentsTest {

    private Arguments args;

    public ArgumentsTest() {
        args = new Arguments(new String[]{"-i", "input", "-o", "output", "-s", "start", "-g", "goal", "-t", "timeout", "--ASDF"});
    }

    @Test
    public void testLength() {
        assertEquals(11, args.getLength());
    }

    @Test
    public void testHasArgument() {
        assertTrue(args.hasArgument("--ASDF"));
        assertTrue(args.hasArgument("input"));
        assertTrue(args.hasArgument("-s"));
        assertFalse(args.hasArgument("qwerty"));
    }

    @Test
    public void testGetArgument() {
        assertEquals("input", args.getArgument("-i"));
        assertEquals("start", args.getArgument("-s"));
    }

}
