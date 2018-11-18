package com.timojo.energymart.datasource;

import com.timojo.energymart.model.OffPeakTimeMultiplier;
import com.timojo.energymart.model.PricePlan;
import one.util.streamex.IntStreamEx;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class PricePlans {
    private List<PricePlan> pricePlanList = new ArrayList<>();

    private static List<Integer> WEEKEND_HOURS = IntStream.range(120, 168).boxed().collect(Collectors.toList());
    private static List<Integer> WEEKDAY_HOURS_1_6 = IntStreamEx.range(1,7)
                                                        .append(IntStream.range(49, 54))
                                                        .append(IntStream.range(73, 78))
                                                        .append(IntStream.range(97, 102))
                                                        .append(IntStream.range(121, 126))
                                                        .boxed().collect(Collectors.toList());
    private static List<Integer> WEEKDAY_HOURS_19_24 = IntStreamEx.range(19,25)
                                                        .append(IntStream.range(43, 49))
                                                        .append(IntStream.range(67, 73))
                                                        .append(IntStream.range(91, 97))
                                                        .append(IntStream.range(115, 121))
                                                        .boxed().collect(Collectors.toList());
    private static List<Integer> WEEKDAY_HOURS_21_24 = IntStreamEx.range(21,25)
                                                        .append(IntStream.range(45, 49))
                                                        .append(IntStream.range(69, 73))
                                                        .append(IntStream.range(93, 97))
                                                        .append(IntStream.range(117, 121))
                                                        .boxed().collect(Collectors.toList());

    public PricePlans() {
        // generate PricePlan for UPS_STD_PLAN
        List<OffPeakTimeMultiplier> upsStdPlanMultipliers = WEEKEND_HOURS
                .stream()
                .map(hr -> new OffPeakTimeMultiplier(hr, BigDecimal.valueOf(.90)))
                .collect(Collectors.toList());
        pricePlanList.add(new PricePlan("UPS",
                "UPS_STD_PLAN",
                BigDecimal.valueOf(0.1002),
                3.8,
                upsStdPlanMultipliers));

        // generate PricePlan for UPS_GREEN_PLAN
        List<OffPeakTimeMultiplier> upsGreenPlanMultipliers = new ArrayList<>();
        upsGreenPlanMultipliers.addAll(WEEKEND_HOURS
                .stream()
                .map(hr -> new OffPeakTimeMultiplier(hr, BigDecimal.valueOf(.85)))
                .collect(Collectors.toList()));
        upsGreenPlanMultipliers.addAll(WEEKDAY_HOURS_1_6
                .stream()
                .map(hr -> new OffPeakTimeMultiplier(hr, BigDecimal.valueOf(.90)))
                .collect(Collectors.toList()));
        upsGreenPlanMultipliers.addAll(WEEKDAY_HOURS_21_24
                .stream()
                .map(hr -> new OffPeakTimeMultiplier(hr, BigDecimal.valueOf(.90)))
                .collect(Collectors.toList()));

        pricePlanList.add(new PricePlan("UPS",
                                        "UPS_GREEN_PLAN",
                                        BigDecimal.valueOf(0.12),
                                        3.9,
                                        upsGreenPlanMultipliers));


        // generate PricePlan for POWERBALL_POWER_PLAN
        List<OffPeakTimeMultiplier> powerballPowerPlanMultipliers = new ArrayList<>();
        powerballPowerPlanMultipliers.addAll(WEEKEND_HOURS
                .stream()
                .map(hr -> new OffPeakTimeMultiplier(hr, BigDecimal.valueOf(.85)))
                .collect(Collectors.toList()));
        powerballPowerPlanMultipliers.addAll(WEEKDAY_HOURS_1_6
                .stream()
                .map(hr -> new OffPeakTimeMultiplier(hr, BigDecimal.valueOf(.90)))
                .collect(Collectors.toList()));
        powerballPowerPlanMultipliers.addAll(WEEKDAY_HOURS_21_24
                .stream()
                .map(hr -> new OffPeakTimeMultiplier(hr, BigDecimal.valueOf(.90)))
                .collect(Collectors.toList()));

        pricePlanList.add(new PricePlan("POWERBALL",
                                        "POWERBALL_POWER_PLAN",
                                        BigDecimal.valueOf(0.14),
                                        3.5,
                                        powerballPowerPlanMultipliers));


        // generate PricePlan for GREENCITY_ECO_PLAN
        List<OffPeakTimeMultiplier> greenCityEcoPlanMultipliers = new ArrayList<>();
        greenCityEcoPlanMultipliers.addAll(WEEKEND_HOURS
                .stream()
                .map(hr -> new OffPeakTimeMultiplier(hr, BigDecimal.valueOf(.88)))
                .collect(Collectors.toList()));
        greenCityEcoPlanMultipliers.addAll(WEEKDAY_HOURS_1_6
                .stream()
                .map(hr -> new OffPeakTimeMultiplier(hr, BigDecimal.valueOf(.88)))
                .collect(Collectors.toList()));
        greenCityEcoPlanMultipliers.addAll(WEEKDAY_HOURS_21_24
                .stream()
                .map(hr -> new OffPeakTimeMultiplier(hr, BigDecimal.valueOf(.88)))
                .collect(Collectors.toList()));

        pricePlanList.add(new PricePlan("GREENCITY",
                                        "GREENCITY_ECO_PLAN",
                                        BigDecimal.valueOf(0.15),
                                        4.3,
                                        greenCityEcoPlanMultipliers));


        // generate PricePlan for GREENCITY_STAR_PLAN
        List<OffPeakTimeMultiplier> greenCityStarPlanMultipliers = new ArrayList<>();
        greenCityStarPlanMultipliers.addAll(WEEKEND_HOURS
                .stream()
                .map(hr -> new OffPeakTimeMultiplier(hr, BigDecimal.valueOf(.88)))
                .collect(Collectors.toList()));
        greenCityStarPlanMultipliers.addAll(WEEKDAY_HOURS_1_6
                .stream()
                .map(hr -> new OffPeakTimeMultiplier(hr, BigDecimal.valueOf(.88)))
                .collect(Collectors.toList()));
        greenCityStarPlanMultipliers.addAll(WEEKDAY_HOURS_19_24
                .stream()
                .map(hr -> new OffPeakTimeMultiplier(hr, BigDecimal.valueOf(.88)))
                .collect(Collectors.toList()));

        pricePlanList.add(new PricePlan("GREENCITY",
                                        "GREENCITY_STAR_PLAN",
                                        BigDecimal.valueOf(0.17),
                                        4.14,
                                        greenCityStarPlanMultipliers));

        // generate PricePlan for GREENCITY_STAR_PLAN
        List<OffPeakTimeMultiplier> qEnergyPlanMultipliers = new ArrayList<>();
        pricePlanList.add(new PricePlan("Q_ENERGY",
                                        "Q_ENERGY_PLAN",
                                        BigDecimal.valueOf(0.0952),
                                        3.5,
                                        qEnergyPlanMultipliers));
    }

    public List<PricePlan> getPricePlanList() {
        return pricePlanList;
    }
}
