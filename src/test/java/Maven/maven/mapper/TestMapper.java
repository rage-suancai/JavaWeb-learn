package Maven.maven.mapper;

import Maven.maven.entity.Student;
import org.apache.ibatis.annotations.Select;

public interface TestMapper {

    @Select("select * from student where sid = #{sid}")
    Student getStudentBySid(int sid);

}
