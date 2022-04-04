package com.example.demo.Models;

public class EventModel {
    private Integer id;
    private Integer groupId;
    private String title;
    private String description;
    private UserModel professor;
    private String date;
    private String duration;
    private Integer minEntries;
    private String availability;
    private boolean attendance;

    public EventModel() {
        professor = new UserModel();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserModel getProfessor() {
        return professor;
    }

    public void setProfessor(UserModel professor) {
        this.professor = professor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getMinEntries() {
        return minEntries;
    }

    public void setMinEntries(Integer minEntries) {
        this.minEntries = minEntries;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public boolean isAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }
}
