package Database.MyBatis.mybatis7.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Student {

    public Student(){
        System.out.println("我被构造了");
    }

    int sid;
    String name;
    String sex;
}
