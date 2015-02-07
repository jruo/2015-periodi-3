package com.hiilimonoksidi.tiralabra.application;

import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;

/**
 * Algoritmien step-by-step -suoritustilan kontrolleri.
 *
 * @author Janne Ruoho
 */
public interface StepController {

    /**
     * Asettaa tämänhetkisen askeleen käsittelyssä olevat solmut.
     *
     * @param open Käsittelyssä olevat solmut
     */
    public void setOpenNodes(Iterable<Node> open);

    /**
     * Asettaa tämänhetkisen askeleen käsitellyt solmut.
     *
     * @param closed Käsitellyt solmut
     */
    public void setClosedNodes(Iterable<Node> closed);

    /**
     * Asettaa löydetyn polun.
     *
     * @param path Polku
     */
    public void setPath(Path path);

    /**
     * Palauttaa nukuttavan ajan askelten välillä.
     * 
     * @return Nukuttava aika
     */
    public int getDelay();
}
