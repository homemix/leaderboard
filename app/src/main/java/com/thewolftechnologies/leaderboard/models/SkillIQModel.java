package com.thewolftechnologies.leaderboard.models;

public class SkillIQModel {
    String nameIQ;
    String IQCountry;
    String badgeUrlIQ;

    public SkillIQModel() {
    }

    public SkillIQModel(String name, String IQCountry, String badgeUrl) {
        this.nameIQ = name;
        this.IQCountry = IQCountry;
        this.badgeUrlIQ = badgeUrl;
    }

    public String getNameIQ() {
        return nameIQ;
    }

    public void setNameIQ(String nameIQ) {
        this.nameIQ = nameIQ;
    }

    public String getIQCountry() {
        return IQCountry;
    }

    public void setIQCountry(String IQCountry) {
        this.IQCountry = IQCountry;
    }

    public String getBadgeUrlIQ() {
        return badgeUrlIQ;
    }

    public void setBadgeUrlIQ(String badgeUrlIQ) {
        this.badgeUrlIQ = badgeUrlIQ;
    }
}
