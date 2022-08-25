package Maven.maven3.mapper;

import Maven.maven3.entity.Student;
import org.apache.ibatis.annotations.Select;

public interface TestMapper {

    @Select("select * from student where sid = #{sid}")
    Student getStudentBySid(int sid);

}
