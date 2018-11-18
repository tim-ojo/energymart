package com.timojo.energymart.model;

import java.math.BigDecimal;

public class OffPeakTimeMultiplier {
    private final int hourOfWeek;
    private final BigDecimal multiplier;

    public OffPeakTimeMultiplier(int hourOfWeek, BigDecimal multiplier) {
        this.hourOfWeek = hourOfWeek;
        this.multiplier = multiplier;
    }

    public int getHourOfWeek() {
        return hourOfWeek;
    }

    public BigDecimal getMultiplier() {
        return multiplier;
    }
}
