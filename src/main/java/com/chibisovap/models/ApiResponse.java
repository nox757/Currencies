package com.chibisovap.models;

import java.util.Locale;

public class ApiResponse {

    private String base;
    private RateObject rates;

    public String getBase() {
        return this.base;
    }

    public RateObject getRates() {
        return this.rates;
    }


    @Override
    public String toString(){
        if (rates == null)
            return String.format("%s => %s : 1", base, base);
        else
            return String.format(Locale.ROOT,"%s => %s : %f", base, rates.getName(), rates.getRate());
    }
}
