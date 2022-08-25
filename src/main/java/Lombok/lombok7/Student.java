package Lombok.lombok7;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
//@Builder(access = AccessLevel.PRIVATE) // 控制静态方法访问权限
//@Builder(setterPrefix = "text") // 给属性名加上前缀
//@Builder(toBuilder = true) // 给类一个方法 让方法变回Builder
public class Student {
    //@Builder.Default // 给属性设置默认值
    //Integer sid = 10;

    //@Builder.ObtainVia(method = "getName") // 调用getName方法 而不是调用this方法

    Integer sid;
    String name;
    String sex;

    /*public String getName() {
        return name;
    }*/
}
