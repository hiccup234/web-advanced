package top.hiccup.blueprint.service.impl;

import top.hiccup.blueprint.dao.IStudentDao;
import top.hiccup.blueprint.entity.Student;
import top.hiccup.blueprint.service.IStudentSmo;
import org.springframework.cglib.core.DefaultGeneratorStrategy;
import org.springframework.objenesis.SpringObjenesis;

/**
 * Created by wenhy on 2018/2/4.
 */
public class StudentSmoImpl implements IStudentSmo{

    private IStudentDao studentDao;

    public void setStudentDao(IStudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void addStudent(Student student) {

        SpringObjenesis s = new SpringObjenesis();
        DefaultGeneratorStrategy d = new DefaultGeneratorStrategy();

        studentDao.saveStudent(student);
    }
}
