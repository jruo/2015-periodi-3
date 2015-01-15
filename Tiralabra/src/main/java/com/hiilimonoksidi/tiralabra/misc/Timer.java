package com.hiilimonoksidi.tiralabra.misc;

/**
 * Yksinkertainen ajastin.
 *
 * @author Janne Ruoho
 */
public class Timer {

    private long start, end;

    /**
     * Käynnistää ajastimen.
     */
    public void start() {
        start = System.nanoTime();
    }

    /**
     * Pysäyttää ajastimen.
     */
    public void stop() {
        end = System.nanoTime();
    }

    /**
     * Palauttaa ajastimen ajan millisekunteina. Palauttaa luotettavan tuloksen
     * ainoastaan, jos metodit start() ja stop() on kutsuttu aikaisemmin.
     *
     * @return Aika millisekunteina
     */
    public double getTime() {
        return (end - start) / 1000000d;
    }
}
