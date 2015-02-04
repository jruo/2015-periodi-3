package com.hiilimonoksidi.tiralabra.datastructures;

/**
 * Pino, joka on toteutettu linkitettynä listana.
 *
 * @author Janne Ruoho
 * @param <E> Pinoon talletettavien elementtien tyyppi
 */
public class Stack<E> extends LinkedList<E> {

    /**
     * Lisää elementin pinoon.
     *
     * @param element Lisättävä elementti
     */
    public void push(E element) {
        add(element);
    }

    /**
     * Palauttaa pinon päällä olevan elementin.
     *
     * @return Pinon päällimmäinen elementti
     */
    public E pop() {
        return removeTail();
    }
}
