package com.timojo.energymart.model;

import java.util.ArrayList;
import java.util.List;

public class Recommendation {
    private String currentPlan;
    private List<RecommendedPlan> recommendedPlans;

    public Recommendation(String currentPlan, List<RecommendedPlan> recommendedPlans) {
        this.currentPlan = currentPlan;
        this.recommendedPlans = recommendedPlans;
    }

    public String getCurrentPlan() {
        return currentPlan;
    }

    public List<RecommendedPlan> getRecommendedPlans() {
        return recommendedPlans;
    }
}
