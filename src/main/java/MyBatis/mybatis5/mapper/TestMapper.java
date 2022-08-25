package MyBatis.mybatis5.mapper;

import MyBatis.mybatis5.entity.Student;
import MyBatis.mybatis5.entity.Teacher;

import java.util.List;

public interface TestMapper {
    List<Student> selectStudent(int tid);

    /*Student getStudentBySid(int sid);
    int addStudent(Student student);
    int deleteStudent(int sid);*/

    Teacher getTeacherById(int tid);

}
