package com.example.demo.Repositories;

import com.example.demo.Models.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentRepository extends RepositoryBase {
    public static List<StudentCalendarModel> getStudentCalendar(String studentId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_calendar(?)");
            cs.setString(1, studentId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<StudentCalendarModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapStudentCalendarModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<StudentCalendarModel> getStudentCalendarCurrent(String studentId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_calendar_curent(?)");
            cs.setString(1, studentId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<StudentCalendarModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapStudentCalendarModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<StudentCalendarModel> getStudentCalendarCollisions(String studentId, String pId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_calendar_check_collision(?, ?)");
            cs.setString(1, studentId);
            cs.setString(2, pId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<StudentCalendarModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapStudentCalendarModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static CalendarModel getCalendarModelById(String pId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("select * from vw_calendar_viitor where id_programare = ?");
            cs.setString(1, pId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            CalendarModel model = null;
            while (rs.next()) {
                model = mapCalendarModel(rs);
            }

            return model;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean studentEnrollActivity(String studentId, String pId) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            CallableStatement cs = conn.prepareCall("call student_enroll_activity(?, ?)");
            cs.setString(1, studentId);
            cs.setString(2, pId);

            cs.executeUpdate();

            conn.commit();
            cs.close();
            conn.close();
            return true;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

            System.out.println(e.getMessage());
            return false;
        }
    }

    public static List<StudyGroupModel> getStudentJoinedGroups(String studentId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_my_study_groups(?)");
            cs.setString(1, studentId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<StudyGroupModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapStudyGroupModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<StudyGroupModel> getStudentNotJoinedGroups(String studentId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_not_my_study_groups(?)");
            cs.setString(1, studentId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<StudyGroupModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapStudyGroupModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<StudentModel> getGroupMembers(String studentId, String groupId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_group_members(?, ?)");
            cs.setString(1, studentId);
            cs.setString(2, groupId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<StudentModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapStudentModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<StudentModel> getGroupSuggestions(String studentId, String groupId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_group_suggestions(?, ?)");
            cs.setString(1, studentId);
            cs.setString(2, groupId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<StudentModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapStudentModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<MessageModel> getGroupMessages(String studentId, String groupId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_group_messages(?, ?)");
            cs.setString(1, studentId);
            cs.setString(2, groupId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<MessageModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapMessageModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static boolean studentAddMessage(String studentId, String groupId, String msg) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            CallableStatement cs = conn.prepareCall("call student_group_add_msg(?, ?, ?)");
            cs.setString(1, studentId);
            cs.setString(2, groupId);
            cs.setString(3, msg);

            cs.executeUpdate();

            conn.commit();
            cs.close();
            conn.close();
            return true;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

            System.out.println(e.getMessage());
            return false;
        }
    }

    public static List<InboxModel> studentGetInbox(String studentId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_inbox(?)");
            cs.setString(1, studentId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<InboxModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapInboxModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static boolean studentJoinGroup(String studentId, String groupId) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            CallableStatement cs = conn.prepareCall("call student_join_group(?, ?)");
            cs.setString(1, studentId);
            cs.setString(2, groupId);

            cs.executeUpdate();

            conn.commit();
            cs.close();
            conn.close();
            return true;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean studentExitGroup(String studentId, String groupId) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            CallableStatement cs = conn.prepareCall("call student_exit_group(?, ?)");
            cs.setString(1, studentId);
            cs.setString(2, groupId);

            cs.executeUpdate();

            conn.commit();
            cs.close();
            conn.close();
            return true;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean studentCreateGroup(String studentId, GroupFormModel groupFormModel) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            CallableStatement cs = conn.prepareCall("call student_create_group(?, ?, ?)");
            cs.setString(1, studentId);
            cs.setInt(2, groupFormModel.getId());
            cs.setString(3, groupFormModel.getName());

            cs.executeUpdate();

            conn.commit();
            cs.close();
            conn.close();
            return true;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean studentUpdateGroup(String studentId, String groupId, GroupFormModel groupFormModel) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            CallableStatement cs = conn.prepareCall("update grupuri_de_studiu set id_materie = ?, nume = ? where id_grup_studiu = ?");
            cs.setString(3, groupId);
            cs.setInt(1, groupFormModel.getId());
            cs.setString(2, groupFormModel.getName());

            System.out.println(cs);

            cs.executeUpdate();

            conn.commit();
            cs.close();
            conn.close();
            return true;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean studentDeleteGroup(String studentId, String groupId) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            CallableStatement cs = conn.prepareCall("call student_delete_group(?, ?)");
            cs.setString(1, studentId);
            cs.setString(2, groupId);

            cs.executeUpdate();

            conn.commit();
            cs.close();
            conn.close();
            return true;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

            System.out.println(e.getMessage());
            return false;
        }
    }

    public static List<EventModel> getGroupEvents(String studentId, String groupId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_get_group_events(?, ?)");
            cs.setString(1, studentId);
            cs.setString(2, groupId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<EventModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapEventModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<UserModel> getGroupProfessors(String studentId, String groupId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_get_group_professors(?, ?)");
            cs.setString(1, studentId);
            cs.setString(2, groupId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<UserModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapUserModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static boolean studentCreateEvent(String studentId, String groupId, EventFormModel eventFormModel) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            CallableStatement cs = conn.prepareCall("call student_create_event(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            cs.setString(1, studentId);
            cs.setString(2, groupId);
            cs.setString(3, eventFormModel.getTitle());
            cs.setString(4, eventFormModel.getDescription());
            cs.setInt(5, eventFormModel.getProfessor());
            cs.setString(6, eventFormModel.getDate());
            cs.setString(7, eventFormModel.getDuration());
            cs.setString(8, eventFormModel.getMinEntries());
            cs.setString(9, eventFormModel.getAvailability());

            cs.executeUpdate();

            conn.commit();
            cs.close();
            conn.close();
            return true;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean studentJoinEvent(String studentId, String groupId, String eventId) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            CallableStatement cs = conn.prepareCall("call student_join_event(?, ?, ?)");
            cs.setString(1, studentId);
            cs.setString(2, groupId);
            cs.setString(3, eventId);

            cs.executeUpdate();

            conn.commit();
            cs.close();
            conn.close();
            return true;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

            System.out.println(e.getMessage());
            return false;
        }
    }

    public static List<CalendarModel> getActivitiesOnCourse(String studentId, String courseId, String type) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call getActivities(?,?,?)");
            cs.setString(1, studentId);
            cs.setString(2, courseId);
            cs.setString(3, type);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<CalendarModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapCalendarModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<CalendarModel> getStudentActivities(String studentId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call getStudentActivities(?)");
            cs.setString(1, studentId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<CalendarModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapCalendarModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }

    }

    public static void getAllCombinations(List<CourseActivityModel> courseList, List<CalendarModel> timetable, int start, int end, int size, StudentTimetableModel studentTimetable) throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        if (timetable.size() == size && !studentTimetable.isFound()) {

            studentTimetable.setTimetable(timetable);
            studentTimetable.setFound(true);

            return;

        }

        for (int i = start; i <= end && !studentTimetable.isFound(); i++) {
            if (!courseList.get(i).getCourses().isEmpty() && courseList.get(i).getCourses() != null && !courseList.get(i).isUsedCourses()) {
                for (CalendarModel toBeEnrolled : courseList.get(i).getCourses()) {
                    int ok = 1;
                    for (CalendarModel alreadyEnrolled : timetable) {

                        Calendar toBeEnrolledStart = Calendar.getInstance();
                        Calendar toBeEnrolledEnd = Calendar.getInstance();
                        Calendar alreadyEnrolledStart = Calendar.getInstance();
                        Calendar alreadyEnrolledEnd = Calendar.getInstance();

                        toBeEnrolledStart.setTime(formatter.parse(toBeEnrolled.getStartDate()));
                        toBeEnrolledEnd.setTime(formatter.parse(toBeEnrolled.getEndDate()));
                        alreadyEnrolledStart.setTime(formatter.parse(alreadyEnrolled.getStartDate()));
                        alreadyEnrolledEnd.setTime(formatter.parse(alreadyEnrolled.getEndDate()));

                        if (!(toBeEnrolled.getStudentsNb() > toBeEnrolled.getCurrStudentsNb()))
                            ok = 0;
                        if (toBeEnrolledStart.get(Calendar.DAY_OF_WEEK) == alreadyEnrolledStart.get(Calendar.DAY_OF_WEEK)) {
                            if (!(toBeEnrolledStart.get(Calendar.HOUR_OF_DAY) >= (alreadyEnrolledEnd).get(Calendar.HOUR_OF_DAY)) && !(toBeEnrolledEnd.get(Calendar.HOUR_OF_DAY) <= alreadyEnrolledStart.get(Calendar.HOUR_OF_DAY)))
                                ok = 0;
                        }
                    }

                    if (ok == 1 || timetable.isEmpty()) {
                        courseList.get(i).setUsedCourses(true);
                        timetable.add(toBeEnrolled);
                        if (courseList.get(i).isUsedCourses() && courseList.get(i).isUsedLaboratories() && courseList.get(i).isUsedSeminaries()) {
                            StudentRepository.getAllCombinations(courseList, timetable, start + 1, end, size, studentTimetable);
                            courseList.get(i).setUsedCourses(false);
                            timetable.remove(timetable.size() - 1);
                        } else {
                            StudentRepository.getAllCombinations(courseList, timetable, start, end, size, studentTimetable);
                            courseList.get(i).setUsedCourses(false);
                            timetable.remove(timetable.size() - 1);
                        }

                    }

                }
            } else if (!courseList.get(i).getLaboratories().isEmpty() && courseList.get(i).getLaboratories() != null && !courseList.get(i).isUsedLaboratories()) {
                for (CalendarModel toBeEnrolled : courseList.get(i).getLaboratories()) {
                    int ok = 1;
                    for (CalendarModel alreadyEnrolled : timetable) {

                        Calendar toBeEnrolledStart = Calendar.getInstance();
                        ;
                        Calendar toBeEnrolledEnd = Calendar.getInstance();
                        ;
                        Calendar alreadyEnrolledStart = Calendar.getInstance();
                        ;
                        Calendar alreadyEnrolledEnd = Calendar.getInstance();
                        ;

                        toBeEnrolledStart.setTime(formatter.parse(toBeEnrolled.getStartDate()));
                        toBeEnrolledEnd.setTime(formatter.parse(toBeEnrolled.getEndDate()));
                        alreadyEnrolledStart.setTime(formatter.parse(alreadyEnrolled.getStartDate()));
                        alreadyEnrolledEnd.setTime(formatter.parse(alreadyEnrolled.getEndDate()));

                        if (!(toBeEnrolled.getStudentsNb() > toBeEnrolled.getCurrStudentsNb()))
                            ok = 0;

                        if (toBeEnrolledStart.get(Calendar.DAY_OF_WEEK) == alreadyEnrolledStart.get(Calendar.DAY_OF_WEEK)) {
                            if (!(toBeEnrolledStart.get(Calendar.HOUR_OF_DAY) >= (alreadyEnrolledEnd).get(Calendar.HOUR_OF_DAY)) && !(toBeEnrolledEnd.get(Calendar.HOUR_OF_DAY) <= alreadyEnrolledStart.get(Calendar.HOUR_OF_DAY)))
                                ok = 0;
                        }
                    }
                    if (ok == 1 || timetable.isEmpty()) {

                        courseList.get(i).setUsedLaboratories(true);
                        timetable.add(toBeEnrolled);


                        if (courseList.get(i).isUsedCourses() && courseList.get(i).isUsedLaboratories() && courseList.get(i).isUsedSeminaries()) {

                            StudentRepository.getAllCombinations(courseList, timetable, start + 1, end, size, studentTimetable);
                            courseList.get(i).setUsedLaboratories(false);
                            timetable.remove(timetable.size() - 1);
                        } else {

                            StudentRepository.getAllCombinations(courseList, timetable, start, end, size, studentTimetable);
                            courseList.get(i).setUsedLaboratories(false);
                            timetable.remove(timetable.size() - 1);
                        }

                    }

                }
            } else if (!courseList.get(i).getSeminaries().isEmpty() && courseList.get(i).getSeminaries() != null && !courseList.get(i).isUsedSeminaries()) {

                for (CalendarModel toBeEnrolled : courseList.get(i).getSeminaries()) {
                    int ok = 1;
                    for (CalendarModel alreadyEnrolled : timetable) {

                        Calendar toBeEnrolledStart = Calendar.getInstance();
                        ;
                        Calendar toBeEnrolledEnd = Calendar.getInstance();
                        ;
                        Calendar alreadyEnrolledStart = Calendar.getInstance();
                        ;
                        Calendar alreadyEnrolledEnd = Calendar.getInstance();
                        ;

                        toBeEnrolledStart.setTime(formatter.parse(toBeEnrolled.getStartDate()));
                        toBeEnrolledEnd.setTime(formatter.parse(toBeEnrolled.getEndDate()));
                        alreadyEnrolledStart.setTime(formatter.parse(alreadyEnrolled.getStartDate()));
                        alreadyEnrolledEnd.setTime(formatter.parse(alreadyEnrolled.getEndDate()));

                        if (!(toBeEnrolled.getStudentsNb() > toBeEnrolled.getCurrStudentsNb()))
                            ok = 0;

                        if (toBeEnrolledStart.get(Calendar.DAY_OF_WEEK) == alreadyEnrolledStart.get(Calendar.DAY_OF_WEEK)) {
                            if (!(toBeEnrolledStart.get(Calendar.HOUR_OF_DAY) >= (alreadyEnrolledEnd).get(Calendar.HOUR_OF_DAY)) && !(toBeEnrolledEnd.get(Calendar.HOUR_OF_DAY) <= alreadyEnrolledStart.get(Calendar.HOUR_OF_DAY)))
                                ok = 0;
                        }
                    }


                    if (ok == 1 || timetable.isEmpty()) {
                        courseList.get(i).setUsedSeminaries(true);
                        timetable.add(toBeEnrolled);
                        if (courseList.get(i).isUsedCourses() && courseList.get(i).isUsedLaboratories() && courseList.get(i).isUsedSeminaries()) {
                            StudentRepository.getAllCombinations(courseList, timetable, start + 1, end, size, studentTimetable);
                            courseList.get(i).setUsedSeminaries(false);
                            timetable.remove(timetable.size() - 1);
                        } else {
                            StudentRepository.getAllCombinations(courseList, timetable, start, end, size, studentTimetable);
                            courseList.get(i).setUsedSeminaries(false);
                            timetable.remove(timetable.size() - 1);
                        }

                    }

                }
            } else
                StudentRepository.getAllCombinations(courseList, timetable, start + 1, end, size, studentTimetable);

        }


    }

    public static List<CourseModel> getJoinableCourses(String id) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_joinable_courses(?)");
            cs.setString(1, id);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<CourseModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapCourseModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<CourseModel> getSelectedCourses(String id) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_selected_courses(?)");
            cs.setString(1, id);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<CourseModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapCourseModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static boolean joinCourse(String userId, String courseId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_join_cours(?, ?)");
            cs.setString(1, userId);
            cs.setString(2, courseId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean dropCourse(String userId, String courseId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_drop_cours(?, ?)");
            cs.setString(1, userId);
            cs.setString(2, courseId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static List<GradeModel> getGrades(String userId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call student_get_note(?)");
            cs.setString(1, userId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<GradeModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapGradeModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static StudentTimetableModel getActivityProgram(String studentId) {
        int nbOfActivities = 0;

        List<CourseModel> courseList = getSelectedCourses(studentId);
        List<CourseActivityModel> courseActivityList = new ArrayList<>();

        for (CourseModel courseTmp : courseList) {

            List<CalendarModel> laboratories = getActivitiesOnCourse(studentId, courseTmp.getId().toString(), "laborator");
            List<CalendarModel> courses = getActivitiesOnCourse(studentId, courseTmp.getId().toString(), "curs");
            List<CalendarModel> seminaries = getActivitiesOnCourse(studentId, courseTmp.getId().toString(), "seminar");

            CourseActivityModel courseActivityTmp = new CourseActivityModel(courseTmp, courses, laboratories, seminaries);
            courseActivityList.add(courseActivityTmp);
        }

        List<CalendarModel> timetable = getStudentActivities(studentId);

        timetable.forEach(programare -> {
            courseActivityList.forEach(conditie -> {
                var curs = conditie.getCourses().stream().filter(x -> x.getId() == programare.getId()).findFirst().orElse(null);
                var lab = conditie.getLaboratories().stream().filter(x -> x.getId() == programare.getId()).findFirst().orElse(null);
                var seminar = conditie.getSeminaries().stream().filter(x -> x.getId() == programare.getId()).findFirst().orElse(null);

                if (curs != null) {
                    conditie.setUsedCourses(true);
                }

                if (lab != null) {
                    conditie.setUsedLaboratories(true);
                }

                if (seminar != null) {
                    conditie.setUsedSeminaries(true);
                }
            });
        });

        for (CourseActivityModel ca : courseActivityList)
            System.out.println(ca);

        for (CourseActivityModel courseActivityTmp : courseActivityList) {
            if (!courseActivityTmp.getCourses().isEmpty() && courseActivityTmp.getCourses() != null)
                nbOfActivities++;
            if (!courseActivityTmp.getLaboratories().isEmpty() && courseActivityTmp.getLaboratories() != null)
                nbOfActivities++;
            if (!courseActivityTmp.getSeminaries().isEmpty() && courseActivityTmp.getSeminaries() != null)
                nbOfActivities++;
        }

        StudentTimetableModel studentTimetable = new StudentTimetableModel();


        try {
            StudentRepository.getAllCombinations(courseActivityList, timetable, 0, courseActivityList.size() - 1, nbOfActivities, studentTimetable);
        } catch (Exception e) {
            System.out.println("EXCEPTIE BOIZ");
        }

        return studentTimetable;
    }

    public static List<EventModel> getStudentEvents(String studentId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call getStudentGroupActivities(?)");
            cs.setString(1, studentId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<EventModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapEventModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<EventModel> getWindows(String studentId, List<CalendarModel> timetable){

        DateTimeFormatter datetime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");

        List<EventModel> eventList = StudentRepository.getStudentEvents(studentId);
        List<EventModel> finalEventList = new ArrayList<>();


        eventList.forEach(eveniment -> {
            AtomicInteger ok = new AtomicInteger();
            ok.set(1);
            System.out.println(ok.get());
            timetable.forEach(programare -> {
                try {

                    LocalDateTime evenimentStart = LocalDateTime.parse(eveniment.getDate(), datetime);
                    LocalDateTime evenimentEnd = evenimentStart.plusHours(LocalTime.parse(eveniment.getDuration(), time).getHour());
                    LocalDateTime programareStart = LocalDateTime.parse(programare.getStartDate(), datetime);
                    LocalDateTime programareEnd = LocalDateTime.parse(programare.getEndDate(), datetime);
                    System.out.println(evenimentStart+ " : "+ evenimentEnd);

                    if (evenimentStart.getDayOfWeek() == programareStart.getDayOfWeek())
                    {

                        if(!(evenimentStart.getHour() >= programareEnd.getHour()) && !(evenimentEnd.getHour() <= programareStart.getHour()))
                        {
                            System.out.println("uwu");
                            ok.set(0);
                        }
                    }
                }

                catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            });
            System.out.println(ok.get());
            if(ok.get()==1)
                finalEventList.add(eveniment);

        });
        return finalEventList;
    }
}
