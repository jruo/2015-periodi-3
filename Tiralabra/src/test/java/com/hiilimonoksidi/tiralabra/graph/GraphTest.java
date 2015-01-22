package com.hiilimonoksidi.tiralabra.graph;

import com.hiilimonoksidi.tiralabra.misc.Direction;
import com.hiilimonoksidi.tiralabra.misc.GraphBuilder;
import com.hiilimonoksidi.tiralabra.misc.Point;
import java.util.Arrays;
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

    // <editor-fold defaultstate="collapsed" desc="JSP-naapurien testaus">
    
    // Hyppy ylös
    private int[][][] n1 = {
        {{0, 0, 0},
         {1, 0, 0},
         {0, 0, 0}},
        {{1, 1, 0},
         {0, 0, 0},
         {0, 0, 0}}
    };
    private int[][][] n2 = {
        {{0, 1, 0},
         {0, 0, 0},
         {0, 0, 0}},
        {{0, 0, 0},
         {0, 0, 0},
         {0, 0, 0}}
    };
    private int[][][] n3 = {
        {{0, 0, 0},
         {0, 0, 0},
         {0, 0, 0}},
        {{0, 1, 0},
         {0, 0, 0},
         {0, 0, 0}}
    };
    private int[][][] n4 = {
        {{1, 0, 0},
         {0, 0, 0},
         {0, 0, 0}},
        {{0, 1, 0},
         {0, 0, 0},
         {0, 0, 0}}
    };
    private int[][][] n5 = {
        {{1, 0, 1},
         {0, 0, 0},
         {0, 0, 0}},
        {{0, 1, 0},
         {0, 0, 0},
         {0, 0, 0}}
    };
    private int[][][] n6 = {
        {{1, 0, 0},
         {1, 0, 0},
         {0, 0, 0}},
        {{0, 1, 0},
         {0, 0, 0},
         {0, 0, 0}}
    };
    private int[][][] n7 = {
        {{1, 0, 0},
         {1, 0, 1},
         {0, 0, 0}},
        {{0, 1, 1},
         {0, 0, 0},
         {0, 0, 0}}
    };
    private int[][][] n8 = {
        {{1, 0, 1},
         {1, 0, 1},
         {0, 0, 0}},
        {{0, 1, 0},
         {0, 0, 0},
         {0, 0, 0}}
    };
    private int[][][] n9 = {
        {{0, 0, 0},
         {1, 0, 1},
         {0, 0, 0}},
        {{1, 1, 1},
         {0, 0, 0},
         {0, 0, 0}}
    };
    private int[][][] n10 = {
        {{0, 0, 0},
         {0, 0, 0},
         {0, 1, 0}},
        {{0, 1, 0},
         {0, 0, 0},
         {0, 0, 0}}
    };
    private int[][][] n11 = {
        {{0, 0, 0},
         {0, 0, 0},
         {1, 0, 1}},
        {{0, 1, 0},
         {0, 0, 0},
         {0, 0, 0}}
    };
    private int[][][] n12 = {
        {{0, 0, 0},
         {0, 0, 0},
         {1, 1, 1}},
        {{0, 1, 0},
         {0, 0, 0},
         {0, 0, 0}}
    };
    
    // Hyppy oikealle
    private int[][][] e1 = {
        {{0, 0, 0},
         {0, 0, 0},
         {0, 0, 0}},
        {{0, 0, 0},
         {0, 0, 1},
         {0, 0, 0}}
    };
    private int[][][] e2 = {
        {{0, 0, 0},
         {0, 0, 1},
         {0, 0, 0}},
        {{0, 0, 0},
         {0, 0, 0},
         {0, 0, 0}}
    };
    private int[][][] e3 = {
        {{0, 1, 0},
         {0, 0, 0},
         {0, 0, 0}},
        {{0, 0, 1},
         {0, 0, 1},
         {0, 0, 0}}
    };
    private int[][][] e4 = {
        {{0, 0, 0},
         {0, 0, 0},
         {0, 1, 0}},
        {{0, 0, 0},
         {0, 0, 1},
         {0, 0, 1}}
    };
    private int[][][] e5 = {
        {{0, 1, 0},
         {0, 0, 0},
         {0, 1, 0}},
        {{0, 0, 1},
         {0, 0, 1},
         {0, 0, 1}}
    };
    private int[][][] e6 = {
        {{0, 1, 0},
         {0, 0, 1},
         {0, 1, 0}},
        {{0, 0, 0},
         {0, 0, 0},
         {0, 0, 0}}
    };
    
    // </editor-fold>
    
    private int[][][][] ns = {n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12};
    private int[][][][] es = {e1, e2, e3, e4, e5, e6};

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
        List<Node> nodes = Arrays.asList(g.getNeighbors(g.get(2, 2)));
        assertEquals(8, nodes.size());
        assertTrue(nodes.contains(g.get(2, 1)));
        assertTrue(nodes.contains(g.get(3, 2)));
        assertTrue(nodes.contains(g.get(2, 3)));
        assertTrue(nodes.contains(g.get(1, 3)));
        assertTrue(nodes.contains(g.get(1, 2)));
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

    @Test
    public void testGetJumpableNeighborsNS() {
        for (int[][][] a : ns) {
            int[][] ag = a[0];
            int[][] at = a[1];

            checkJumpableNeighborsAtDirection(ag, at, Direction.NORTH);
        }
    }
    
    @Test
    public void testGetJumpableNeighborsES() {
        for (int[][][] a : es) {
            int[][] ag = a[0];
            int[][] at = a[1];

            checkJumpableNeighborsAtDirection(ag, at, Direction.EAST);
        }
    }

    private void checkJumpableNeighborsAtDirection(int[][] ag, int[][] at, Direction dir) {
        g = GraphBuilder.createFromIntArray(ag);
        Node[] neighbors = g.getJumpableNeighbors(1, 1, dir);
        for (Node neighbor : neighbors) {
            if (neighbor == null) {
                continue;
            }
            assertTrue(at[neighbor.y][neighbor.x] == 1);
            at[neighbor.y][neighbor.x] = 0;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(0, at[j][i]);
            }
        }
    }
}
