package Lombok.lombok5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//@AllArgsConstructor // 有参的构造
//@AllArgsConstructor(staticName = "create") // 转换为静态工厂方法

//@NoArgsConstructor // 无参的构造
//@NoArgsConstructor(staticName = "create", force = true) // 同上 (force强制无参并给一个默认值)

//@RequiredArgsConstructor

//@Data // 包含以上所有东西
@Data(staticConstructor = "create")
public class Student {

    //final Integer sid;
    Integer sid;
    String name;
    String sex;

}
