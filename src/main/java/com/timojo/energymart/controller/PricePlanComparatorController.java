package com.timojo.energymart.controller;

import com.timojo.energymart.datasource.Accounts;
import com.timojo.energymart.model.PricePlan;
import com.timojo.energymart.model.Recommendation;
import com.timojo.energymart.model.RecommendedPlan;
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
    private final Accounts accounts;

    @Autowired
    public PricePlanComparatorController(PricePlanService pricePlanService, Accounts accounts) {
        this.pricePlanService = pricePlanService;
        this.accounts = accounts;
    }

    @GetMapping("/recommend/{accountId}")
    public ResponseEntity<Recommendation> recommendPricePlans(@PathVariable Integer accountId,
                                                              @RequestParam(value = "limit", required = false) Integer limit) {
        Map<PricePlan, BigDecimal> consumptionCostForPricePlans = pricePlanService.get7DayCostOfElectricityForEachPricePlan(accountId);

        if ( consumptionCostForPricePlans.isEmpty() ) {
            return ResponseEntity.notFound().build();
        }

        List<RecommendedPlan> recommendedPlans = consumptionCostForPricePlans.entrySet()
                .stream()
                .map(entry -> new RecommendedPlan(entry.getKey().getEnergySupplier(), entry.getKey().getPlanName(),
                        entry.getKey().getUnitRate(), entry.getKey().getRating(), entry.getValue()))
                .collect(Collectors.toList());

        recommendedPlans.sort(Comparator.comparing(RecommendedPlan::getUsageCost));

        if (limit != null && limit < recommendedPlans.size()) {
            recommendedPlans = recommendedPlans.subList(0, limit);
        }

        return ResponseEntity.ok(
                new Recommendation(accounts.getAccount(accountId).getPlanId(), recommendedPlans));
    }
}
