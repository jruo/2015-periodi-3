package com.hiilimonoksidi.tiralabra.misc;

import com.hiilimonoksidi.tiralabra.graph.Graph;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Janne Ruoho
 */
public class GraphBuilderTest {

    public GraphBuilderTest() {
    }

    @Test
    public void testCreateFromImage() throws IOException {
        BufferedImage image = ImageIO.read(getClass().getResource("/7px.png"));
        Graph g = GraphBuilder.createFromImage(image);

        assertEquals(7, g.width);
        assertEquals(7, g.height);
        assertFalse(g.get(0, 0).clear);
        assertFalse(g.get(1, 1).clear);
        assertFalse(g.get(1, 1).clear);
        assertFalse(g.get(6, 0).clear);
        assertFalse(g.get(3, 3).clear);
        assertFalse(g.get(5, 3).clear);
        assertTrue(g.get(2, 0).clear);
        assertTrue(g.get(6, 6).clear);
        assertTrue(g.get(0, 6).clear);
        assertTrue(g.get(1, 6).clear);
        assertTrue(g.get(2, 6).clear);
    }

    @Test
    public void testCreateFromIntArray() {
        Graph g = GraphBuilder.createFromIntArray(new int[][]{{0, 1, 1},
                                                              {1, 0, 0},
                                                              {0, 0, 0}});

        assertEquals(3, g.width);
        assertEquals(3, g.height);
        assertFalse(g.get(1, 0).clear);
        assertFalse(g.get(2, 0).clear);
        assertFalse(g.get(0, 1).clear);
        assertTrue(g.get(0, 0).clear);
        assertTrue(g.get(1, 1).clear);
        assertTrue(g.get(2, 1).clear);
        assertTrue(g.get(0, 2).clear);
        assertTrue(g.get(1, 2).clear);
        assertTrue(g.get(2, 2).clear);
    }

}
