package com.example.demo.Models;

public class ActivityModel {
    private Integer id;
    private String name;
    private String examName;
    private Integer daysNb;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Integer getDaysNb() {
        return daysNb;
    }

    public void setDaysNb(Integer daysNb) {
        this.daysNb = daysNb;
    }
}
