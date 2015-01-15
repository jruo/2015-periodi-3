package com.hiilimonoksidi.tiralabra.graph;

import com.hiilimonoksidi.tiralabra.misc.Direction;
import com.hiilimonoksidi.tiralabra.misc.Point;
import java.util.ArrayList;
import java.util.List;

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

    public Node[][] getNodes() {
        return nodes;
    }

    /**
     * Palauttaa solmun annetussa sijainnissa.
     *
     * @param p Solmun sijainti
     * @return Solmu sijainnissa p
     */
    public Node get(Point p) {
        return nodes[p.y][p.x];
    }

    public Node get(int x, int y) {
        return nodes[y][x];
    }

    /**
     * Palauttaa kaikki annetun solmun vapaat naapurisolmut.
     *
     * @param node Solmu, jonka naapurit halutaan
     * @return Lista naapureista
     */
    public List<Node> getNeighbors(Node node) {
        return getNeighbors(node.x, node.y);
    }

    public List<Node> getNeighbors(int x, int y) {
        List<Node> neighbors = new ArrayList<>();

        Node north = getNeighborAtDirection(x, y, Direction.NORTH);
        Node east = getNeighborAtDirection(x, y, Direction.EAST);
        Node south = getNeighborAtDirection(x, y, Direction.SOUTH);
        Node west = getNeighborAtDirection(x, y, Direction.WEST);
        Node northeast = getNeighborAtDirection(x, y, Direction.NORTHEAST);
        Node northwest = getNeighborAtDirection(x, y, Direction.NORTHWEST);
        Node southeast = getNeighborAtDirection(x, y, Direction.SOUTHEAST);
        Node southwest = getNeighborAtDirection(x, y, Direction.SOUTHWEST);

        boolean northClear = false;
        boolean eastClear = false;
        boolean southClear = false;
        boolean westClear = false;

        if (north != null && north.isClear()) {
            neighbors.add(north);
            northClear = true;
        }
        if (east != null && east.isClear()) {
            neighbors.add(east);
            eastClear = true;
        }
        if (south != null && south.isClear()) {
            neighbors.add(south);
            southClear = true;
        }
        if (west != null && west.isClear()) {
            neighbors.add(west);
            westClear = true;
        }

        if (northeast != null && northeast.isClear() && (northClear || eastClear)) {
            neighbors.add(northeast);
        }
        if (northwest != null && northwest.isClear() && (northClear || westClear)) {
            neighbors.add(northwest);
        }
        if (southeast != null && southeast.isClear() && (southClear || eastClear)) {
            neighbors.add(southeast);
        }
        if (southwest != null && southwest.isClear() && (southClear || westClear)) {
            neighbors.add(southwest);
        }

        return neighbors;
    }

    /**
     * Palauttaa solmun (x, y) naapurisolmun suunnassa dir.
     *
     * @param x Solmun x
     * @param y Solmun y
     * @param dir Suunta
     * @return Naapurisolmu
     */
    private Node getNeighborAtDirection(int x, int y, Direction dir) {
        int dx = x + dir.dx;
        int dy = y + dir.dy;

        if (isValidNode(dx, dy)) {
            return nodes[dy][dx];
        } else {
            return null;
        }
    }

    /**
     * Tarkistaa, kuuluuko solmu (x, y) verkkoon.
     *
     * @param x Solmun x
     * @param y Solmun y
     * @return Tosi jos ja vain jos kuuluu, epätosi muutoin
     */
    private boolean isValidNode(int x, int y) {
        return x >= 0 && y >= 0 && y < height && x < width;
    }

    @Override
    public String toString() {
        String r = "";
        for (Node[] row : nodes) {
            for (Node node : row) {
                r += node.isClear() ? "." : "#";
            }
            r += "\n";
        }
        return r;
    }

}
