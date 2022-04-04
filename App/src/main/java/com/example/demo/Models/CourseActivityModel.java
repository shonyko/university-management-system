package com.example.demo.Models;

import java.util.List;

public class CourseActivityModel {

    private CourseModel course;
    private List<CalendarModel> courses;
    private List<CalendarModel> laboratories;
    private List<CalendarModel> seminaries;
    private boolean isUsedCourses;
    private boolean isUsedLaboratories;
    private boolean isUsedSeminaries;

    public CourseActivityModel(CourseModel course, List<CalendarModel> courses, List<CalendarModel> laboratories, List<CalendarModel> seminaries) {
        this.course = course;
        this.courses = courses;
        this.laboratories = laboratories;
        this.seminaries = seminaries;
        
        if (courses.isEmpty())
            this.isUsedCourses = true;
        else
            this.isUsedCourses = false;

        if (courses.isEmpty())
            this.isUsedLaboratories = true;
        else
            this.isUsedLaboratories = false;

        if (courses.isEmpty())
            this.isUsedSeminaries = true;
        else
            this.isUsedSeminaries = false;

    }

    public CourseModel getCourse() {
        return course;
    }

    public void setCourse(CourseModel course) {
        this.course = course;
    }

    public List<CalendarModel> getCourses() {
        return courses;
    }

    public void setCourses(List<CalendarModel> courses) {
        this.courses = courses;
    }

    public List<CalendarModel> getLaboratories() {
        return laboratories;
    }

    public void setLaboratories(List<CalendarModel> laboratories) {
        this.laboratories = laboratories;
    }

    public List<CalendarModel> getSeminaries() {
        return seminaries;
    }

    public void setSeminaries(List<CalendarModel> seminaries) {
        this.seminaries = seminaries;
    }

    public boolean isUsedCourses() {
        return isUsedCourses;
    }

    public void setUsedCourses(boolean usedCourses) {
        isUsedCourses = usedCourses;
    }

    public boolean isUsedLaboratories() {
        return isUsedLaboratories;
    }

    public void setUsedLaboratories(boolean usedLaboratories) {
        isUsedLaboratories = usedLaboratories;
    }

    public boolean isUsedSeminaries() {
        return isUsedSeminaries;
    }

    public void setUsedSeminaries(boolean usedSeminaries) {
        isUsedSeminaries = usedSeminaries;
    }

    @Override
    public String toString() {
        return "CourseActivityModel{" +
                ", isUsedCourses=" + isUsedCourses +
                ", isUsedLaboratories=" + isUsedLaboratories +
                ", isUsedSeminaries=" + isUsedSeminaries +
                '}';
    }
}