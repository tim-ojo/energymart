package com.timojo.energymart.model;

import java.time.ZonedDateTime;
import java.util.Objects;

public class UserAccount {
    private Integer id;
    private String meterId;
    private String planId;
    private ZonedDateTime userJoinDate;
    private String accountType;
    private String accountStatus;

    public UserAccount(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public ZonedDateTime getUserJoinDate() {
        return userJoinDate;
    }

    public void setUserJoinDate(ZonedDateTime userJoinDate) {
        this.userJoinDate = userJoinDate;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(meterId, that.meterId) &&
                Objects.equals(planId, that.planId) &&
                Objects.equals(userJoinDate, that.userJoinDate) &&
                Objects.equals(accountType, that.accountType) &&
                Objects.equals(accountStatus, that.accountStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meterId, planId, userJoinDate, accountType, accountStatus);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", meterId='" + meterId + '\'' +
                ", planId='" + planId + '\'' +
                ", userJoinDate=" + userJoinDate +
                ", accountType='" + accountType + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                '}';
    }
}
