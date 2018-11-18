package com.timojo.energymart.service;

import com.timojo.energymart.datasource.PricePlans;
import com.timojo.energymart.model.ElectricityReading;
import com.timojo.energymart.model.PricePlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PricePlanService {
    private final MeterReadingService meterReadingService;
    private final PricePlans pricePlans;

    @Autowired
    public PricePlanService(MeterReadingService meterReadingService, PricePlans pricePlans) {
        this.meterReadingService = meterReadingService;
        this.pricePlans = pricePlans;
    }

    public Map<PricePlan, BigDecimal> get7DayCostOfElectricityForEachPricePlan(int accountId) {
        List<ElectricityReading> electricityReadings = meterReadingService.getReadings(accountId);

        if ( electricityReadings.isEmpty() ) {
            return new HashMap<>();
        }

        electricityReadings.sort(Comparator.comparing(ElectricityReading::getHour).reversed());
        List<ElectricityReading> last7DaysOfReadings = electricityReadings.stream().limit(7 * 24).collect(Collectors.toList());

        return pricePlans.getPricePlanList().stream().collect(Collectors.toMap(pp -> pp, pp -> calculateTotalCost(last7DaysOfReadings, pp)));
    }

    private BigDecimal calculateTotalCost(List<ElectricityReading> electricityReadings, PricePlan pricePlan) {
        return electricityReadings.stream()
                .map(reading -> pricePlan.getPrice(reading.getReading(), reading.getHour()))
                .reduce(BigDecimal.ZERO, (p1, p2) -> p1.add(p2));
    }

    public Map<PricePlan, BigDecimal> getConsumptionCostOfElectricityReadingsForEachPricePlan(int accountId) {
        List<ElectricityReading> electricityReadings = meterReadingService.getReadings(accountId);

        if ( electricityReadings.isEmpty() ) {
            return new HashMap<>();
        }

        return pricePlans.getPricePlanList().stream().collect(Collectors.toMap(pp -> pp, pp -> calculateCost(electricityReadings, pp)));
    }

    private BigDecimal calculateCost(List<ElectricityReading> electricityReadings, PricePlan pricePlan) {
        BigDecimal averageReading = calculateAverageReading(electricityReadings);
        BigDecimal timeElapsed = calculateTimeElapsed(electricityReadings);

        BigDecimal averagedUsage = averageReading.divide(timeElapsed, RoundingMode.HALF_UP);
        // avg KWh * price per KWh
        return averagedUsage.multiply(pricePlan.getUnitRate());
    }

    private BigDecimal calculateAverageReading(List<ElectricityReading> electricityReadings) {
        BigDecimal summedReadings = electricityReadings.stream()
                .map(ElectricityReading::getReading)
                .reduce(BigDecimal.ZERO, (reading, accumulator) -> reading.add(accumulator));

        return summedReadings.divide(BigDecimal.valueOf(electricityReadings.size()), RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTimeElapsed(List<ElectricityReading> electricityReadings) {
        ElectricityReading first = electricityReadings.stream()
                .min(Comparator.comparing(ElectricityReading::getHour))
                .get();
        ElectricityReading last = electricityReadings.stream()
                .max(Comparator.comparing(ElectricityReading::getHour))
                .get();

        return BigDecimal.valueOf(Duration.between(first.getHour(), last.getHour()).getSeconds() / 3600.0);
    }
}
