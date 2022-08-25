package JUnit.junit5.mapper;

import JUnit.junit2.entity.Student;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@CacheNamespace(readWrite = false)
public interface TestMapper {

    @Select("select * from student")
    List<Student> selectStudent();

    @Select("select * from student where sid = #{sid} and sex = #{sex}")
    Student getStudentBySidAndSex(@Param("sid") int sid, @Param("sex") String sex);
}
