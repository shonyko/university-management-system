package com.example.demo.Controllers;

import com.example.demo.Models.ProfActivityFormModel;
import com.example.demo.Models.ProfActivityModel;
import com.example.demo.Models.ProfAddActivityFormModel;
import com.example.demo.Models.ProfCatalogFormModel;
import com.example.demo.Repositories.AdminRepository;
import com.example.demo.Repositories.ProfRepository;
import com.example.demo.Repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/professor")
public class ProfessorController extends ControllerBase {
    @PostMapping("/courses")
    public ModelAndView getProfessorCoursesTable(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var partialView = getView("profesorCursuriTable", null);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/courses/assigned")
    public ModelAndView getAssignedCourses(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            model.addAttribute("data", ProfRepository.getProfesorAssignedCourses(key));
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @GetMapping("/courses/edit/{name}")
    public ModelAndView getWeightForm(HttpSession session, Model model, @PathVariable String name) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var modelMap = new ModelMap();
            modelMap.addAttribute("title", "Editeaza ponderi");
            var curs = AdminRepository.getCourseByName(name).get(0);
            modelMap.addAttribute("curs", curs);
            modelMap.addAttribute("activitati", ProfRepository.getProfessorActivities(key, curs.getId().toString()));

            var partialView = getView("PonderiForm", modelMap);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/courses/edit")
    public ModelAndView assignProfessor(HttpSession session, Model model, ProfActivityFormModel profActivityFormModel) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {

            boolean success = true;
            int sum = 0;
            for (String s : profActivityFormModel.getWeight()) {
                try {
                    sum += Integer.parseInt(s);
                }
                catch (Exception e) {
                    model.addAttribute("message", "Sunt acceptate doar numere !");
                    model.addAttribute("success", false);
                    return new ModelAndView(new MappingJackson2JsonView());
                }
            }

            if (sum != 100) {
                success = false;
                model.addAttribute("message", "Suma trebuie sa fie 100 !");
            }
            else {
                success &= ProfRepository.updateProfessorWeights(key, profActivityFormModel);
            }

            model.addAttribute("success", success);
        }

        model.asMap().remove("profActivityFormModel");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/catalog")
    public ModelAndView getProfessorCatalog(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var partialView = getView("profesorCatalog", null);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/activities")
    public ModelAndView getProfessorActivities(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var partialView = getView("profesorActivities", null);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @GetMapping("/activities/get")
    public ModelAndView getAddActivityForm(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var modelMap = new ModelMap();
            modelMap.addAttribute("title", "Adauga activitate");

            var activities = ProfRepository.getProfessorActivities(key, null);
            var labels = new HashSet<String>();

            class Temp {
                String label;
                List<ProfActivityModel> activities = new ArrayList<>();

                public String getLabel() {
                    return label;
                }

                public void setLabel(String label) {
                    this.label = label;
                }

                public List<ProfActivityModel> getActivities() {
                    return activities;
                }

                public void setActivities(List<ProfActivityModel> activities) {
                    this.activities = activities;
                }
            }

            List<Temp> l = new ArrayList<>();
            for(var e : activities) {
                if(labels.contains(e.getCourseName()) == false) {
                    labels.add(e.getCourseName());
                    var el = new Temp();
                    el.setLabel(e.getCourseName());
                    l.add(el);
                }

                var target = l.stream().filter(x -> x.getLabel().equals(e.getCourseName())).findFirst().orElse(null);
                target.getActivities().add(e);
            }

            modelMap.addAttribute("activities", l);

            var partialView = getView("ProfActivitiesForm", modelMap);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/activities/add")
    public ModelAndView addActivity(HttpSession session, Model model, ProfAddActivityFormModel profAddActivityFormModel) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            boolean success = true;

            int studentsNb = -1;

            try {
                studentsNb = Integer.parseInt(profAddActivityFormModel.getStudentsNb());
                if(studentsNb <= 0) {
                    throw new Exception();
                }
            }
            catch (Exception e) {
                model.addAttribute("success", false);
                model.addAttribute("message", "Numarul de studenti trebuie sa fie un numar pozitiv !");
                return new ModelAndView(new MappingJackson2JsonView());
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
            var startTime = LocalDateTime.parse(profAddActivityFormModel.getStartDate(), formatter);
            var endTime = LocalDateTime.now();

            if(startTime.isBefore(LocalDateTime.now())) {
                model.addAttribute("success", false);
                model.addAttribute("message", "Data de inceput trebuie sa fie in viitor !");
                return new ModelAndView(new MappingJackson2JsonView());
            }

            if(profAddActivityFormModel.getIsExam() != null) {
                profAddActivityFormModel.setIsExam("1");
            }
            else {
                profAddActivityFormModel.setIsExam("0");
            }

            if(profAddActivityFormModel.getIsExam().equals("0")) {
                endTime = LocalDateTime.parse(profAddActivityFormModel.getEndDate(), formatter);
                if(endTime.isBefore(startTime)) {
                    model.addAttribute("success", false);
                    model.addAttribute("message", "Data de final trebuie sa fie dupa data de inceput !");
                    return new ModelAndView(new MappingJackson2JsonView());
                }
            }
            else {
                endTime = startTime;
                endTime.plusHours(2);
            }

            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            profAddActivityFormModel.setStartDate(startTime.format(formatter));
            profAddActivityFormModel.setEndDate(endTime.format(formatter));

            success &= ProfRepository.profAddSchedule(profAddActivityFormModel);

            model.addAttribute("success", success);
            model.addAttribute("message", "Oops! A aparut o eroare...");
        }

        model.asMap().remove("profAddActivityFormModel");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/catalog/{course}/students")
    public ModelAndView getProfessorCatalogStudents(HttpSession session, Model model, @PathVariable String course) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var curs = AdminRepository.getCourseByName(course).get(0);
            model.addAttribute("data", ProfRepository.getProfessorStudentsByCourse(key, curs.getId().toString()));
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/catalog/{course}")
    public ModelAndView getProfessorCatalog(HttpSession session, Model model, @PathVariable String course) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var curs = AdminRepository.getCourseByName(course).get(0);
            model.addAttribute("data", ProfRepository.getCatalog(key, curs.getId().toString()));
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @GetMapping("/catalog/{cId}/{sId}")
    public ModelAndView getAddGradeForm(HttpSession session, Model model, @PathVariable String cId, @PathVariable String sId) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var modelMap = new ModelMap();
            modelMap.addAttribute("title", "Adauga note");
            var curs = AdminRepository.getCourseByName(cId).get(0);
            modelMap.addAttribute("curs", curs);
            modelMap.addAttribute("student", UserRepository.getStudent(sId));
            modelMap.addAttribute("activitati", ProfRepository.getProfessorActivities(key, curs.getId().toString()));

            var partialView = getView("CatalogNoteForm", modelMap);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/catalog/add")
    public ModelAndView addGrade(HttpSession session, Model model, ProfCatalogFormModel profCatalogFormModel) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {

            boolean success = true;
            int sum = 0;
            for (int i = profCatalogFormModel.getGrade().size() - 1; i>= 0; i--) {
                String s = profCatalogFormModel.getGrade().get(i);
                if(s.isEmpty() || s.isBlank()) {
                    profCatalogFormModel.getGrade().remove(s);
                    profCatalogFormModel.getActivity().remove(i);
                    continue;
                }

                try {
                    int nota = Integer.parseInt(s);
                    if(nota == -2) {
                        continue;
                    }
                    if(nota <= 0 || nota > 10) {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    model.addAttribute("message", "Sunt acceptate doar numere intre 1 si 10 sau -2 !");
                    model.addAttribute("success", false);
                    return new ModelAndView(new MappingJackson2JsonView());
                }
            }

            success &= ProfRepository.putGrades(key, profCatalogFormModel);

            model.addAttribute("success", success);
        }

        model.asMap().remove("profCatalogFormModel");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/calendar")
    public ModelAndView getCalendar(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var data = ProfRepository.getProfessorCalendar(key);
            model.addAttribute("data", data);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/calendar/now")
    public ModelAndView getCalendarCurrent(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var data = ProfRepository.getProfessorCalendarCurrent(key);
            model.addAttribute("data", data);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }
}
