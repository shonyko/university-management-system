package com.example.demo.Controllers;

import com.example.demo.Models.EventFormModel;
import com.example.demo.Models.GroupFormModel;
import com.example.demo.Repositories.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/student")
public class StudentController extends ControllerBase {
    @PostMapping("/courses")
    public ModelAndView getStudentCoursesTable(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var partialView = getView("studentCursuriTable", null);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/joinable")
    public ModelAndView getStudentJoinableCourses(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            model.addAttribute("data", StudentRepository.getJoinableCourses(key));
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @GetMapping("/joinable")
    public ModelAndView asd(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            model.addAttribute("data", StudentRepository.getJoinableCourses(key));
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/selected")
    public ModelAndView getStudentSelectedCourses(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            model.addAttribute("data", StudentRepository.getSelectedCourses(key));
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @GetMapping("/selected")
    public ModelAndView asdf(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            model.addAttribute("data", StudentRepository.getSelectedCourses(key));
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/courses/{id}")
    public ModelAndView joinCourse(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            model.addAttribute("success", StudentRepository.joinCourse(key, id));
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @DeleteMapping("/courses/{id}")
    public ModelAndView dropCourse(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            model.addAttribute("success", StudentRepository.dropCourse(key, id));
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/grades")
    public ModelAndView getStudentGradesTable(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var partialView = getView("studentNoteTable", null);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/note")
    public ModelAndView getStudentGrades(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            model.addAttribute("data", StudentRepository.getGrades(key));
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/activities")
    public ModelAndView getStudentActivities(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var partialView = getView("studentActivities", null);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/activities/optiune")
    public ModelAndView getOptiuneOrarModal(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var modelMap = new ModelMap();
            modelMap.addAttribute("title", "Recomandare");
            modelMap.addAttribute("url", "/student/activities/optiune");

            var partialView = getView("OptiuneOrarModal", modelMap);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/activities/optiune/get")
    public ModelAndView getOptiuneOrar(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            System.out.println("Peste");
            model.addAttribute("data", StudentRepository.getActivityProgram(key));
            System.out.println("Macaroane");
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/activities/optiune/windows/get")
    public ModelAndView getWindows(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            model.addAttribute("data", StudentRepository.getWindows(key, StudentRepository.getActivityProgram(key).getTimetable()));
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/activities/add/{id}")
    public ModelAndView addActivity(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            boolean success = true;

            var p = StudentRepository.getCalendarModelById(id);

            if(p == null) {
                model.addAttribute("success", false);
                model.addAttribute("message", "Nu mai exista aceasta activitate !");
                return new ModelAndView(new MappingJackson2JsonView());
            }

            if(p.getStudentsNb() == p.getCurrStudentsNb()) {
                model.addAttribute("success", false);
                model.addAttribute("message", "Nu mai sunt locuri disponibile pentru aceasta activitate !");
                return new ModelAndView(new MappingJackson2JsonView());
            }

            var collisions = StudentRepository.getStudentCalendarCollisions(key, id);
            var target = collisions.stream().filter(x -> x.getId().toString().equals(id)).findFirst().orElse(null);

            if(target != null) {
                model.addAttribute("success", false);
                model.addAttribute("message", "Esti deja inscris la aceasta activitate");
                return new ModelAndView(new MappingJackson2JsonView());
            }

            if(collisions.size() > 0) {
                var activity = collisions.get(0);

                if(activity.getExam()) {
                    activity.setActivityName(activity.getExamName());
                }

                var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime date = LocalDateTime.parse(activity.getStartDate(), formatter);

                String zi = null;

                switch (date.toLocalDate().getDayOfWeek()) {
                    case MONDAY:
                        zi = "luni";
                        break;
                    case TUESDAY:
                        zi = "marti";
                        break;
                    case WEDNESDAY:
                        zi = "miercuri";
                        break;
                    case THURSDAY:
                        zi = "joi";
                        break;
                    case FRIDAY:
                        zi = "vineri";
                        break;
                    case SATURDAY:
                        zi = "sambata";
                        break;
                    case SUNDAY:
                        zi = "duminica";
                        break;
                }

                model.addAttribute("success", false);
                model.addAttribute("message", "Esti deja inscris la un " + activity.getActivityName() + " de " + activity.getCourseName() + " " + zi + " la ora " + date.toLocalTime().toString());
                return new ModelAndView(new MappingJackson2JsonView());
            }

            success &= StudentRepository.studentEnrollActivity(key, id);
            model.addAttribute("success", success);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/setActivities")
    public ModelAndView getStudentAddActivities(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var partialView = getView("studentAddActivities", null);

            model.addAttribute("data", partialView);
        }

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
            var data = StudentRepository.getStudentCalendar(key);
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
            var data = StudentRepository.getStudentCalendarCurrent(key);
            model.addAttribute("data", data);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study")
    public ModelAndView getStudentStudyGroups(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var partialView = getView("studentStudyGroup", null);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/joinedGroups")
    public ModelAndView getGroups(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var data = StudentRepository.getStudentJoinedGroups(key);
            model.addAttribute("data", data);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/notJoinedGroups")
    public ModelAndView getNotGroups(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var data = StudentRepository.getStudentNotJoinedGroups(key);
            model.addAttribute("data", data);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/{id}/members")
    public ModelAndView getMembers(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var modelMap = new ModelMap();
            modelMap.addAttribute("title", "Membrii grup");
            modelMap.addAttribute("members", StudentRepository.getGroupMembers(key, id));
            modelMap.addAttribute("suggestions", StudentRepository.getGroupSuggestions(key, id));

            var partialView = getView("MembriiGrupModal", modelMap);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/{id}/messages")
    public ModelAndView getMessagesModal(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var modelMap = new ModelMap();
            modelMap.addAttribute("title", "Mesaje grup");
            modelMap.addAttribute("url", "/student/study/" + id + "/messages");

            var partialView = getView("MesajeModal", modelMap);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/{id}/events")
    public ModelAndView getEventsModal(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var modelMap = new ModelMap();
            modelMap.addAttribute("title", "Evenimente");
            modelMap.addAttribute("url", "/student/study/" + id + "/events");
            modelMap.addAttribute("id", id);

            var partialView = getView("EvenimenteModal", modelMap);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @GetMapping("/study/{id}/events/new")
    public ModelAndView getAddEvent(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var modelMap = new ModelMap();

            modelMap.addAttribute("title", "Creeaza eveniment");
            modelMap.addAttribute("id", id);
            modelMap.addAttribute("professors", StudentRepository.getGroupProfessors(key, id));
            var partialView = getView("NewEventForm", modelMap);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/{id}/events/get")
    public ModelAndView getEvents(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            model.addAttribute("data", StudentRepository.getGroupEvents(key, id));
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/{id}/events/set")
    public ModelAndView getEvents(HttpSession session, Model model, @PathVariable String id, EventFormModel eventFormModel) {
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
                studentsNb = Integer.parseInt(eventFormModel.getMinEntries());
                if(studentsNb <= 2) {
                    throw new Exception();
                }
            }
            catch (Exception e) {
                model.addAttribute("success", false);
                model.addAttribute("message", "Numarul de studenti trebuie sa fie un numar pozitiv mai mare ca 1 !");
                return new ModelAndView(new MappingJackson2JsonView());
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
            var date = LocalDateTime.parse(eventFormModel.getDate(), formatter);

            if(date.isBefore(LocalDateTime.now())) {
                model.addAttribute("success", false);
                model.addAttribute("message", "Data trebuie sa fie in viitor !");
                return new ModelAndView(new MappingJackson2JsonView());
            }

            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            eventFormModel.setDate(date.format(formatter));

            success &= StudentRepository.studentCreateEvent(key, id, eventFormModel);

            model.addAttribute("success", success);
            model.addAttribute("message", "Oops! A aparut o eroare...");
        }

        model.asMap().remove("eventFormModel");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/{id}/events/{id2}")
    public ModelAndView enrollEvent(HttpSession session, Model model, @PathVariable String id, @PathVariable String id2) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            boolean success = true;

            success &= StudentRepository.studentJoinEvent(key, id, id2);

            model.addAttribute("success", success);
            model.addAttribute("message", "Oops! A aparut o eroare...");
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/{id}/messages/get")
    public ModelAndView getMessages(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            model.addAttribute("data", StudentRepository.getGroupMessages(key, id));
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/{id}/messages/set")
    public ModelAndView addMessage(HttpSession session, Model model, @PathVariable String id, String message) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            model.addAttribute("data", StudentRepository.studentAddMessage(key, id, message));
        }

        model.asMap().remove("message");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/{id}/join")
    public ModelAndView joinGroup(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            model.addAttribute("data", StudentRepository.studentJoinGroup(key, id));
        }

        model.asMap().remove("message");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/{id}/exit")
    public ModelAndView exitGroup(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            model.addAttribute("data", StudentRepository.studentExitGroup(key, id));
        }

        model.asMap().remove("message");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/{id}/delete")
    public ModelAndView deleteGroup(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var success = true;

            success &= StudentRepository.studentDeleteGroup(key, id);

            String msg = null;
            if(success) {
                msg = "Stergerea a fost facuta cu succes!";
            }
            else {
                msg = "Stergerea a esuat!";
            }

            model.addAttribute("message", msg);
            model.addAttribute("success", success);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @GetMapping("/study/new")
    public ModelAndView getStudentAddGroup(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var modelMap = new ModelMap();

            modelMap.addAttribute("title", "Creeaza grup");
            modelMap.addAttribute("courses", StudentRepository.getSelectedCourses(key));
            var partialView = getView("studentCreateGroupForm", modelMap);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @GetMapping("/study/update/{id}")
    public ModelAndView getStudentAddGroup(HttpSession session, Model model, @PathVariable String id) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var modelMap = new ModelMap();

            var groups = StudentRepository.getStudentJoinedGroups(key);
            modelMap.addAttribute("title", "Actualizeaza grup");
            modelMap.addAttribute("courses", StudentRepository.getSelectedCourses(key));
            modelMap.addAttribute("readonly", true);
            modelMap.addAttribute("value", groups.stream().filter(x -> x.getId().toString().equals(id)).findFirst().orElse(null).getCourseId());
            modelMap.addAttribute("name", groups.stream().filter(x -> x.getId().toString().equals(id)).findFirst().orElse(null).getName());
            var partialView = getView("studentCreateGroupForm", modelMap);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/new/set")
    public ModelAndView createGroup(HttpSession session, Model model, GroupFormModel groupFormModel) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            boolean success = true;

            success &= StudentRepository.studentCreateGroup(key, groupFormModel);

            model.addAttribute("success", success);
        }

        model.asMap().remove("groupFormModel");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/study/update/{gid}")
    public ModelAndView updateGroup(HttpSession session, Model model, @PathVariable String gid, GroupFormModel groupFormModel) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            boolean success = true;

            success &= StudentRepository.studentUpdateGroup(key, gid, groupFormModel);

            model.addAttribute("success", success);
        }

        model.asMap().remove("groupFormModel");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/inbox")
    public ModelAndView getInbox(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var partialView = getView("studentInbox", null);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/inbox/get")
    public ModelAndView getInboxEntries(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if(key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        }
        else {
            var data = StudentRepository.studentGetInbox(key);
            model.addAttribute("data", data);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }
}
