package com.example.demo.Models;

public class StudentModel extends UserModel {
    private int year;
    private int classesNb;

    public StudentModel() {
    }

    public StudentModel(int year, int classesNb) {
        this.year = year;
        this.classesNb = classesNb;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getClassesNb() {
        return classesNb;
    }

    public void setClassesNb(int classesNb) {
        this.classesNb = classesNb;
    }
}
