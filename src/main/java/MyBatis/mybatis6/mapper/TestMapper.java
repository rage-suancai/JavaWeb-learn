package MyBatis.mybatis6.mapper;

import MyBatis.mybatis6.entity.Student;
import MyBatis.mybatis6.entity.Teacher;

import java.util.List;

public interface TestMapper {
    List<Student> selectStudent(int tid);

    Student getStudentBySid(int sid);
    int addStudent(Student student);
    int deleteStudent(int sid);

    Teacher getTeacherById(int tid);

}
