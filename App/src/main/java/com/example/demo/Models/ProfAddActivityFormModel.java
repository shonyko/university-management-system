package com.example.demo.Models;

public class ProfAddActivityFormModel {
    //id_conditie
    private String activity;
    private String studentsNb;
    private String isExam;
    private String startDate;
    private String endDate;

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getStudentsNb() {
        return studentsNb;
    }

    public void setStudentsNb(String studentsNb) {
        this.studentsNb = studentsNb;
    }

    public String getIsExam() {
        return isExam;
    }

    public void setIsExam(String isExam) {
        this.isExam = isExam;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
