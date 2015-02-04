package com.hiilimonoksidi.tiralabra.datastructures;

/**
 * Jono, joka on toteutettu linkitettynä listana.
 *
 * @author Janne Ruoho
 * @param <E> Jonoon talletettavien elementtien tyyppi.
 */
public class Queue<E> extends LinkedList<E> {

    /**
     * Lisää elementin jonon perään.
     *
     * @param element Lisättävä elementti
     */
    public void enqueue(E element) {
        add(element);
    }

    /**
     * Palauttaa jonon edessä olevan elementin.
     *
     * @return Jonon pää
     */
    public E dequeue() {
        return removeHead();
    }
}
