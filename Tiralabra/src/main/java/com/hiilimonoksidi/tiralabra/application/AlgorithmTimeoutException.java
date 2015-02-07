package com.hiilimonoksidi.tiralabra.application;

/**
 * Heitetään kun algoritmia ei voitu suorittaa loppuun aikarajoituksen takia.
 *
 * @author Janne Ruoho
 */
public class AlgorithmTimeoutException extends Exception {

    public AlgorithmTimeoutException() {
    }

    public AlgorithmTimeoutException(String msg) {
        super(msg);
    }
}
