package MyBatis.mybatis4.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Student {

    /*public Student(Integer xxx){
        System.out.println("构造方法1 xxx: " + xxx);
    }
    public Student(Integer xxx, String name){
        System.out.println("构造方法2 xxx: " + xxx);
        System.out.println("构造方法2 name: " + name);
    }
    /*public Student(int xxx, String name, String sex){
        System.out.println("构造方法2 xxx: " + xxx);
        System.out.println("构造方法2 name: " + xxx);
    }*/

    int id;
    String name;
    String sex;
    // int MyWages;

}
