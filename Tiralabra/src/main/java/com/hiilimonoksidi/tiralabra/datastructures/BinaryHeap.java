package com.hiilimonoksidi.tiralabra.datastructures;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Binäärikeko.
 *
 * @author Janne Ruoho
 * @param <E> Kekoon talletettavien elementtien tyyppi
 */
public class BinaryHeap<E> implements Heap<E> {

    protected Object[] array;
    protected int index = 1;
    protected int length = 16;

    private Comparator<E> comparator;

    /**
     * Luo uuden keon comparatorilla. Keko järjestetään minimikeoksi, eli
     * comparatorin vertaamasta kahdesta elementista piemenpi on lähempänä
     * juurta.
     *
     * @param comparator Comparator
     */
    public BinaryHeap(Comparator<E> comparator) {
        this.comparator = comparator;
        array = new Object[length];
    }

    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Can't add null to the heap");
        }

        if (index >= length) {
            grow();
        }

        array[index] = element;
        moveUp(index++);
    }

    @Override
    public E remove() {
        if (index == 1) {
            return null;
        }

        E root = (E) array[1];
        array[1] = array[--index];
        moveDown(1);

        return root;
    }

    @Override
    public void remove(E element) {
        int ind = find(element);
        if (ind == 0) {
            return;
        }

        array[ind] = array[--index];

        update(ind);
    }

    @Override
    public void update(E element) {
        int ind = find(element);
        if (ind == 0) {
            return;
        }
        update(ind);
    }

    @Override
    public int size() {
        return index - 1;
    }

    @Override
    public boolean isEmpty() {
        return index == 1;
    }

    /**
     * Etsii tietyn elementin indeksin keossa.
     *
     * @param element Etsittävä elementti
     * @return Elementin indeksi tai 0, jos ei löytynyt
     */
    int find(E element) {
        int ind = 0;
        for (int i = 1; i < index; i++) {
            if (element.equals(array[i])) {
                ind = i;
            }
        }
        return ind;
    }

    /**
     * Siirtää elementin tietyssä sijainnissa uuteen paikkaan niin että kekoeho
     * on voimassa.
     *
     * @param ind Siirrettävän elementin indeksi
     */
    private void update(int ind) {
        if (ind == 1) {
            moveDown(1);
            return;
        }
        int parentInd = parent(ind);
        E self = get(ind);
        E parent = get(parentInd);

        if (isParent(self, parent)) {
            moveUp(ind);
        } else {
            moveDown(ind);
        }
    }

    /**
     * Siirtää elementtiä ylös keossa, kunnes kekoehto on voimassa koko puulle.
     *
     * @param ind Siirrettävän elementin indeksi
     */
    private void moveUp(int ind) {
        if (ind == 1) {
            return;
        }

        int parentInd = parent(ind);
        E self = get(ind);
        E parent = get(parentInd);

        if (isParent(self, parent)) {
            swap(ind, parentInd);
            moveUp(parentInd);
        }
    }

    /**
     * Siirtää elementtia alas keossa, kunnes kekoehto on voimassa koko puulle.
     * (aka heapify)
     *
     * @param ind Siirrettävän elementin indeksil
     */
    private void moveDown(int ind) {
        if (ind >= index - 1) {
            return;
        }

        E self = get(ind);

        int leftInd = left(ind);
        int rightInd = right(ind);

        if (leftInd > index - 1) {
            return;
        }

        E left = get(leftInd);
        E right = get(rightInd);

        if (rightInd > index - 1) {
            if (isChild(self, left)) {
                swap(ind, leftInd);
                moveDown(leftInd);
            }
        } else {
            boolean leftBigger = comparator.compare(left, right) <= 0;
            E bigger = leftBigger ? left : right;
            int biggerInd = leftBigger ? leftInd : rightInd;

            if (isChild(self, bigger)) {
                swap(ind, biggerInd);
                moveDown(biggerInd);
            }
        }
    }

    /**
     * Palauttaa elementin indeksissä.
     *
     * @param ind Elementin indeksi
     * @return Elementti
     */
    private E get(int ind) {
        if (ind >= length) {
            return null;
        }
        return (E) array[ind];
    }

    /**
     * Palauttaa indeksissä olevan elementin vanhemman indeksin.
     *
     * @param ind Sen elementin indeksi, jonka vanhempi haluataan
     * @return Vanhemman indeksi
     */
    private int parent(int ind) {
        return ind / 2;
    }

    /**
     * Palauttaa indeksissä olevan elementin vasemman lapsen indeksin.
     *
     * @param ind Sen elementin indeksi, jonka vasen lapsi halutaan.
     * @return Vasemman lapsen indeksi
     */
    private int left(int ind) {
        return ind * 2;
    }

    /**
     * Palauttaa indeksissä olevan elementin oikean lapsen indeksin.
     *
     * @param ind Sen elementin indeksi, jonka oikea lapsi halutaan.
     * @return OIkean lapsen indeksi
     */
    private int right(int ind) {
        return (ind * 2) + 1;
    }

    /**
     * Vaihtaa kahden elementin paikkaa keossa.
     *
     * @param ind1 Ensimmäisen elementin indeksi
     * @param ind2 Toisen elementin indeksi
     */
    void swap(int ind1, int ind2) {
        Object o = array[ind1];
        array[ind1] = array[ind2];
        array[ind2] = o;
    }

    /**
     * Tuplaa keon koon.
     */
    private void grow() {
        length *= 2;
        Object[] tmp = new Object[length];
        System.arraycopy(array, 0, tmp, 0, index);
        array = tmp;
    }

    /**
     * Tarkistaa, kuuluisiko parent-elementin olla child-elementin vanhempi.
     *
     * @param parent Testattava vanhempi
     * @param child Testattava lapsi
     * @return Tosi jos parent kuuluisi olla childin vahnempi
     */
    private boolean isParent(E parent, E child) {
        return comparator.compare(parent, child) < 0;
    }

    /**
     * Tarkistaa, kuuluisiko child-elementin olla parent-elementin lapsi.
     *
     * @param child Testattava lapsi
     * @param parent Testattava vanhempi
     * @return Tosi jos child kuuluisi olla parentin lapsi
     */
    private boolean isChild(E child, E parent) {
        return comparator.compare(child, parent) > 0;
    }

    @Override
    public String toString() {
        String ret = "";
        int levelWidth = 1;
        int currentWidth = 1;
        for (int i = 1; i < index; i++) {
            E e = get(i);
            ret += e + " ";
            if (++currentWidth > levelWidth) {
                currentWidth = 1;
                levelWidth *= 2;
                ret += "\n";
            }
        }
        return ret;
    }

    @Override
    public Iterator<E> iterator() {
        return new HeapIterator();
    }
    
    /**
     * Iteraattori.
     */
    public class HeapIterator implements Iterator<E> {
        
        private int currentIndex = 1;

        @Override
        public boolean hasNext() {
            return currentIndex < index;
        }

        @Override
        public E next() {
            return (E) array[currentIndex++];
        }
        
    }
}
