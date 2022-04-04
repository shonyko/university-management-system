package com.example.demo.Models;

public class CalendarModel {
    private Integer id;
    private Integer condId;
    private String startDate;
    private String endDate;
    private Boolean isExam;
    private Integer daysNb;
    private Integer studentsNb;
    private Integer teachingId;
    private Integer activityId;
    private Integer professorId;
    private Integer courseId;
    private String courseName;
    private String courseDescription;
    private Integer year;
    private String activityName;
    private String examName;
    private Integer currStudentsNb;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCondId() {
        return condId;
    }

    public void setCondId(Integer condId) {
        this.condId = condId;
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

    public Boolean getExam() {
        return isExam;
    }

    public void setExam(Boolean exam) {
        isExam = exam;
    }

    public Integer getDaysNb() {
        return daysNb;
    }

    public void setDaysNb(Integer daysNb) {
        this.daysNb = daysNb;
    }

    public Integer getStudentsNb() {
        return studentsNb;
    }

    public void setStudentsNb(Integer studentsNb) {
        this.studentsNb = studentsNb;
    }

    public Integer getTeachingId() {
        return teachingId;
    }

    public void setTeachingId(Integer teachingId) {
        this.teachingId = teachingId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Integer professorId) {
        this.professorId = professorId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Integer getCurrStudentsNb() {
        return currStudentsNb;
    }

    public void setCurrStudentsNb(Integer currStudentsNb) {
        this.currStudentsNb = currStudentsNb;
    }
}
