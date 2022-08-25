package MyBatis.mybatis8.mapper;

import MyBatis.mybatis8.entity.Student;
import MyBatis.mybatis8.entity.Teacher;
import org.apache.ibatis.annotations.*;

@CacheNamespace(readWrite = false)
public interface TestMapper {

    @Insert("insert into student(name, sex) values(#{name}, #{sex})")
    int addStudent(Student student);

    /*@Results({
            @Result(column = "sid", property = "sid"),
            @Result(column = "name", property = "sex"),
            @Result(column = "sex", property = "name"),
    })
    @Select("select * from student where sid = #{sid}")
    Student getStudentBySid(int sid);*/

    /*@Results({
            @Result(column = "tid", property = "tid", id = true),
            @Result(column = "name", property = "name"),
            @Result(column = "tid", property = "studentList", many =
                @Many(select = "getStudentByTid")
            )
    })*/
    @Select("select * from teacher where tid = #{tid}")
    Teacher getTeacherByTid(int tid);

    /*@Select("select * from student inner join teach on student.sid = teach.sid where tid = #{tid}")
    List<Student> getStudentByTid(int tid);*/

    /*@Results({
            @Result(column = "sid", property = "sid", id = true),
            @Result(column = "name", property = "name"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "sid", property = "teacher", one =
                @One(select = "getTeacherBySid")
            )
    })*/
    @Select("select * from student where sid = #{sid}")
    @ConstructorArgs({
            @Arg(column = "sid", javaType = int.class),
            @Arg(column = "name", javaType = String.class)
    })
    Student getStudentBySid(int sid);

    @Select("select * from teacher inner join teach on teacher.tid = teach.tid where sid = #{sid}")
    Teacher getTeacherBySid(int sid);

    @Select("select * from student where sid = #{sid} and sex = #{sex}")
    Student getStudentBySidAndSex(@Param("sid") int sid, @Param("sex") String sex);

    @Insert("insert into student(sid, name, sex) values(#{sid}, #{student.name}, #{student.sex})")
    int addStudentAndSid(@Param("sid") int sid, @Param("student") Student student);

}
