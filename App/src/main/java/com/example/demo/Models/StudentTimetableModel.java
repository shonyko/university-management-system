package com.example.demo.Models;

import java.util.ArrayList;
import java.util.List;

public class StudentTimetableModel {

    private List<CalendarModel> timetable;
    private boolean isFound;

    public StudentTimetableModel() {
        this.timetable=new ArrayList<>();
        this.isFound=false;
    }

    public List<CalendarModel> getTimetable() {
        return timetable;
    }

    @Override
    public String toString() {
        return "StudentTimetableModel{" +
                "timetable=" + timetable +
                ", isFound=" + isFound +
                '}';
    }

    public void setTimetable(List<CalendarModel> timetable) {
        for(CalendarModel calendarTmp : timetable)
            this.timetable.add(calendarTmp);
    }

    public boolean isFound() {
        return isFound;
    }

    public void setFound(boolean found) {
        isFound = found;
    }
}