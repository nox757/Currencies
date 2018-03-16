package com.chibisovap.models;

public class RateObject {

    private String name;
    private double rate;


    public RateObject(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return this.name;
    }

    public double getRate() {
        return this.rate;
    }
}
