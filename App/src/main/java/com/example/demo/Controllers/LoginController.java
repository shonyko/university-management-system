package com.example.demo.Controllers;

import com.example.demo.Models.*;
import com.example.demo.Repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Controller
public class LoginController extends ControllerBase {

    @GetMapping("/")
    public ModelAndView index(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        if (key != null) {
            return new ModelAndView("redirect:/account");
        }

        return new ModelAndView("fancyLogin");
    }

    @GetMapping("/account")
    public ModelAndView account(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        if (key == null) {
            return new ModelAndView("redirect:/");
        }

        model.addAttribute("view", "fancyProfile");
        model.addAttribute("obj", UserRepository.getUserByKey(key));
        return new ModelAndView("layout");
    }

    @PostMapping("/login")
    public ModelAndView login(HttpSession session, Model model,
                              UserLoginModel userLoginModel) {
        var user = UserRepository.getUserByLogin(userLoginModel.getUsername(), userLoginModel.getPassword());

        model.addAttribute("success", true);

        if (user == null) {
            model.addAttribute("success", false);
        } else {
            session.setAttribute("key", user.getId().toString());
            model.addAttribute("redirect", "/account");
        }

        model.asMap().remove("userLoginModel");
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session, Model model) {
        session.removeAttribute("key");

        return new ModelAndView("redirect:/");
    }

    @PostMapping("/profile")
    public ModelAndView profile(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            var modelMap = new ModelMap();
            modelMap.addAttribute("args", UserRepository.getUserByKey(key));

            var partialView = getView("fancyProfile", modelMap);

            model.addAttribute("data", partialView);
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }

    @PostMapping("/empty")
    public ModelAndView getEmptyList(HttpSession session, Model model) {
        String key = (String) session.getAttribute("key");

        model.addAttribute("success", true);

        if (key == null) {
            model.addAttribute("success", false);
            model.addAttribute("redirect", "/");
        } else {
            model.addAttribute("data", Collections.emptyList());
        }

        return new ModelAndView(new MappingJackson2JsonView());
    }
}
