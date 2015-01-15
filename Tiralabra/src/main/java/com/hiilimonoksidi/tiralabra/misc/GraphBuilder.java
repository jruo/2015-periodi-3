package com.hiilimonoksidi.tiralabra.misc;

import com.hiilimonoksidi.tiralabra.graph.Graph;
import com.hiilimonoksidi.tiralabra.graph.Node;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class GraphBuilder {
    
    private static final int RGB_WHITE = Color.WHITE.getRGB();

    public static Graph createFromImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        Node[][] nodes = new Node[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = image.getRGB(j, i);
                boolean empty = rgb == RGB_WHITE;
                Node node = new Node(j, i, empty);
                nodes[i][j] = node;
            }
        }
        
        return new Graph(nodes);
    }
    
    public static Graph createFromIntArray(int[][] array) {
        int height = array.length;
        int width = array[0].length;
        
        Node[][] nodes = new Node[height][width];
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boolean empty = array[i][j] == 0;
                Node node = new Node(j, i, empty);
                nodes[i][j] = node;
            }
        }
        
        return new Graph(nodes);
    }
}
