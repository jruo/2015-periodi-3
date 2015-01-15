package com.hiilimonoksidi.tiralabra.graph;

import com.hiilimonoksidi.tiralabra.misc.Point;

/**
 * Verkon solmu.
 * 
 * @author Janne Ruoho
 */
public class Node {

    /**
     * Solmun sijainti verkossa
     */
    public final int x, y;
    
    /**
     * Onko solmu vapaa vai este
     */
    private final boolean clear;
    
    /**
     * Solmu, josta saavuttiin tähän
     */
    private Node parent;

    public Node(int x, int y, boolean empty) {
        this.x = x;
        this.y = y;
        this.clear = empty;
    }
    
    /**
     * Kopioi anneton solmun.
     * 
     * @param node Kopioitava solmu
     */
    public Node(Node node) {
        x = node.x;
        y = node.y;
        clear = node.clear;
    }
    
    public void setParent(Node node) {
        parent = node;
    }

    public Node getParent() {
        return parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isClear() {
        return clear;
    }
    
    public Point getLocation() {
        return new Point(x, y);
    }    
}