package com.timojo.energymart.datasource;

import com.timojo.energymart.model.PricePlan;
import com.timojo.energymart.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Accounts {
    private Map<Integer, UserAccount> userAccountsMap;

    private final PricePlans pricePlans;

    @Autowired
    public Accounts(PricePlans pricePlans) {
        this.pricePlans = pricePlans;
    }

    @PostConstruct
    public void init() {
        this.userAccountsMap = generateAccounts();
    }

    private Map<Integer, UserAccount> generateAccounts() {
        int numAccountsToGenerate = 100_000;
        Map<Integer, UserAccount> userAccountMap = new HashMap<>(numAccountsToGenerate);

        Random random = new Random();
        List<String> planIds = pricePlans.getPricePlanList().stream().map(PricePlan::getPlanName).collect(Collectors.toList());

        for (int id = 0; id < numAccountsToGenerate; id++) {
            UserAccount userAccount = new UserAccount(id);
            userAccount.setMeterId("meter-" + id);
            userAccount.setPlanId(planIds.get(random.nextInt(planIds.size())));
            userAccount.setAccountType("BASIC");
            userAccount.setUserJoinDate(ZonedDateTime.now(ZoneId.of("UTC")));
            userAccount.setAccountStatus("ACTIVE");

            userAccountMap.put(id, userAccount);
        }

        return userAccountMap;
    }

    public UserAccount getAccount(int accountId) {
        return userAccountsMap.get(accountId);
    }

    public Optional<UserAccount> getAccountByMeterId(String meterId) {
        return userAccountsMap.values().stream().filter(userAccount -> userAccount.getMeterId().equals(meterId)).findFirst();
    }

    Map<Integer, UserAccount> getUserAccountsMap() {
        return userAccountsMap;
    }
}
