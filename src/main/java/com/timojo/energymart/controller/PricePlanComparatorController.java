package com.timojo.energymart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timojo.energymart.model.PricePlan;
import com.timojo.energymart.model.Recommendation;
import com.timojo.energymart.service.PricePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plans")
public class PricePlanComparatorController {
    private final PricePlanService pricePlanService;
    private final ObjectMapper mapper;

    @Autowired
    public PricePlanComparatorController(PricePlanService pricePlanService, ObjectMapper mapper) {
        this.pricePlanService = pricePlanService;
        this.mapper = mapper;
    }

    @GetMapping("/recommend/{accountId}")
    public ResponseEntity<List<Recommendation>> recommendPricePlans(@PathVariable Integer accountId, @RequestParam(value = "limit", required = false) Integer limit) {
        Map<PricePlan, BigDecimal> consumptionCostForPricePlans = pricePlanService.get7DayCostOfElectricityForEachPricePlan(accountId);

        if ( consumptionCostForPricePlans.isEmpty() ) {
            return ResponseEntity.notFound().build();
        }

        List<Recommendation> recommendations = consumptionCostForPricePlans.entrySet()
                .stream()
                .map(entry -> new Recommendation(entry.getKey().getEnergySupplier(), entry.getKey().getPlanName(),
                        entry.getKey().getUnitRate(), entry.getKey().getRating(), entry.getValue()))
                .collect(Collectors.toList());

        recommendations.sort(Comparator.comparing(Recommendation::getUsageCost));

        if (limit != null && limit < recommendations.size()) {
            recommendations = recommendations.subList(0, limit);
        }

        return ResponseEntity.ok(recommendations);
    }
}
