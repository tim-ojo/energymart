package com.timojo.energymart.model;

import com.timojo.energymart.util.WeekHour;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PricePlan {
    private static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");

    private final String energySupplier;
    private final String planName;
    private final BigDecimal unitRate; // unit price per kWh
    private final Double rating;
    private final List<OffPeakTimeMultiplier> offPeakTimeMultipliers;

    public PricePlan(String energySupplier, String planName, BigDecimal unitRate, Double rating,
                     List<OffPeakTimeMultiplier> offPeakTimeMultipliers) {
        this.energySupplier = energySupplier;
        this.planName = planName;
        this.unitRate = unitRate;
        this.rating = rating;
        this.offPeakTimeMultipliers = offPeakTimeMultipliers;
    }

    public String getEnergySupplier() {
        return energySupplier;
    }

    public String getPlanName() {
        return planName;
    }

    public BigDecimal getUnitRate() {
        return unitRate;
    }

    public BigDecimal getPrice(BigDecimal reading, Instant instant) {
        ZonedDateTime dateTime = instant.atZone(UTC_ZONE_ID);
        int weekHour = WeekHour.fromDateTime(dateTime);
        Optional<OffPeakTimeMultiplier> offPeakTimeMultiplier = offPeakTimeMultipliers.stream()
                .filter(multiplier -> multiplier.getHourOfWeek() == weekHour)
                .findFirst();

        BigDecimal adjustedRate = offPeakTimeMultiplier.map(multiplier -> unitRate.multiply(multiplier.getMultiplier())).orElse(unitRate);
        return reading.multiply(adjustedRate);
    }

    public Double getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricePlan pricePlan = (PricePlan) o;
        return Objects.equals(energySupplier, pricePlan.energySupplier) &&
                Objects.equals(planName, pricePlan.planName) &&
                Objects.equals(unitRate, pricePlan.unitRate) &&
                Objects.equals(rating, pricePlan.rating) &&
                Objects.equals(offPeakTimeMultipliers, pricePlan.offPeakTimeMultipliers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(energySupplier, planName, unitRate, rating, offPeakTimeMultipliers);
    }

    @Override
    public String toString() {
        return "PricePlan{" +
                "energySupplier='" + energySupplier + '\'' +
                ", planName='" + planName + '\'' +
                ", unitRate=" + unitRate +
                ", rating=" + rating +
                ", offPeakTimeMultipliers=" + offPeakTimeMultipliers +
                '}';
    }
}
