package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

@Controller
public class ControllerBase {
    @Autowired
    ViewResolver viewResolver;

    protected String getView(String viewName, ModelMap modelMap) {
        String res = null;
        try {
            var view = viewResolver.resolveViewName(viewName, Locale.US);
            MockHttpServletResponse mockResp = new MockHttpServletResponse();
            MockHttpServletRequest mockReq = new MockHttpServletRequest();
            view.render(modelMap, mockReq, mockResp);
            res = mockResp.getContentAsString();
        }
        catch (Exception e) {
            System.out.println("View rederer error: " + e.getMessage());
        }

        return res;
    }
}
