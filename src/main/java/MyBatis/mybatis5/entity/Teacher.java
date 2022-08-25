package MyBatis.mybatis5.entity;

import lombok.Data;

import java.util.List;

@Data
public class Teacher {
    /**
     * 一个老师可以教授多个学生 那么能否一次性将老师的学生全部映射给此老师的对象呢 比如:
     */
    int tid;
    String name;
    List<String> studentList;

}
