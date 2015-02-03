package com.hiilimonoksidi.tiralabra.datastructures;

import java.util.Iterator;

/**
 * Hajautustauluun perustuva joukko.
 *
 * @author Janne Ruoho
 * @param <E> Joukkoon talletettavien elementtien tyyppi
 */
public class HashSet<E> implements Iterable<E> {

    private Entry<E>[] table;
    private int tableSize = 64;
    private int entryCount = 0;
    private float loadFactorThreshold = 2 / 3f;

    public HashSet() {
        table = new Entry[tableSize];
    }

    public HashSet(int initialSize) {
        tableSize = initialSize;
        table = new Entry[tableSize];
    }

    /**
     * Palauttaa joukon elementtien lukumäärän.
     *
     * @return Joukon koko
     */
    public int size() {
        return entryCount;
    }

    /**
     * Lisää elementin joukkoon.
     *
     * @param element Lisättävä alkio
     */
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Can't add null to the set");
        }
        
        if (getLoadFactor() > loadFactorThreshold) {
            grow();
        }

        addToTable(element, table);

        entryCount++;
    }

    /**
     * Lisää elementin annettuun tauluun.
     *
     * @param element Lisättävä elementti
     * @param table Taulu johon elementti lisätään
     */
    private void addToTable(E element, Entry<E>[] table) {
        if (tableContains(table, element)) {
            return;
        }

        Entry newEntry = new Entry(element);
        int index = hash(element, table.length);

        Entry entry = table[index];
        if (entry == null) {
            table[index] = newEntry;
        } else {
            while (entry.next != null) {
                entry = entry.next;
            }
            entry.next = newEntry;
        }
    }

    /**
     * Poistaa elementin joukosta.
     *
     * @param element Poistettava elementti
     */
    public void remove(E element) {
        int index = hash(element, tableSize);
        Entry entry = table[index];

        if (entry == null) {
            return;
        }

        if (entry.element.equals(element)) {
            table[index] = entry.next;
            entryCount--;
            return;
        }

        while (entry.next != null) {
            if (entry.next.element.equals(element)) {
                entry.next = entry.next.next;
                entryCount--;
            } else {
                entry = entry.next;
            }
        }
    }

    /**
     * Tarkistaa kuuluuko elementti joukkoon.
     *
     * @param element Testattava elementti
     * @return Tosi jos kuuluu, epätosi muutoin
     */
    public boolean contains(E element) {
        return tableContains(table, element);
    }

    /**
     * Tarkisata kuuluuko elementti annettuun tauluun.
     *
     * @param table Taulu, josta tarkistetaan
     * @param element Tarkistettava elementti
     * @return Tosi jos kuuluu, epätosi muutoin
     */
    private boolean tableContains(Entry<E>[] table, E element) {
        int index = hash(element, table.length);
        Entry entry = table[index];

        while (entry != null) {
            if (entry.element.equals(element)) {
                return true;
            }
            entry = entry.next;
        }

        return false;
    }

    /**
     * Laskee hajautusarvon elementille.
     *
     * @param element Elementti, jonka hajautusarvo lasketaan
     * @param limit Hajautusarvon maksimirarvo
     * @return Hajautusarvo
     */
    private int hash(E element, int limit) {
        return Math.abs(element.hashCode() % limit);
    }

    /**
     * Palauttaa joukon kuormittumiskertoimen.
     *
     * @return Kuormittumiskerroin
     */
    private float getLoadFactor() {
        return entryCount / (float) tableSize;
    }

    /**
     * Tuplaa joukon koon.
     */
    private void grow() {
        int newSize = tableSize * 2;
        Entry<E>[] tmp = new Entry[newSize];

        for (E e : this) {
            addToTable(e, tmp);
        }

        table = tmp;
        tableSize = newSize;
    }

    @Override
    public String toString() {
        String str = "";
        for (Entry e : table) {
            if (e != null) {
                str += "[" + e.toString() + "]\n";
            } else {
                str += "[]\n";
            }
        }
        return str;
    }

    @Override
    public Iterator<E> iterator() {
        return new SetIterator();
    }

    /**
     * Tauluihin tallennettava alkio, joka sisältää elementin ja viittauksen
     * samassa indeksissä olevaan seuraavaan alkioon.
     *
     * @param <T> Sisältävän elementin tyyppi
     */
    private static class Entry<T> {

        final T element;
        Entry next;

        public Entry(T element) {
            this.element = element;
        }

        @Override
        public String toString() {
            String str = "";
            str += element.toString();
            if (next != null) {
                str += " -> " + next.toString();
            }
            return str;
        }
    }

    /**
     * Iteraattori.
     */
    private class SetIterator implements Iterator<E> {

        private Entry<E> currentEntry;
        private int index = 0;

        public SetIterator() {
            prepareNextEntry();
        }

        @Override
        public boolean hasNext() {
            return currentEntry != null;
        }

        @Override
        public E next() {
            E element = currentEntry.element;
            currentEntry = currentEntry.next;

            if (currentEntry == null) {
                prepareNextEntry();
            }

            return element;
        }

        private void prepareNextEntry() {
            while (currentEntry == null && index < tableSize) {
                currentEntry = table[index++];
            }
        }
    }
}
