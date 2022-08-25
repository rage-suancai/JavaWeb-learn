package Lombok.lombok3;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString // lombok封装的快速实现toString方法
//@ToString(includeFieldNames = false) // 省略字段名称
//@ToString(exclude = {"name", "sex"}) // 省略某些字段
//@ToString(of = {"name", "sex"}) // 选择显示哪些字段
//@ToString(callSuper = true) // 额外显示父类toString结果
//@ToString(doNotUseGetters = true) // 避免默认先执行get方法
//@ToString(onlyExplicitlyIncluded = true) // 返回一个Student()
public class Student {
    @ToString.Include(rank = 2, name = "学号")
    Integer sid;
    //@ToString.Exclude // 指定排除的字段
    @ToString.Include(rank = 3, name = "姓名") // 指定显示的字段 (rank排序优先级 数值越大排序越靠前) (name替换默认字段名)
    String name;
    @ToString.Include(rank = 1, name = "年龄")
    String sex;

    /*public String getName(){
        System.out.println("调用了get方法");
        return name;
    }*/

}
