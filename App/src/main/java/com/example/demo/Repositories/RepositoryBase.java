package com.example.demo.Repositories;

import com.example.demo.Models.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RepositoryBase {
    protected final static String URL = "jdbc:mysql://localhost:3306/";
    protected final static String DB_NAME = "owo";
    protected final static String USER = "root";
    protected final static String PASSWORD = "toor";

    protected static UserModel mapUserModel(ResultSet rs) throws SQLException {
        var user = mapUserModel(rs, new UserModel());
        return user;
    }

    protected static UserModel mapUserModel(ResultSet rs, UserModel user) throws SQLException {
        user.setId(rs.getInt("id_utilizator"));
        user.setType(rs.getInt("id_tip_utilizator"));
        user.setTypeName(rs.getString(11));
        user.setTypeRank(rs.getInt(12));
        user.setCnp(rs.getString("cnp"));
        user.setSecondName(rs.getString("nume"));
        user.setFirstName(rs.getString("prenume"));
        user.setAddress(rs.getString("adresa"));
        user.setPhone(rs.getString("telefon"));
        user.setEmail(rs.getString("email"));
        user.setIban(rs.getString("iban"));
        user.setContract(rs.getInt("nr_contract"));

        return user;
    }

    protected static StudentModel mapStudentModel(ResultSet rs) throws SQLException {
        var student = new StudentModel();

        mapUserModel(rs, student);
        student.setYear(rs.getInt("an"));
        student.setClassesNb(rs.getInt("nr_ore"));

        return student;
    }

    protected static ProfesorModel mapProfessorModel(ResultSet rs) throws SQLException {
        var professor = new ProfesorModel();

        mapUserModel(rs, professor);
        professor.setMinHours(rs.getInt("minim_ore"));
        professor.setMaxHours(rs.getInt("maxim_ore"));
        professor.setDepartment(rs.getString("departament"));

        return professor;
    }

    protected static CourseModel mapCourseModel(ResultSet rs) throws SQLException {
        var course = new CourseModel();

        course.setId(rs.getInt("id_materie"));
        course.setName(rs.getString("nume"));
        course.setDescription(rs.getString("descriere"));
        course.setYear(rs.getInt("an"));

        return course;
    }

    protected static GradeModel mapGradeModel(ResultSet rs) throws SQLException {
        var grade = new GradeModel();

        grade.setCourseName(rs.getString("nume"));
        grade.setGrade(rs.getString("nota"));

        if (grade.getGrade() == null || grade.getGrade().isBlank() || grade.getGrade().isEmpty()) {
            grade.setGrade("-");
        }

        return grade;
    }

    protected static ActivityModel mapActivityModel(ResultSet rs) throws SQLException {
        return mapActivityModel(rs, new ActivityModel());
    }

    protected static ActivityModel mapActivityModel(ResultSet rs, ActivityModel activityModel) throws SQLException {
        activityModel.setId(rs.getInt("id_activitate"));
        activityModel.setName(rs.getString("denumire_activitate_didactica"));
        activityModel.setExamName(rs.getString("denumire_evaluare"));
        activityModel.setDaysNb(rs.getInt("interval_zile"));

        return activityModel;
    }

    protected static ProfActivityModel mapProfActivityModel(ResultSet rs) throws SQLException {
        var activity = new ProfActivityModel();

        mapActivityModel(rs, activity);
        activity.setWeight(rs.getInt("procentaj"));
        activity.setTeachingId(rs.getInt("id_conditie"));
        activity.setCourseName(rs.getString("nume"));

        return activity;
    }

    protected static ProfCatalogModel mapProfCatalogModel(ResultSet rs) throws SQLException {
        var model = new ProfCatalogModel();

        model.setProfessorId(rs.getInt("id_profesor"));
        model.setProfessorName(rs.getString("profesor"));
        model.setStudentId(rs.getInt("id_student"));
        model.setStudentName(rs.getString("student"));
        model.setCourseId(rs.getInt("id_materie"));
        model.setCourseName(rs.getString("materie"));
        model.setTeachingId(rs.getInt("id_predare"));
        model.setActivityId(rs.getInt("id_activitate"));
        model.setActivityName(rs.getString("activitate"));
        model.setGrade(rs.getInt("nota"));

        return model;
    }

    protected static CalendarModel mapCalendarModel(ResultSet rs) throws SQLException {
        var model = mapCalendarModel(rs, new CalendarModel());

        return model;
    }

    protected static CalendarModel mapCalendarModel(ResultSet rs, CalendarModel model) throws SQLException {
        model.setId(rs.getInt("id_programare"));
        model.setCondId(rs.getInt("id_conditie"));
        model.setStartDate(rs.getString("data_incepre"));
        model.setEndDate(rs.getString("data_terminare"));
        model.setExam(rs.getBoolean("evaluare"));
        model.setDaysNb(rs.getInt("interval_zile"));
        model.setStudentsNb(rs.getInt("nr_max_studenti"));
        model.setTeachingId(rs.getInt("id_predare"));
        model.setActivityId(rs.getInt("id_activitate"));
        model.setProfessorId(rs.getInt("id_profesor"));
        model.setCondId(rs.getInt("id_materie"));
        model.setCourseName(rs.getString("nume"));
        model.setCourseDescription(rs.getString("descriere"));
        model.setYear(rs.getInt("an"));
        model.setActivityName(rs.getString("denumire_activitate_didactica"));
        model.setExamName(rs.getString("denumire_evaluare"));
        try {
            model.setCurrStudentsNb(rs.getInt("nr_studenti"));
        }
        catch (Exception e) {
            model.setCurrStudentsNb(-1);
        }


        return model;
    }

    protected static StudentCalendarModel mapStudentCalendarModel(ResultSet rs) throws SQLException {
        var model = new StudentCalendarModel();

        mapCalendarModel(rs, model);
        model.setEnrollmentStatus(rs.getString("inscris"));

        return model;
    }

    protected static StudyGroupModel mapStudyGroupModel(ResultSet rs) throws SQLException {
        var model = new StudyGroupModel();

        model.setId(rs.getInt("id_grup_studiu"));
        model.setCourseId(rs.getInt("id_materie"));
        model.setName(rs.getString("nume"));
        model.setCourseName(rs.getString("nume_curs"));

        return model;
    }

    protected static MessageModel mapMessageModel(ResultSet rs) throws SQLException {
        var model = new MessageModel();

        model.setSender(rs.getString("sender"));
        model.setMessage(rs.getString("mesaj"));

        return model;
    }

    protected static InboxModel mapInboxModel(ResultSet rs) throws SQLException {
        var model = new InboxModel();

        model.setId(rs.getInt("id_mesaje"));
        model.setStudentId(rs.getInt("id_student"));
        model.setTitle(rs.getString("titlu"));
        model.setMessage(rs.getString("mesaj"));

        return model;
    }

    protected static EventModel mapEventModel(ResultSet rs) throws SQLException {
        var model = new EventModel();

        model.setId(rs.getInt("id_event"));
        model.setGroupId(rs.getInt("id_grup_studiu"));
        model.setTitle(rs.getString("titlu"));
        model.setDescription(rs.getString("descriere"));
        model.setProfessor(mapUserModel(rs, model.getProfessor()));
        model.setDate(rs.getString("data_event"));
        model.setDuration(rs.getString("durata"));
        model.setMinEntries(rs.getInt("nr_min_participanti"));
        model.setAvailability(rs.getString("valabilitate"));
        model.setAttendance(rs.getBoolean("participa"));

        return model;
    }
}

