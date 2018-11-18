package com.timojo.energymart.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class ElectricityReading {
    private Instant hour;
    private BigDecimal reading; // kW
    private String meterId;

    @SuppressWarnings("unused")
    public ElectricityReading() { }

    public ElectricityReading(Instant hour, BigDecimal reading, String meterId) {
        Objects.requireNonNull(hour, "hour should not be null");
        Objects.requireNonNull(reading, "reading should not be null");
        Objects.requireNonNull(meterId, "meterId should not be null");
        this.hour = hour;
        this.reading = reading;
        this.meterId = meterId;
    }

    public BigDecimal getReading() {
        return reading;
    }

    public Instant getHour() {
        return hour;
    }

    public String getMeterId() {
        return meterId;
    }
}
