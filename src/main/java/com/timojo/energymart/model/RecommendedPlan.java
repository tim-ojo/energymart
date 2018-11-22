package com.timojo.energymart.model;

import com.timojo.energymart.util.WeekHour;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RecommendedPlan {
    private final String planName;
    private final String energySupplier;
    private final BigDecimal usageCost; // for the past 7 days
    private final BigDecimal unitRate; // unit price per kWh
    private final Double rating;

    public RecommendedPlan(String energySupplier, String planName, BigDecimal unitRate, Double rating,
                           BigDecimal usageCost) {
        this.energySupplier = energySupplier;
        this.planName = planName;
        this.unitRate = unitRate;
        this.rating = rating;
        this.usageCost = usageCost;
    }

    public String getPlanName() {
        return planName;
    }

    public String getEnergySupplier() {
        return energySupplier;
    }

    public BigDecimal getUsageCost() {
        return usageCost;
    }

    public BigDecimal getUnitRate() {
        return unitRate;
    }

    public Double getRating() {
        return rating;
    }
}
