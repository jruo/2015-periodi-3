package com.hiilimonoksidi.tiralabra.datastructures;

/**
 * Jono, joka on toteutettu linkitettynä listana.
 *
 * @author Janne Ruoho
 * @param <E> Jonoon talletettavien elementtien tyyppi.
 */
public class Queue<E> {

    private Entry head;
    private Entry tail;
    private int size = 0;

    /**
     * Lisää elementin jonoon.
     *
     * @param element Lisättävä elementti
     */
    public void add(E element) {
        Entry<E> entry = new Entry<>(element);

        if (size == 0) {
            head = entry;
            tail = entry;
        } else {
            tail.next = entry;
            tail = tail.next;
        }
        size++;
    }

    /**
     * Poistaa ja palauttaa jonon päässä olevan elementin.
     *
     * @return Jonon pää
     */
    public E remove() {
        if (size == 0) {
            return null;
        }
        E element = (E) head.element;
        if (head.next == null) {
            head = null;
            tail = null;
        } else {
            head = head.next;
        }
        size--;
        return element;
    }

    /**
     * Palauttaa jonon elementtien lukumäärän.
     *
     * @return Jonon koko
     */
    public int size() {
        return size;
    }

    /**
     * Tarkistaa onko jono tyhjä.
     *
     * @return Tosi jos on tyhjä, epätosi muuten.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Jonon alkio, joka sisätlää elementin ja viittauksen seuraavaan alkioon.
     *
     * @param <T> Elementin tyyppi
     */
    private static class Entry<T> {

        final T element;
        Entry next;

        public Entry(T element) {
            this.element = element;
        }
    }
}
