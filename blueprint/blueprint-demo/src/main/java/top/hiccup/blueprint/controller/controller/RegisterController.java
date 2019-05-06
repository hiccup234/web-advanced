package top.hiccup.blueprint.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wenhy on 2018/2/4.
 */
@Controller
@RequestMapping(value = "/stu")
public class RegisterController{

    @RequestMapping("/register")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("index.jsp");
    }


}
