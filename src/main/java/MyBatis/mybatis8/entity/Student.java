package MyBatis.mybatis8.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Student {

    /*public Student(int sid){
        System.out.println("我是一号构造方法" + sid);
    }

    public Student(int sid, String name){
        System.out.println("我是二号构造方法" + sid + name);
    }*/

    int sid;
    String name;
    String sex;
    //Teacher teacher;
}
