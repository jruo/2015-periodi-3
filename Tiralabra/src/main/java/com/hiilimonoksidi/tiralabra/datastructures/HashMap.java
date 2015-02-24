package com.hiilimonoksidi.tiralabra.datastructures;

import com.hiilimonoksidi.tiralabra.datastructures.HashMap.Entry;
import java.util.Iterator;
import java.util.Objects;

/**
 * Assosiatiivinen taulukko (map). Toteutettu HashSetin avulla.
 *
 * @author Janne Ruoho
 * @param <K> Avaimen tyyppi
 * @param <V> Arvon tyyppi
 */
public class HashMap<K, V> implements Iterable<Entry<K,V>> {

    private final HashSet<Entry<K,V>> map;

    public HashMap() {
        map = new HashSet<>();
    }

    /**
     * Lisää avain-arvo -parin taulukkoon.
     *
     * @param key Avain
     * @param value Arvo
     */
    public void put(K key, V value) {
        Entry<K, V> entry = new Entry<>(key, value);
        map.add(entry);
    }

    /**
     * Tarkistaa onko taulukossa paria avaimella.
     *
     * @param key Avain
     * @return Tosi/epätosi
     */
    public boolean contains(K key) {
        Entry<K, V> dummyEntry = new Entry<>(key, null);
        return map.contains(dummyEntry);
    }

    /**
     * Palauttaa avainta vastaavan arvon.
     *
     * @param key Avain
     * @return Arvo
     */
    public V get(K key) {
        Entry<K, V> dummyEntry = new Entry<>(key, null);
        Entry<K, V> mapEntry = map.get(dummyEntry);

        if (mapEntry != null) {
            return mapEntry.value;
        }

        return null;
    }

    /**
     * Poistaa avainta vastaavan parin.
     *
     * @param key
     */
    public void remove(K key) {
        Entry<K, V> dummyEntry = new Entry<>(key, null);
        map.remove(dummyEntry);
    }

    /**
     * Palauttaa taulukon koon.
     *
     * @return Taulukon avain-arvo -parien lukumäärä
     */
    public int size() {
        return map.size();
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return map.iterator();
    }

    /**
     * Taulukkoon talletettava olio, joka sisältää avaimen ja sen arvon.
     *
     * @param <L> Avaimen tyyppi
     * @param <X> Arvon tyyppi
     */
    public static class Entry<L, X> {

        final L key;
        final X value;

        public Entry(L key, X value) {
            this.key = key;
            this.value = value;
        }

        public L getKey() {
            return key;
        }

        public X getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            return 145 + Objects.hashCode(this.key);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final Entry<?, ?> other = (Entry<?, ?>) obj;
            return Objects.equals(this.key, other.key);
        }
    }
}
