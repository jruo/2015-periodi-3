package com.hiilimonoksidi.tiralabra.misc;

/**
 * Yksinkertainen työkalu komentoriviargumenttien lukemiseen.
 *
 * @author Janne Ruoho
 */
public class Arguments {

    private final String[] args;

    public Arguments(String[] args) {
        this.args = args;
    }

    /**
     * Palauttaa kaikkien argumentien lukumäärän.
     *
     * @return Argumenttien lukumäärä
     */
    public int length() {
        return args.length;
    }

    /**
     * Tarkistaa onko argumenttien joukossa tiettyä argumenttia.
     *
     * @param argument Targistettava argumentti
     * @return Tosi jos on, false muutoin
     */
    public boolean hasArgument(String argument) {
        for (String arg : args) {
            if (arg.equals(argument)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Palauttaa nimetyn argumentin. Käytännössä palauttaa parametrinä annettua
     * argumenttia vastaavan seuraavan argumentin. Eli jos argumentit ovat "-a
     * jotain -b argumentteja", niin getArgument("-a") palauttaisi "jotain" ja
     * getArgument("-b") palauttaisi "argumentteja".
     *
     * @param option Halutun argumentin flagi
     * @return Haluttu argumentti tai null, jos sitä ei löytynyt
     */
    public String getArgument(String option) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(option) && i + 1 < args.length) {
                return args[i + 1];
            }
        }
        return null;
    }
}
