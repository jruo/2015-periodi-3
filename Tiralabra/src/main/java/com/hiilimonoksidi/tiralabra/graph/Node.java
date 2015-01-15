package com.hiilimonoksidi.tiralabra.graph;

import com.hiilimonoksidi.tiralabra.misc.Point;

public class Node {

    public final int x, y;
    private final boolean clear; // Vapaa vai este
    private Node parent; // Se solmu, jota kautta saavuttiin tähän solmuun

    public Node(int x, int y, boolean empty) {
        this.x = x;
        this.y = y;
        this.clear = empty;
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