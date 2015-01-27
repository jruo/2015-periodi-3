package com.hiilimonoksidi.tiralabra.datastructures;

/**
 * Lista.
 *
 * @author Janne Ruoho
 * @param <T> Listaan talletettavien olioiden tyyppi
 */
public class ArrayList<T> {

    private Object[] elements;
    private int length = 10;
    private int index = 0;

    public ArrayList() {
        elements = new Object[length];
    }

    /**
     * Lisää listaan olion.
     *
     * @param t Lisättävä olio
     */
    public void add(T t) {
        if (t == null) {
            throw new NullPointerException("Can't add null to the list");
        }

        if (index >= length) {
            grow();
        }
        
        elements[index++] = t;
    }

    /**
     * Poistaa listasta olion.
     *
     * @param t Poistettava olio
     */
    public void remove(T t) {
        if (t == null) {
            return;
        }

        int removeIndex = 0;
        for (int i = 0; i < index; i++) {
            if (t.equals(elements[i])) {
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
     * Palauttaa listan koon.
     *
     * @return Listan koko
     */
    public int size() {
        return index;
    }

    /**
     * Palauttaa tietyn olion listasta.
     *
     * @param i Olion indeksi
     * @return Olio indeksissä i
     */
    public T get(int i) {
        if (i >= index || i < 0) {
            throw new ArrayIndexOutOfBoundsException("index: " + i);
        }

        return (T) elements[i];
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
}
