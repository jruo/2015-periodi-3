package com.hiilimonoksidi.tiralabra.datastructures;

import java.util.Iterator;

/**
 * Lista.
 *
 * @author Janne Ruoho
 * @param <E> Listaan talletettavien elementtien tyyppi
 */
public class ArrayList<E> implements Iterable<E> {

    private Object[] elements;
    private int length = 16;
    private int index = 0;

    public ArrayList() {
        elements = new Object[length];
    }

    /**
     * Lisää elementin listaan.
     *
     * @param element Lisättävä olio
     */
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Can't add null to the list");
        }

        if (index >= length) {
            grow();
        }

        elements[index++] = element;
    }

    /**
     * Poistaa elementin listasta.
     *
     * @param element Poistettava olio
     */
    public void remove(E element) {
        if (element == null) {
            return;
        }

        int removeIndex = 0;
        for (int i = 0; i < index; i++) {
            if (element.equals(elements[i])) {
                removeIndex = i;
                break;
            }

            if (i + 1 == index) {
                return;
            }
        }

        System.arraycopy(elements, removeIndex + 1, elements, removeIndex, index - removeIndex - 1);
        index--;
    }

    /**
     * Palauttaa listan elementtien lukumäärän.
     *
     * @return Listan koko
     */
    public int size() {
        return index;
    }

    /**
     * Palauttaa tietyn elementin listasta.
     *
     * @param i Olion indeksi
     * @return Olio indeksissä i
     */
    public E get(int i) {
        if (i >= index || i < 0) {
            throw new ArrayIndexOutOfBoundsException("index: " + i);
        }

        return (E) elements[i];
    }

    /**
     * Tarkistaa onko lista tyhjä.
     *
     * @return Tosi jos tyhjä, epätosi muuten
     */
    public boolean isEmpty() {
        return index == 0;
    }

    /**
     * Kasvattaa listan koon kaksinkertaiseksi.
     */
    private void grow() {
        length *= 2;
        Object[] tmp = new Object[length];
        System.arraycopy(elements, 0, tmp, 0, index);
        elements = tmp;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }
    
    /**
     * Iteraattori
     */
    public class ListIterator implements Iterator<E> {
        
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < index;
        }

        @Override
        public E next() {
            return (E) elements[currentIndex++];
        }
        
    }
}
