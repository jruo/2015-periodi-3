package com.hiilimonoksidi.tiralabra.pathfinding;

import com.hiilimonoksidi.tiralabra.datastructures.HashSet;
import com.hiilimonoksidi.tiralabra.datastructures.Stack;
import com.hiilimonoksidi.tiralabra.graph.Node;

/**
 * Syvyyssuuntainen hakualgoritmi. Tämä algoritmi ei useimmissa tapauksissa
 * löydä lyhintä reittiä, vaan se vaan ottaa aina askeleen eteenpäin kunnes
 * törmää umpikujaan, palaa takaisin sellaiseen pisteeseen josta voi taas
 * jatkaa, ja ennen pitkään törmää maaliin.
 *
 * @author Janne Ruoho
 */
public class DepthFirstSearch extends PathfindingAlgorithm {

    /**
     * Käsittelyssä olevat solmut.
     */
    private Stack<Node> open;
    
    /**
     * Joukko, sisältää samat solmut kuin open. Nopeuttaa sisältyvyystarkistusta.
     */
    private HashSet<Node> openSet;

    /**
     * Käsitellyt solmut.
     */
    private HashSet<Node> closed;

    @Override
    protected void init() {
        open = new Stack<>();
        openSet = new HashSet<>();
        closed = new HashSet<>();

        open.push(graph.get(start));
        openSet.add(graph.get(start));
    }

    @Override
    public boolean searchStep() {
        Node current = open.pop();
        
        if (!closed.contains(current)) {
            closed.add(current);
            processNeighbors(current);
        }
        
        return finishStep(current);
    }

    /**
     * Käsittelee solmun naapurit.
     *
     * @param current Solmu
     */
    private void processNeighbors(Node current) {
        for (Node neighbor : graph.getOrthogonalNeighbors(current)) {
            if (neighbor == null) {
                continue;
            }

            if (!closed.contains(neighbor) && !openSet.contains(neighbor)) {
                neighbor.setParent(current);
                open.push(neighbor);
                openSet.add(neighbor);
            }
        }
    }

    @Override
    public boolean hasNextStep() {
        return !open.isEmpty();
    }

    @Override
    public Iterable<Node> getOpenNodes() {
        return open;
    }

    @Override
    public Iterable<Node> getClosedNodes() {
        return closed;
    }

}
