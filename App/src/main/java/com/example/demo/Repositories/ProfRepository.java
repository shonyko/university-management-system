package com.example.demo.Repositories;

import com.example.demo.Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfRepository extends RepositoryBase {
    public static List<CourseModel> getProfesorAssignedCourses(String profId) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            CallableStatement cs = conn.prepareCall("call profesor_assigned_courses(?)");
            cs.setString(1, profId);

            ResultSet rs = cs.executeQuery();

            List<CourseModel> l = new ArrayList<>();

            while (rs.next()) {
                l.add(mapCourseModel(rs));
            }

            conn.commit();
            cs.close();
            conn.close();
            return l;
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
            return Collections.emptyList();
        }
    }

    public static List<ProfActivityModel> getProfessorActivities(String profId, String courseId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call profesor_activities(?, ?)");
            cs.setString(1, profId);
            cs.setString(2, courseId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<ProfActivityModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapProfActivityModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static boolean updateProfessorWeights(String profId, ProfActivityFormModel profActivityFormModel) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            CallableStatement cs = conn.prepareCall("select id_predare from predare where id_profesor = ? and id_materie = ?");
            cs.setString(1, profId);
            cs.setString(2, profActivityFormModel.getCourse());

            ResultSet rs = cs.executeQuery();

            int pId = -1;
            if (rs.next()) {
                pId = rs.getInt(1);
            }

            if (pId != -1) {
                String sql = "update conditie set procentaj = ? where id_predare = ? and id_activitate = ?";

                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(2, pId);
                for (int i = 0; i < profActivityFormModel.getWeight().size(); i++) {
                    stmt.setInt(1, Integer.parseInt(profActivityFormModel.getWeight().get(i)));
                    stmt.setInt(3, Integer.parseInt(profActivityFormModel.getActivity().get(i)));
                    stmt.executeUpdate();
                }

                conn.commit();
                cs.close();
                stmt.close();
                conn.close();
                return true;
            } else {
                conn.rollback();
                cs.close();
                conn.close();
                return false;
            }

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

    public static List<StudentModel> getProfessorStudentsByCourse(String profId, String courseId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call profesor_students_by_course(?, ?)");
            cs.setString(1, profId);
            cs.setString(2, courseId);

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

    public static boolean putGrades(String profId, ProfCatalogFormModel profCatalogFormModel) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement("select id_predare from predare where id_profesor = ? and id_materie = ?");
            ps.setString(1, profId);
            ps.setString(2, profCatalogFormModel.getCourse());

            ResultSet rs = ps.executeQuery();

            Integer pId = -1;
            if (rs.next()) {
                pId = rs.getInt(1);
            }

            if (pId != -1) {
                ps = conn.prepareStatement("select id_conditie from conditie where id_predare = ? and id_activitate = ?");
                ps.setInt(1, pId);

                int cnt = profCatalogFormModel.getActivity().size();

                for (int i = 0; i < cnt; i++) {
                    int aId = Integer.parseInt(profCatalogFormModel.getActivity().get(i));
                    int nota = Integer.parseInt(profCatalogFormModel.getGrade().get(i));

                    ps.setInt(2, aId);
                    rs = ps.executeQuery();

                    int cId = -1;
                    if (rs.next()) {
                        cId = rs.getInt(1);
                    } else {
                        throw new Exception();
                    }

                    String sql = "insert into catalog values (?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, 0);
                    stmt.setString(2, profCatalogFormModel.getStudent());
                    stmt.setInt(3, cId);
                    stmt.setInt(4, nota);

                    stmt.executeUpdate();

                    stmt.close();
                }

                conn.commit();
                ps.close();
                conn.close();
                return true;
            } else {
                conn.rollback();
                ps.close();
                conn.close();
                return false;
            }

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

    public static List<ProfCatalogModel> getCatalog(String profId, String courseId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("select * from vw_catalog where id_profesor = ? and id_materie = ?");
            cs.setString(1, profId);
            cs.setString(2, courseId);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<ProfCatalogModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapProfCatalogModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static boolean profAddSchedule(ProfAddActivityFormModel profAddActivityFormModel) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            CallableStatement cs = conn.prepareCall("call profesor_programare_activitati(?, ?, ?, ?, ?, ?)");
            cs.setString(1, profAddActivityFormModel.getActivity());
            cs.setString(2, profAddActivityFormModel.getStartDate());
            cs.setString(3, profAddActivityFormModel.getEndDate());
            cs.setString(4, profAddActivityFormModel.getIsExam());
            cs.setInt(5, 0);
            cs.setString(6, profAddActivityFormModel.getStudentsNb());

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

    public static List<CalendarModel> getProfessorCalendar(String profId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call profesor_calendar(?)");
            cs.setString(1, profId);

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

    public static List<CalendarModel> getProfessorCalendarCurrent(String profId) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call profesor_calendar_curent(?)");
            cs.setString(1, profId);

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
}
