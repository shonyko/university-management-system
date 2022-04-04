package com.example.demo.Controllers;

import com.example.demo.Models.*;
import com.example.demo.Repositories.AdminRepository;
import com.example.demo.Repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController extends ControllerBase {
    @PostMapping("/tables")
    public ModelAndView tables(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            var modelMap = new ModelMap();
            modelMap.addAttribute("args", UserRepository.getUserByKey(key));

            var partialView = getView("fancyTables", modelMap);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/students")
    public ModelAndView getStudentTable(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            var partialView = getView("studentTable", null);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/professors")
    public ModelAndView getProfessorTable(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            var partialView = getView("profesorTable", null);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @GetMapping("/professors/assign/{name}")
    public ModelAndView getProfessorAssignForm(HttpSession session, Model model, @PathVariable String name) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            var modelMap = new ModelMap();
            modelMap.addAttribute("title", "Asigneaza profesor");
            var curs = AdminRepository.getCourseByName(name).get(0);
            modelMap.addAttribute("curs", curs);
            modelMap.addAttribute("profesori", AdminRepository.getProfessorsByNotCourse(curs.getId().toString()));
            modelMap.addAttribute("activitati", UserRepository.getActivities());

            var partialView = getView("AssignProf", modelMap);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/professors/assign")
    public ModelAndView assignProfessor(HttpSession session, Model model, ActivityFormModel activityFormModel) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            boolean success = true;
            success &= AdminRepository.assignProfessor(activityFormModel);
            model.addAttribute("success", success);

        }

        model.asMap().remove("activityFormModel");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/admins")
    public ModelAndView getAdminsTable(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            var partialView = getView("adminTable", null);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/admin/courses")
    public ModelAndView getCoursesTable(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            var partialView = getView("cursuriTable", null);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @GetMapping("/users")
    public ModelAndView getUsers(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            model.addAttribute("data", UserRepository.getUsers());
        }


        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/users/filter")
    public ModelAndView getUsersFiltered(HttpSession session, Model model, UserModel userModel) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            model.addAttribute("data", AdminRepository.getUsersFiltered(userModel));
        }


        model.asMap().remove("userModel");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @GetMapping("/users/{id}")
    public ModelAndView getUserModal(HttpSession session, Model model, @PathVariable String id, UserModel userModel) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            var modelMap = new ModelMap();

            modelMap.addAttribute("title", "Editeaza");

            var user = UserRepository.getUserByKey(id);
            List<Field> l = new ArrayList<>();

            l.add(new Field("ID", "id", user.getId().toString()).setHidden(true));
            l.add(new Field("CNP", "cnp", user.getCnp()));
            l.add(new Field("Nume", "secondName", user.getSecondName()));
            l.add(new Field("Prenume", "firstName", user.getFirstName()));
            l.add(new Field("Adresa", "address", user.getAddress()));
            l.add(new Field("Telefon", "phone", user.getPhone()));
            l.add(new Field("Email", "email", user.getEmail()));
            l.add(new Field("IBAN", "iban", user.getIban()));
            l.add(new Field("Nr. contract", "contract", user.getContract().toString()));

            var type = user.getType();
            if (type == UserType.STUDENT) {
                var studentModel = UserRepository.getStudent(user);
                l.add(new Field("An", "year", studentModel.getYear().toString()));
                l.add(new Field("Nr. ore", "classesNb", studentModel.getClassesNb().toString()));
            }
            if (type == UserType.PROFESSOR) {
                var profesorModel = UserRepository.getProfesor(user);
                l.add(new Field("Nr. minim ore", "minHours", profesorModel.getMinHours().toString()));
                l.add(new Field("Nr. maxim ore", "maxHours", profesorModel.getMaxHours().toString()));
                l.add(new Field("Departament", "department", profesorModel.getDepartment()));
            }

            modelMap.addAttribute("disabled", true);
            modelMap.addAttribute("selectedValue", user.getType());
            modelMap.addAttribute("userTypes", UserRepository.getUserTypes());
            modelMap.addAttribute("fields", l);
            var partialView = getView("EditModal", modelMap);

            model.addAttribute("data", partialView);
        }


        model.asMap().remove("userModel");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/users/set")
    public ModelAndView setUser(HttpSession session, Model model, UserModel userModel, StudentModel studentModel, ProfesorModel profesorModel) {
        String key = (String) session.getAttribute("key");

        boolean success = true;

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            success &= UserRepository.updateUser(userModel, profesorModel, studentModel);
            model.addAttribute("success", success);
        }


        model.asMap().remove("userModel");
        model.asMap().remove("studentModel");
        model.asMap().remove("profesorModel");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @GetMapping("/users/add")
    public ModelAndView getUserModal(HttpSession session, Model model, UserModel userModel) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            var modelMap = new ModelMap();

            modelMap.addAttribute("title", "Adauga");

            List<Field> l = new ArrayList<>();

            l.add(new Field("ID", "id", "0").setHidden(true));
            l.add(new Field("CNP", "cnp"));
            l.add(new Field("Nume", "secondName"));
            l.add(new Field("Prenume", "firstName"));
            l.add(new Field("Adresa", "address"));
            l.add(new Field("Telefon", "phone"));
            l.add(new Field("Email", "email"));
            l.add(new Field("IBAN", "iban"));
            l.add(new Field("Nr. contract", "contract"));

            var type = userModel.getType();
            if (type != null) {
                modelMap.addAttribute("selectedValue", userModel.getType());
                modelMap.addAttribute("disabled", true);

                if (type == UserType.STUDENT) {
                    l.add(new Field("An", "year"));
                    l.add(new Field("Nr. ore", "classesNb"));
                }
                if (type == UserType.PROFESSOR) {
                    l.add(new Field("Nr. minim ore", "minHours"));
                    l.add(new Field("Nr. maxim ore", "maxHours"));
                    l.add(new Field("Departament", "department"));
                }
            }
            modelMap.addAttribute("userTypes", UserRepository.getUserTypes());
            modelMap.addAttribute("fields", l);
            var partialView = getView("EditModal", modelMap);

            model.addAttribute("data", partialView);
        }


        model.asMap().remove("userModel");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @DeleteMapping("/users/{id}")
    public ModelAndView deleteUser(HttpSession session, Model model, @PathVariable String id) {
        model.addAttribute("success", UserRepository.deleteUser(id));
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/courses/filter")
    public ModelAndView getCoursesFiltered(HttpSession session, Model model, CourseModel courseModel) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            model.addAttribute("data", AdminRepository.getCourseByName(courseModel.getName()));
        }


        model.asMap().remove("courseModel");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/courses/filter/professors/{id}")
    public ModelAndView getProfessorsFilteredByCourse(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            var data = AdminRepository.getProfessorsByCourse(id);
            model.addAttribute("data", data);
        }


        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/courses/filter/students/{id}")
    public ModelAndView getStudentsFilteredByCourse(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            var data = AdminRepository.getStudentsByCourse(id);
            model.addAttribute("data", data);
        }


        return new ModelAndView(new MappingJackson2JsonView());
    }
}
