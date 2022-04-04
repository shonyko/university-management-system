package com.example.demo.Repositories;

import com.example.demo.Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserRepository extends RepositoryBase {
    public static UserLoginModel getUserByLogin(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("call login (?, ?)");
            cs.setString(1, username);
            cs.setString(2, password);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                var user = new UserLoginModel();
                user.setId(rs.getInt("id_utilizator"));
                user.setUsername(rs.getString("nume_utilizator"));
                user.setPassword((rs.getString("pass_hash")));

                return user;
            }

            return null;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static UserModel getUserByKey(String key) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("select u.*, t.nume, t.grad from utilizator u inner join tip_utilizator t on t.id_tip_utilizator = u.id_tip_utilizator where u.id_utilizator = ?");
            cs.setString(1, key);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                return mapUserModel(rs);
            }

            return null;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<UserModel> getUsers() {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("select u.*, t.nume, t.grad from utilizator u inner join tip_utilizator t on t.id_tip_utilizator = u.id_tip_utilizator");

            ResultSet rs = cs.executeQuery();
            List<UserModel> l = new ArrayList<>();

            while (rs.next()) {
                var user = mapUserModel(rs);

                l.add(user);
            }

            return l;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<UserTypeModel> getUserTypes() {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("select * from tip_utilizator");

            ResultSet rs = cs.executeQuery();

            List<UserTypeModel> l = new ArrayList<>();
            while (rs.next()) {
                var userType = new UserTypeModel();
                userType.setId(rs.getInt("id_tip_utilizator"));
                userType.setName(rs.getString("nume"));
                userType.setRank(rs.getInt("grad"));

                l.add(userType);
            }

            return l;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static boolean updateUser(UserModel user) {
        return updateUser(user, null, null);
    }

    public static boolean updateUser(UserModel user, ProfesorModel profesorModel, StudentModel studentModel) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            conn.setAutoCommit(false);

            PreparedStatement cs = conn.prepareStatement("replace into utilizator values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            cs.setInt(1, user.getId());
            cs.setInt(2, user.getType());
            cs.setString(3, user.getCnp());
            cs.setString(4, user.getSecondName());
            cs.setString(5, user.getFirstName());
            cs.setString(6, user.getAddress());
            cs.setString(7, user.getPhone());
            cs.setString(8, user.getEmail());
            cs.setString(9, user.getIban());
            cs.setInt(10, user.getContract());

            System.out.println(cs);

            int affectedRows = cs.executeUpdate();
            System.out.println(affectedRows);
            ResultSet rs = cs.getGeneratedKeys();

            int newId = -1;
            if (rs.next()) {
                newId = rs.getInt(1);
            }

            if (affectedRows >= 1) {
                if (affectedRows == 2) {
                    newId = user.getId();
                }

                PreparedStatement stmt = null;
                if (user.getType() == UserType.PROFESSOR) {
                    String sql = "replace into profesori values(?, ?, ?, ?)";
                    stmt = conn.prepareStatement(sql);

                    stmt.setInt(1, newId);
                    stmt.setInt(2, profesorModel.getMinHours());
                    stmt.setInt(3, profesorModel.getMaxHours());
                    stmt.setString(4, profesorModel.getDepartment());

                    stmt.executeUpdate();
                }

                if (user.getType() == UserType.STUDENT) {
                    String sql = "replace into student values(?, ?, ?)";
                    stmt = conn.prepareStatement(sql);

                    stmt.setInt(1, newId);
                    stmt.setInt(2, studentModel.getYear());
                    stmt.setInt(3, studentModel.getClassesNb());

                    stmt.executeUpdate();
                }

                conn.commit();
                cs.close();
                if (stmt != null) {
                    stmt.close();
                }
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

    public static boolean deleteUser(String key) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("delete from utilizator where id_utilizator = ?");
            cs.setString(1, key);

            cs.execute();
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static StudentModel getStudent(String id) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("select * from utilizator u inner join student s on s.id_student = u.id_utilizator where u.id_utilizator = ?");
            cs.setString(1, id);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                return mapStudentModel(rs);
            }

            return null;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static StudentModel getStudent(UserModel userModel) {
        return getStudent(userModel.getId().toString());
    }

    public static ProfesorModel getProfesor(UserModel userModel) {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("select * from utilizator u inner join profesori p on p.id_profesor = u.id_utilizator where u.id_utilizator = ?");
            cs.setInt(1, userModel.getId());

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                return mapProfessorModel(rs);
            }

            return null;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public static List<ActivityModel> getActivities() {
        try {
            Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            CallableStatement cs = conn.prepareCall("select * from activitate");

            System.out.println(cs);

            ResultSet rs = cs.executeQuery();

            List<ActivityModel> l = new ArrayList<>();
            while (rs.next()) {
                l.add(mapActivityModel(rs));
            }

            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}
