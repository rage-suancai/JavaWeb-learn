package Lombok.lombok4;

import lombok.EqualsAndHashCode;

//@EqualsAndHashCode(exclude = "sid") // 设置某个字段不需要比较
@EqualsAndHashCode(cacheStrategy = EqualsAndHashCode.CacheStrategy.LAZY) // 缓存策略(懒加载)
public class Student {
    Integer sid;
    String name;
    String sex;
}

/*@EqualsAndHashCode(callSuper = true) // 设置进行父类的比较
class StudentSub extends Student{
    Integer number;
}*/
