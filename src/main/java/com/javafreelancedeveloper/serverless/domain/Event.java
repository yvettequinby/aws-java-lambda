package com.javafreelancedeveloper.serverless.domain;


import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.io.Serializable;


@DynamoDBTable(tableName = "EVENT")
public class Event implements Serializable {

    private static final long serialVersionUID = -8243145429438016232L;
    public static final String CITY_INDEX = "city-index";
    public static final String AWAY_TEAM_INDEX = "awayTeam-index";

    @DynamoDBAttribute
    private Long eventId;

    @DynamoDBRangeKey
    private Long eventDate;

    @DynamoDBAttribute
    private String sport;

    @DynamoDBHashKey
    private String homeTeam;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = AWAY_TEAM_INDEX)
    private String awayTeam;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = CITY_INDEX)
    private String city;

    @DynamoDBAttribute
    private String country;

    public Event() { }

    public Event(String team, Long date) {
        this.homeTeam = team;
        this.eventDate = date;
    }

    public Event(Long eventId, Long eventDate, String sport, String homeTeam, String awayTeam, String city, String country) {
        this.eventId = eventId;
        this.eventDate = eventDate;
        this.sport = sport;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.city = city;
        this.country = country;
    }


    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventDate() {
        return eventDate;
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
