package com.hiilimonoksidi.tiralabra.graph;

import com.hiilimonoksidi.tiralabra.misc.Direction;
import com.hiilimonoksidi.tiralabra.misc.Point;

/**
 * Verkko, tai oikeastaan ruudukko, jonka jokainen vapaa solmu on yhteydessä
 * jokaiseen ympärillään olevaan vapaaseen solmuun.
 *
 * @author Janne Ruoho
 */
public class Graph {

    public final int width, height;
    private final Node[][] nodes;

    public Graph(Node[][] nodes) {
        this.nodes = nodes;
        height = nodes.length;
        width = nodes[0].length;
    }

    /**
     * Kopioi annetun verkon.
     *
     * @param graph Kopioitava verkko
     */
    public Graph(Graph graph) {
        width = graph.width;
        height = graph.height;
        nodes = new Node[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                nodes[i][j] = new Node(graph.nodes[i][j]);
            }
        }
    }

    /**
     * Palauttaa solmun annetussa sijainnissa. Jos sijainti on verkon
     * ulkopuolella, palauttaa aina solmun joka ei ole vapaa.
     *
     * @param p Solmun sijainti
     * @return Solmu sijainnissa p
     */
    public Node get(Point p) {
        return get(p.x, p.y);
    }

    public Node get(int x, int y) {
        if (isValidNode(x, y)) {
            return nodes[y][x];
        } else {
            return new Node(x, y, false);
        }
    }

    /**
     * Palauttaa solmun (x, y) naapurisolmun suunnassa dir.
     *
     * @param x Solmun x
     * @param y Solmun y
     * @param dir Suunta
     * @return Naapurisolmu
     */
    public Node getNeighbor(int x, int y, Direction dir) {
        return get(x + dir.dx, y + dir.dy);
    }

    public boolean isClear(int x, int y, Direction dir) {
        return getNeighbor(x, y, dir).clear;
    }

    /**
     * Tarkistaa, kuuluuko solmu (x, y) verkkoon.
     *
     * @param x Solmun x
     * @param y Solmun y
     * @return Tosi jos ja vain jos kuuluu, epätosi muutoin
     */
    public boolean isValidNode(int x, int y) {
        return x >= 0 && y >= 0 && y < height && x < width;
    }

    /**
     * Palauttaa kaikki annetun solmun vapaat naapurisolmut.
     *
     * @param node Solmu, jonka naapurit halutaan
     * @return Lista naapureista
     */
    public Node[] getNeighbors(Node node) {
        return getNeighbors(node.x, node.y);
    }

    public Node[] getNeighbors(int x, int y) {
        Node[] neighbors = new Node[8];
        
        System.arraycopy(getOrthogonalNeighbors(x, y), 0, neighbors, 0, 4);
        System.arraycopy(getDiagonalNeighbors(x, y), 0, neighbors, 4, 4);

        return neighbors;
    }

    /**
     * Palauttaa solmun vapaat naapurit, jotka ovat suoraan sen yllä, alla tai
     * sivuilla.
     *
     * @param node Solmu, jonka naapurit halutaan.
     * @return Lista naapureista
     */
    public Node[] getOrthogonalNeighbors(Node node) {
        return getOrthogonalNeighbors(node.x, node.y);
    }

    private Node[] getOrthogonalNeighbors(int x, int y) {
        Node[] neighbors = new Node[4];
        neighbors[0] = isClear(x, y, Direction.NORTH) ? getNeighbor(x, y, Direction.NORTH) : null;
        neighbors[1] = isClear(x, y, Direction.EAST) ? getNeighbor(x, y, Direction.EAST) : null;
        neighbors[2] = isClear(x, y, Direction.SOUTH) ? getNeighbor(x, y, Direction.SOUTH) : null;
        neighbors[3] = isClear(x, y, Direction.WEST) ? getNeighbor(x, y, Direction.WEST) : null;
        return neighbors;
    }
    
    /**
     * Palauttaa vinosuunnissa olevat avoimet naapurit.
     * 
     * @param x Tarkasteltavan solmun x
     * @param y Tarkasteltavan solmun y
     * @return Lista naapureista
     */
    public Node[] getDiagonalNeighbors(int x, int y) {
        Node[] neighbors = new Node[4];
        neighbors[0] = (isClear(x, y, Direction.NORTH) || isClear(x, y, Direction.EAST))
                       && isClear(x, y, Direction.NORTHEAST) ? getNeighbor(x, y, Direction.NORTHEAST) : null;
        neighbors[1] = (isClear(x, y, Direction.NORTH) || isClear(x, y, Direction.WEST))
                       && isClear(x, y, Direction.NORTHWEST) ? getNeighbor(x, y, Direction.NORTHWEST) : null;
        neighbors[2] = (isClear(x, y, Direction.SOUTH) || isClear(x, y, Direction.EAST))
                       && isClear(x, y, Direction.SOUTHEAST) ? getNeighbor(x, y, Direction.SOUTHEAST) : null;
        neighbors[3] = (isClear(x, y, Direction.SOUTH) || isClear(x, y, Direction.WEST))
                       && isClear(x, y, Direction.SOUTHWEST) ? getNeighbor(x, y, Direction.SOUTHWEST) : null;
        return neighbors;
    }

    /**
     * Palauttaa kaikki solmut, joita tulisi tarkastella jump point searchissa
     * hypetässä annetusta solmusta annettuun suuntaan.
     *
     * @param node Solmu, jonka naapureita tarkastellaan
     * @param direction Suunta, johon halutaan hypätä
     * @return Lista naapureista joiden suuntiin tulisi hypätä
     */
    public Node[] getJumpableNeighbors(Node node, Direction direction) {
        return getJumpableNeighbors(node.x, node.y, direction);
    }

    public Node[] getJumpableNeighbors(int x, int y, Direction direction) {
        if (direction == null) {
            return getNeighbors(x, y);
        }

        if (direction.isOrthogonal()) {
            return getOrthogonalJumpableNeighbors(x, y, direction);
        } else {
            return getDiagonalJumpableNeighbors(x, y, direction);
        }
    }

    /**
     * Tarkistaa, onko annetulla solmulla 'pakotettuja naapureita' eli
     * naapureita, jotka JPS:ssä normaalisti käydään toisen solmun toimesta
     * läpi, mutta esteen takia nykyinen solmu on pakotettu käymään se läpi.
     *
     * @param node Tarkistettava solmu
     * @param direction Suunta, josta tarkistetaan naapurit
     * @return Tosi jos naapureita on, false muutoin
     */
    public boolean hasForcedNeighbors(Node node, Direction direction) {
        return hasForcedNeighbors(node.x, node.y, direction);
    }

    public boolean hasForcedNeighbors(int x, int y, Direction direction) {
        if (direction.isOrthogonal()) {
            return hasOrthogonalForcedNeighbors(x, y, direction);
        } else {
            return hasDiagonalForcedNeighbors(x, y, direction);
        }
    }

    /**
     * Palauttaa kaikki JPS-haun hypättävät naapurit vinottaissuunnassa
     *
     * @param x Tarkasteltavan solmun x
     * @param y Tarkasteltavan solmun y
     * @param direction Hypättävä suunta
     * @return Lista naapureista
     */
    private Node[] getDiagonalJumpableNeighbors(int x, int y, Direction direction) {
        Node[] neighbors = new Node[5];

        int dx = direction.dx;
        int dy = direction.dy;
        boolean hClear = false;
        boolean vClear = false;

        if (get(x, y + dy).clear) {
            neighbors[0] = nodes[y + dy][x];
            vClear = true;
        }
        if (get(x + dx, y).clear) {
            neighbors[1] = nodes[y][x + dx];
            hClear = true;
        }
        if ((hClear || vClear) && get(x + dx, y + dy).clear) {
            neighbors[2] = nodes[y + dy][x + dx];
        }
        if (hClear && !get(x, y - dy).clear && get(x + dx, y - dy).clear) {
            neighbors[3] = nodes[y - dy][x + dx];
        }
        if (vClear && !get(x - dx, y).clear && get(x - dx, y + dy).clear) {
            neighbors[4] = nodes[y + dy][x - dx];
        }

        return neighbors;
    }

    /**
     * Palauttaa kaikki JPS-haun hypättävät naapurit pysty- ja vaakasuunnassa
     *
     * @param x Tarkasteltavan solmun x
     * @param y Tarkasteltavan solmun y
     * @param direction Hypättävä suunta
     * @return Lista naapureista
     */
    private Node[] getOrthogonalJumpableNeighbors(int x, int y, Direction direction) {
        Node[] neighbors = new Node[3];

        int dx = direction.dx;
        int dy = direction.dy;
        int lx = dy;       // Vasen x
        int ly = -dx;      // Vasen y
        int rx = -dy;      // Oikea x
        int ry = dx;       // Oikea y
        int lfx = dx + dy; // Vasen eteen x
        int lfy = dy - dx; // Vasen eteen y
        int rfx = dx - dy; // Oikea eteen x
        int rfy = dx + dy; // Oikea eteen y

        if (!get(x + dx, y + dy).clear) {
            return neighbors;
        }
        if (!get(x + lx, y + ly).clear && get(x + lfx, y + lfy).clear) {
            neighbors[0] = nodes[y + lfy][x + lfx];
        }
        if (!get(x + rx, y + ry).clear && get(x + rfx, y + rfy).clear) {
            neighbors[1] = nodes[y + rfy][x + rfx];
        }
        neighbors[2] = nodes[y + dy][x + dx];

        return neighbors;
    }

    /**
     * Tarkistaa onko solmulla pakotettuja naapureita vinosuunnassa.
     *
     * @param x Solmun x
     * @param y Solmun y
     * @param direction Suunta
     * @return Tosi/epätosi
     */
    private boolean hasDiagonalForcedNeighbors(int x, int y, Direction direction) {
        int dx = direction.dx;
        int dy = direction.dy;
        return get(x - dx, y + dy).clear && !get(x - dx, y).clear
               || get(x + dx, y - dx).clear && !get(x, y - dy).clear;
    }

    /**
     * Tarkistaa onko solmulla pakotettuja naapureita pysty- tai vaakasuunnassa.
     *
     * @param x Solmun x
     * @param y Solmun y
     * @param direction Suunta
     * @return Tosi/epätosi
     */
    private boolean hasOrthogonalForcedNeighbors(int x, int y, Direction direction) {
        int dx = direction.dx;
        int dy = direction.dy;
        if (dx == 0) {
            if (get(x + 1, y + dy).clear && !get(x + 1, y).clear
                || get(x - 1, y + dy).clear && !get(x - 1, y).clear) {
                return true;
            }
        } else {
            if (get(x + dx, y + 1).clear && !get(x, y + 1).clear
                || get(x + dx, y - 1).clear && !get(x, y - 1).clear) {
                return true;
            }
        }
        return false;
    }
}
