package com.hiilimonoksidi.tiralabra.datastructures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

/**
 * Binäärikeko, jonka jokainen elementti on indeksoitu, jotta find-operaatio
 * toimii vakioajassa.
 *
 * @author Janne Ruoho
 * @param <E> Kekoon talletettavien elementtien tyyppi
 */
public class IndexedBinaryHeap<E> implements Heap<E>, Iterable<E> {

    private final EntryHeap heap;
    private final HashSet<Entry<E>> indexSet;

    /**
     * Luo uuden keon comparatorilla. Järjestetään minimikeoksi.
     *
     * @param comparator Comparator
     */
    public IndexedBinaryHeap(Comparator<E> comparator) {
        heap = new EntryHeap(comparator);
        indexSet = new HashSet<>();
    }

    @Override
    public void add(E element) {
        Entry<E> entry = new Entry<>(element);
        heap.add(entry);
        indexSet.add(entry);
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public E remove() {
        Entry<E> removed = heap.remove();
        if (removed != null) {
            indexSet.remove(removed);
            return removed.element;
        }
        return null;
    }

    @Override
    public void remove(E element) {
        Entry<E> dummyElement = new Entry<>(element);
        heap.remove(dummyElement);
        indexSet.remove(dummyElement);
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public void update(E element) {
        Entry<E> dummyElement = new Entry<>(element);
        heap.update(dummyElement);
    }

    @Override
    public Iterator<E> iterator() {
        final Iterator<Entry<E>> heapIterator = heap.iterator();

        return new Iterator<E>() {

            @Override
            public boolean hasNext() {
                return heapIterator.hasNext();
            }

            @Override
            public E next() {
                return heapIterator.next().element;
            }
        };
    }

    /**
     * Palauttaa sisältävän keon iteraattorin (testausta varten).
     *
     * @return Iteraattori
     */
    Iterator<Entry<E>> entryIterator() {
        return heap.iterator();
    }

    @Override
    public String toString() {
        return heap.toString();
    }

    /**
     * Varsinainen keko, johon elementit tallennetaan. Perii BinaryHeapin, mutta
     * jokaisen indeksejä muuttavan operaation jälkeen tallettaa indeksin
     * oikeaan Entryyn.
     */
    private class EntryHeap extends BinaryHeap<Entry<E>> {

        public EntryHeap(final Comparator<E> comparator) {
            super(new Comparator<Entry<E>>() {
                @Override
                public int compare(Entry<E> entry1, Entry<E> entry2) {
                    return comparator.compare(entry1.element, entry2.element);
                }
            });
        }

        @Override
        public void add(Entry<E> entry) {
            entry.index = super.index;
            super.add(entry);
        }

        @Override
        protected int find(Entry<E> entry) {
            Entry<E> found = indexSet.get(entry);
            if (found != null) {
                return found.index;
            }
            return 0;
        }

        @Override
        protected void swap(int ind1, int ind2) {
            Entry<E> entry1 = (Entry<E>) array[ind1];
            Entry<E> entry2 = (Entry<E>) array[ind2];

            entry1.index = ind2;
            entry2.index = ind1;

            array[ind1] = entry2;
            array[ind2] = entry1;
        }
    }

    /**
     * Kekoon talletettava alkio, joka sisältää varsinaisen talletettavan
     * elementin ja indeksin keossa.
     *
     * @param <T> Elementin tyyppi
     */
    static class Entry<T> {

        final T element;
        int index;

        public Entry(T element) {
            this.element = element;
        }

        @Override
        public int hashCode() {
            return 485 + Objects.hashCode(this.element);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Entry<?> other = (Entry<?>) obj;
            return Objects.equals(this.element, other.element);
        }

        @Override
        public String toString() {
            return "[" + index + ":" + element.toString() + "]";
        }
    }
}
