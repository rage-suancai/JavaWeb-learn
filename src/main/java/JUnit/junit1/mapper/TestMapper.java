package JUnit.junit1.mapper;

import JUnit.junit1.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@CacheNamespace(readWrite = false)
public interface TestMapper {

    @Select("select * from student")
    List<Student> selectStudent();

    @Select("select * from student where sid = #{sid} and sex = #{sex}")
    Student getStudentBySidAndSex(@Param("sid") int sid, @Param("sex") String sex);
}
