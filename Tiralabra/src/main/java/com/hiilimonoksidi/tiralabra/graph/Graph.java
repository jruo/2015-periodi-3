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
                nodes[j][i] = new Node(graph.nodes[j][i]);
            }
        }
    }

    public Node[][] getNodes() {
        return nodes;
    }

    /**
     * Palauttaa solmun annetussa sijainnissa. Jos sijainti on verkon
     * ulkopuolella, palauttaa aina solmun joka ei ole vapaa.
     *
     * @param p Solmun sijainti
     * @return Solmu sijainnissa p
     */
    public Node get(Point p) {
        if (isValidNode(p.x, p.y)) {
            return nodes[p.y][p.x];
        } else {
            return new Node(p.x, p.y, false);
        }
    }

    public Node get(int x, int y) {
        if (isValidNode(x, y)) {
            return nodes[y][x];
        } else {
            return new Node(x, y, false);
        }
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

        if (!nodes[y][x].clear) {
            return neighbors;
        }

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

        if (north != null && north.clear) {
            neighbors[0] = north;
            northClear = true;
        }
        if (east != null && east.clear) {
            neighbors[1] = east;
            eastClear = true;
        }
        if (south != null && south.clear) {
            neighbors[2] = south;
            southClear = true;
        }
        if (west != null && west.clear) {
            neighbors[3] = west;
            westClear = true;
        }

        if (northeast != null && northeast.clear && (northClear || eastClear)) {
            neighbors[4] = northeast;
        }
        if (northwest != null && northwest.clear && (northClear || westClear)) {
            neighbors[5] = northwest;
        }
        if (southeast != null && southeast.clear && (southClear || eastClear)) {
            neighbors[6] = southeast;
        }
        if (southwest != null && southwest.clear && (southClear || westClear)) {
            neighbors[7] = southwest;
        }

        return neighbors;
    }

    /**
     * Palauttaa kaikki solmut, joita tulisi tarkastella jump point searchissa
     * hypetässä annetusta solmusta annettuun suuntaan.
     *
     * @param node Solmu, jonka naapureita tarkastellaan
     * @param jumpDirection Suunta, johon halutaan hypätä
     * @return Lista naapureista joiden suuntiin tulisi hypätä
     */
    public Node[] getJumpableNeighbors(Node node, Direction jumpDirection) {
        return getJumpableNeighbors(node.x, node.y, jumpDirection);
    }

    public Node[] getJumpableNeighbors(int x, int y, Direction jumpDirection) {
        if (jumpDirection == null) {
            return getNeighbors(x, y);
        }

        Node[] neighbors = new Node[5];

        int dx = jumpDirection.dx;
        int dy = jumpDirection.dy;

        // Pysty- ja vaakasuunta
        if (dx == 0 || dy == 0) {
            if (!get(x + dx, y + dy).clear) {
                return neighbors;
            }

            int lx = dy;       // Vasen x
            int ly = -dx;      // Vasen y
            int rx = -dy;      // Oikea x
            int ry = dx;       // Oikea y
            int lfx = dx + dy; // Vasen eteen x
            int lfy = dy - dx; // Vasen eteen y
            int rfx = dx - dy; // Oikea eteen x
            int rfy = dx + dy; // Oikea eteen y

            if (!get(x + lx, y + ly).clear && get(x + lfx, y + lfy).clear) {
                neighbors[0] = nodes[y + lfy][x + lfx];
            }
            if (!get(x + rx, y + ry).clear && get(x + rfx, y + rfy).clear) {
                neighbors[1] = nodes[y + rfy][x + rfx];
            }
            neighbors[2] = nodes[y + dy][x + dx];

        } else {
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
        }

        return neighbors;
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
        int dx = direction.dx;
        int dy = direction.dy;

        if (dx == 0 || dy == 0) {
            if (dx == 0) {
                if ((get(x + 1, y + dy).clear && !get(x + 1, y).clear)
                    || get(x - 1, y + dy).clear && !get(x - 1, y).clear) {
                    return true;
                }
            } else {
                if ((get(x + dx, y + 1).clear && !get(x, y + 1).clear)
                    || get(x + dx, y - 1).clear && !get(x, y - 1).clear) {
                    return true;
                }
            }
        } else {
            if ((get(x - dx, y + dy).clear && !get(x - dx, y).clear)
                || get(x + dx, y - dx).clear && !get(x, y - dy).clear) {
                return true;
            }
        }

        return false;
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
                r += node.clear ? "." : "#";
            }
            r += "\n";
        }
        return r;
    }

}
