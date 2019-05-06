package top.hiccup.blueprint.controller.controller;

import top.hiccup.blueprint.service.IStudentSmo;
import top.hiccup.blueprint.entity.Student;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wenhy on 2018/2/4.
 */
public class StudentController implements Controller {

    private IStudentSmo studentSmo;

    public void setStudentSmo(IStudentSmo studentSmo) {
        this.studentSmo = studentSmo;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        String ageStr = request.getParameter("age");
        Integer age = Integer.valueOf(ageStr);
        Student student = new Student(name, age);
        studentSmo.addStudent(student);
        return new ModelAndView("success.jsp");
    }


}
