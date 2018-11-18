package com.timojo.energymart.service;

import com.timojo.energymart.datasource.Accounts;
import com.timojo.energymart.datasource.ElectricityReadings;
import com.timojo.energymart.model.ElectricityReading;
import com.timojo.energymart.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeterReadingService {
    private ElectricityReadings electricityReadings;
    private Accounts accounts;

    @Autowired
    public MeterReadingService(ElectricityReadings electricityReadings, Accounts accounts) {
        this.electricityReadings = electricityReadings;
        this.accounts = accounts;
    }

    public List<ElectricityReading> getReadings(int accountId) {
        UserAccount userAccount = accounts.getAccount(accountId);
        if (userAccount == null || userAccount.getMeterId() == null)
            return new ArrayList<>();

        String meterId = userAccount.getMeterId();
        return electricityReadings.getReadingsForMeter(meterId);
    }

    public void storeReading(ElectricityReading reading) {
        electricityReadings.add(reading);
    }
}
