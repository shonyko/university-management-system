package com.example.demo.Repositories;

import com.example.demo.Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminRepository extends RepositoryBase {
    public static List getUsersFiltered(UserModel userModel) {
        if (userModel.getSecondName() != null && (userModel.getSecondName().isEmpty() || userModel.getSecondName().isBlank())) {
            userModel.setSecondName(null);
        }
        if (userModel.getFirstName() != null && (userModel.getFirstName().isEmpty() || userModel.getFirstName().isBlank())) {
            userModel.setFirstName(null);
        }
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call admin_search_user(?, ?, ?)");
            cs.setString(1, userModel.getSecondName());
            cs.setString(2, userModel.getFirstName());
            cs.setInt(3, userModel.getType());

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List l = new ArrayList();
            while (rs.next()) {
                switch (userModel.getType()) {
                    case UserType.PROFESSOR:
                        l.add(mapProfessorModel(rs));
                        break;
                    case UserType.STUDENT:
                        l.add(mapStudentModel(rs));
                        break;
                    default:
                        l.add(mapUserModel(rs));
                }
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<CourseModel> getCourseByName(String courseName) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call admin_search_courses(?)");
            cs.setString(1, courseName);

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

    public static List<ProfesorModel> getProfessorsByCourse(String id) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call admin_professors_by_course(?)");
            cs.setString(1, id);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<ProfesorModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapProfessorModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<ProfesorModel> getProfessorsByNotCourse(String id) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call admin_professors_by_not_course(?)");
            cs.setString(1, id);

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<ProfesorModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapProfessorModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<StudentModel> getStudentsByCourse(String id) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call admin_students_by_course(?)");
            cs.setString(1, id);

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

    public static boolean assignProfessor(ActivityFormModel activityFormModel) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement("insert into predare values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, 0);
            ps.setInt(2, Integer.parseInt(activityFormModel.getProfessor()));
            ps.setInt(3, Integer.parseInt(activityFormModel.getCourse()));

            int rowsAffected = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            int newId = -1;
            if (rs.next()) {
                newId = rs.getInt(1);
            }

            if (rowsAffected == 1) {
                int cnt = activityFormModel.getActivity().size();
                int per = 100 / cnt;

                String sql = "insert into conditie values(?, ?, ?, ?)";

                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, 0);
                stmt.setInt(2, newId);
                stmt.setInt(4, per);
                for (int i = 0; i < cnt - 1; i++) {
                    stmt.setString(3, activityFormModel.getActivity().get(i));
                    stmt.executeUpdate();
                }

                stmt.setString(3, activityFormModel.getActivity().get(cnt - 1));
                stmt.setInt(4, 100 - (cnt - 1) * per);

                stmt.executeUpdate();

                conn.commit();
                ps.close();
                stmt.close();
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
}
