package com.eventmanagement.model;

import java.sql.Date;
import java.sql.Time;

public class Event {
    private int eventId;
    private String title;
    private String description;
    private Date date;
    private Time time;
    private String location;
    private int organizerId;

    public Event() {}
    
    public Event(int eventId, String title, String description, Date date, Time time, String location, int organizerId) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.location = location;
        this.organizerId = organizerId;
    }

    // Getters and Setters
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public Time getTime() { return time; }
    public void setTime(Time time) { this.time = time; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getOrganizerId() { return organizerId; }
    public void setOrganizerId(int organizerId) { this.organizerId = organizerId; }
}