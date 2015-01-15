package com.hiilimonoksidi.tiralabra.misc;

public class Timer {

    private long start, end;
    
    public void start() {
        start = System.nanoTime();
    }
    
    public void stop() {
        end = System.nanoTime();
    }
    
    public double getTime() {
        return (end - start) / 1000000d;
    }
}