package com.hiilimonoksidi.tiralabra.datastructures;

/**
 * Kahteen suuntaan linkitetty lista.
 *
 * @author Janne Ruoho
 * @param <E> Listaan talletettavien elementtien tyyppi.
 */
public class LinkedList<E> {

    private Entry<E> head;
    private Entry<E> tail;
    private int size = 0;

    /**
     * Lisää elementin listan loppuun.
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
            tail.next.previous = tail;
            tail = tail.next;
        }
        size++;
    }

    /**
     * Poistaa ja palauttaa listan viimeisen elementin.
     *
     * @return Listan viimeinen elementti
     */
    public E removeTail() {
        if (size == 0) {
            return null;
        }

        E element = (E) tail.element;
        if (tail.previous != null) {
            tail = tail.previous;
        }
        size--;

        return element;
    }

    /**
     * Poistaa ja palauttaa listan ensimmäisen elementin.
     *
     * @return Listan ensimmäinen elementti
     */
    public E removeHead() {
        if (size == 0) {
            return null;
        }

        E element = (E) head.element;
        if (head.next != null) {
            head = head.next;
        }
        size--;

        return element;
    }

    /**
     * Poistaa tietyn elementin listasta.
     *
     * @param element Poistettava elementti
     */
    public void remove(E element) {
        Entry entry = head;
        while (!element.equals(entry.element)) {
            if (entry.next == null) {
                return;
            }
            entry = entry.next;
        }
        
        if (entry.previous != null && entry.next != null) {
            entry.previous.next = entry.next;
            entry.next.previous = entry.previous;
        } else if (entry.previous == null) {
            head = entry.next;
        } else if (entry.next == null) {
            tail = entry.previous;
        }
        
        size--;
    }

    /**
     * Palauttaa elementin indeksistä.
     *
     * @param index Indeksi, josta elementti palautetaan
     * @return Elementti indeksissä
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Entry entry = head;
        for (int i = 0; i < index; i++) {
            entry = entry.next;
        }

        return (E) entry.element;
    }

    /**
     * Palauttaa listan elementtien lukumäärän.
     *
     * @return Listan koko
     */
    public int size() {
        return size;
    }

    /**
     * Tarkistaa onko lista tyhjä.
     *
     * @return Tosi jos tyhjä, epätosi muuten
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Listan alkio, joka sisältää siihen tallennetun elementin sekä viittauksen
     * edelliseen ja seuraavaan alkioon listassa.
     *
     * @param <T> Alkioon talletettavan elementin tyyppi
     */
    private static class Entry<T> {

        final T element;
        Entry next;
        Entry previous;

        public Entry(T element) {
            this.element = element;
        }
    }
}
