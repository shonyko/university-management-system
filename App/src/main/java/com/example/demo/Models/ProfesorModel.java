package com.example.demo.Models;

public class ProfesorModel extends UserModel {
    private int minHours;
    private int maxHours;
    private String department;

    public ProfesorModel() {
    }

    public ProfesorModel(int minHours, int maxHours, String department) {
        this.minHours = minHours;
        this.maxHours = maxHours;
        this.department = department;
    }

    public Integer getMinHours() {
        return minHours;
    }

    public void setMinHours(int minHours) {
        this.minHours = minHours;
    }

    public Integer getMaxHours() {
        return maxHours;
    }

    public void setMaxHours(int maxHours) {
        this.maxHours = maxHours;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
