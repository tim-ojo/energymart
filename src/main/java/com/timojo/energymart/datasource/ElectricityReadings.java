package com.timojo.energymart.datasource;

import com.timojo.energymart.model.ElectricityReading;
import com.timojo.energymart.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class ElectricityReadings {
    private Map<String, List<ElectricityReading>> meterAssociatedReadings;

    private Accounts accounts;

    @Autowired
    public ElectricityReadings(Accounts accounts) {
        this.accounts = accounts;
    }

    @PostConstruct
    public void init() {
        meterAssociatedReadings = generateMeterReadings();
    }

    private Map<String, List<ElectricityReading>> generateMeterReadings() {
        int numReadingsPerMeter = 200;
        Map<String, List<ElectricityReading>> meterReadings = new HashMap<>(accounts.getUserAccountsMap().size());

        for (UserAccount account : accounts.getUserAccountsMap().values()) {
            String meterId = account.getMeterId();

            Random readingRandomizer = new Random();
            Instant currentHour = Instant.now().truncatedTo(ChronoUnit.HOURS);
            List<ElectricityReading> readings = new ArrayList<>();

            for (int i = 0; i < numReadingsPerMeter; i++) {
                double reading = readingRandomizer.nextDouble() * 3;
                ElectricityReading electricityReading = new ElectricityReading(currentHour.minusSeconds(i * 3600),
                        BigDecimal.valueOf(reading), meterId);
                readings.add(electricityReading);
            }

            meterReadings.put(meterId, readings);
        }

        return meterReadings;
    }

    public List<ElectricityReading> getReadingsForMeter(String meterId) {
        return meterAssociatedReadings.containsKey(meterId) ? meterAssociatedReadings.get(meterId) : new ArrayList<>();
    }

    public void add(ElectricityReading reading) {
        meterAssociatedReadings.computeIfAbsent(reading.getMeterId(), r -> new ArrayList<>()).add(reading);
    }
}
