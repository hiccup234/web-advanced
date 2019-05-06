package top.hiccup.blueprint.controller.controller;

import top.hiccup.blueprint.entity.po.Student;
import top.hiccup.blueprint.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wenhy on 2018/2/4.
 */
@Controller
@RequestMapping("/stu")
public class StudentController {

    @Autowired
    @Qualifier("studentSmo")
    private IUserService studentSmo;

    @RequestMapping("doRegister")
    public String doRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        String ageStr = request.getParameter("age");
        Integer age = Integer.valueOf(ageStr);
        Student student = new Student(name, age);
//        studentSmo.addStudent(student);
        return "success.jsp";
    }


}
