package com.hiilimonoksidi.tiralabra.misc;

import com.hiilimonoksidi.tiralabra.graph.Graph;
import com.hiilimonoksidi.tiralabra.graph.Node;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Luokka verkon rakentamiseen eri lähteistä.
 *
 * @author Janne Ruoho
 */
public class GraphBuilder {

    private static final int RGB_WHITE = Color.WHITE.getRGB();

    /**
     * Rakentaa verkon annetusta kuvasta. Verkon leveys ja korkeus on sama kuin
     * kuvan leveys ja korkeus, ja kuvan pikselit vastaavat yksi yhteen verkon
     * solmuja. Solmu on vapaa jos ja vain jos sitä vastaava pikseli on täysin
     * valkoinen, muuten se on este.
     *
     * @param image Kuva, josta verkko rakennetaan
     * @return Kuvaa vastaava verkko
     */
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

    /**
     * Rakentaa verkon anneutsta int[][]-taulukosta. Taulukon arvot vastaavat
     * verkon solmuja. 0-arvot vastaavat vapaata solmua ja muut arvot estettä.
     * Taulukon täytyy olla neliskulmainen, muuten metodi heittää poikkeuksen.
     *
     * @param array Taulukko, josta verkko rakennetaan
     * @return Taulukkoa vastaava verkko.
     */
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
