package com.thewolftechnologies.leaderboard.models;

public class LearningLeadersModel {

    String name;
    String hoursCountry;
    String badgeUrl;

    public LearningLeadersModel() {
    }

    public LearningLeadersModel(String name, String hoursCountry, String badgeUrl) {
        this.name = name;
        this.hoursCountry = hoursCountry;
        this.badgeUrl = badgeUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHoursCountry() {
        return hoursCountry;
    }

    public void setHoursCountry(String hoursCountry) {
        this.hoursCountry = hoursCountry;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeUrl = badgeUrl;
    }
}
