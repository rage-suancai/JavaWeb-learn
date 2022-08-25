package Database.MyBatis.mybatis9.entity;

import lombok.Data;

import java.util.List;

@Data
public class Teacher {
    int tid;
    String name;
    List<String> studentList;

}
