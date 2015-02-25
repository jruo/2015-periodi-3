package com.hiilimonoksidi.tiralabra.datastructures;

/**
 * Kekorajapinta
 *
 * @author Janne Ruoho
 * @param <E> Kekoon talletettavien elementtien tyyppi
 */
public interface Heap<E> {

    /**
     * Lisää elementin kekoon.
     *
     * @param element Lisättävä elementti
     */
    void add(E element);

    /**
     * Tarkistaa onko keko tyhjä.
     *
     * @return Tosi jos tyhjä, epätosi muutoin
     */
    boolean isEmpty();

    /**
     * Poistaa ja palauttaa keon juuren.
     *
     * @return Keon juurielementti
     */
    E remove();

    /**
     * Poistaa keosta tietyn elementin
     *
     * @param element
     */
    void remove(E element);

    /**
     * Palauttaa keon elementtien lukumäärän.
     *
     * @return Keon koko
     */
    int size();

    /**
     * Siirtää elementin uuteen paikkaan keossa jotta kekoeho on voimassa.
     *
     * @param element Siirrettävä elementti
     */
    void update(E element);
}
