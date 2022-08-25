package Maven.maven2.mapper;

import Maven.maven2.entity.Student;
import org.apache.ibatis.annotations.Select;

public interface TestMapper {

    @Select("select * from student where sid = #{sid}")
    Student getStudentBySid(int sid);

}
