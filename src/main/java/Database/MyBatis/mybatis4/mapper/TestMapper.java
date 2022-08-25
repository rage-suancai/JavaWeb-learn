package Database.MyBatis.mybatis4.mapper;

import Database.MyBatis.mybatis4.entity.Student;

import java.util.List;

public interface TestMapper {
    List<Student> selectStudent();

    Student getStudentById(int id);

    int addStudent(Student student);

    int deleteStudent(int id);

    int updateStudent(Student student);

}
