package Database.MyBatis.mybatis6.entity;

import lombok.Data;

import java.util.List;

@Data
public class Teacher {
    int tid;
    String name;
    List<String> studentList;

}
