package com.timojo.energymart.controller;

import com.timojo.energymart.datasource.Accounts;
import com.timojo.energymart.model.ElectricityReading;
import com.timojo.energymart.model.UserAccount;
import com.timojo.energymart.service.MeterReadingService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/readings")
public class MeterReadingController {

    private MeterReadingService meterReadingService;
    private Accounts accounts;

    @Autowired
    public MeterReadingController(MeterReadingService meterReadingService, Accounts accounts) {
        this.meterReadingService = meterReadingService;
        this.accounts = accounts;
    }

    @Timed
    @PostMapping("/store")
    public ResponseEntity storeReading(@RequestBody ElectricityReading reading) {
        if (!isValidReading(reading)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        meterReadingService.storeReading(reading);
        return ResponseEntity.ok().build();
    }

    private boolean isValidReading(ElectricityReading reading) {
        Optional<UserAccount> account = accounts.getAccountByMeterId(reading.getMeterId());
        return account.isPresent() && reading.getReading().doubleValue() > 0.0 && reading.getHour().isBefore(Instant.now());
    }

    @Timed
    @GetMapping("/read/{accountId}")
    public ResponseEntity<List<ElectricityReading>> readReadings(@PathVariable Integer accountId,
                                                                 @RequestParam(value = "limit", required = false) Integer limit) {
        List<ElectricityReading> readings = meterReadingService.getReadings(accountId);

        if (readings.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (limit != null && limit < readings.size()) {
            readings = readings.subList(0, limit);
        }

        return ResponseEntity.ok(readings);
    }
}
