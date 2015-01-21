package com.hiilimonoksidi.tiralabra.graph;

import com.hiilimonoksidi.tiralabra.misc.GraphBuilder;
import com.hiilimonoksidi.tiralabra.misc.Point;
import java.util.List;
import javax.imageio.ImageIO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class GraphTest {

    private Graph g;

    @Before
    public void setUp() throws Exception {
        g = GraphBuilder.createFromImage(ImageIO.read(getClass().getResource("/7px.png")));
    }

    @Test
    public void testGetNodes() {
        assertTrue(g.getNodes() != null);
    }

    @Test
    public void testGet_Point() {
        Node n = g.get(new Point(0, 0));
        assertEquals(0, n.x);
        assertEquals(0, n.y);
        assertFalse(n.clear);
    }

    @Test
    public void testGet_int_int() {
        Node n = g.get(2, 1);
        assertEquals(2, n.x);
        assertEquals(1, n.y);
        assertTrue(n.clear);
    }

    @Test
    public void testGetNeighbors_Node() {
        List<Node> nodes = g.getNeighbors(g.get(2, 2));
        assertEquals(5, nodes.size());
        assertTrue(nodes.contains(g.get(2, 1)));
        assertTrue(nodes.contains(g.get(3, 2)));
        assertTrue(nodes.contains(g.get(2, 3)));
        assertTrue(nodes.contains(g.get(1, 3)));
        assertTrue(nodes.contains(g.get(1, 2)));
    }

    @Test
    public void testGetNeighbors_int_int() {
        List<Node> nodes = g.getNeighbors(0, 0);
        assertEquals(0, nodes.size());
    }

    @Test
    public void testToString() {
        assertEquals("##....#\n"
                     + ".#.#...\n"
                     + ".......\n"
                     + "...#.#.\n"
                     + ".......\n"
                     + "..#..#.\n"
                     + ".......\n", g.toString());
    }

}
