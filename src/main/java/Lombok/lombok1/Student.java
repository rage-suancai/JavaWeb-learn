package Lombok.lombok1;

import lombok.*;

/**
 * 认识Lombok
 * 我们发现 在以往编写项目时 尤其是在类进行类内部成员字段封装时 需要编写大量的get/set方法 这不仅使得我们类定义中充满了get和set方法
 * 同时如果字段名称发生改变 又要挨个进行修改 甚至当字段变得很多时 构造方法的编写会非常麻烦 通过使用 Lombok(小辣椒) 就可以解决这样的问题
 *
 * 配置Lombok
 *
 *      > 首先我们需要导入Lombok的jar依赖 和jdbc一样 放在项目根目录下直接导入
 *      > 然后我们需要安装一下Lombok插件 由于IDEA默认都安装了Lombok的插件 因此直接导入依赖就可以使用了
 *      > 重启IDEA
 *
 * Lombok是一种插件化注解API 是通过添加注解来实现的 然后在javac进行编译的时候 进行处理
 *
 * java的编译过程可以分成三个阶段
 *
 *      1.所有源文件会被解析成语法树
 *      2.调用注解处理器 如果注解处理器产生了新的源文件 新文件也要进行编译
 *      3.最后 语法树会被分析并转化成类文件
 *
 * 实际上在上述的第二阶段 会执行lombok.core.AnnotationProcessor 它所做的工作就是我们上面所说的 修改语法树
 */

@Setter
@Getter
@AllArgsConstructor
public class Student {
    /**
     * 我们来看看 使用原生方式和小辣椒方式编写类的区别 首先是传统方式
     */
    /*private Integer sid;
    private String name;
    private String sex;

    public Student(Integer sid, String name, String sex) { // 我们发现传统的方式长到爆炸
        this.sid = sid;
        this.name = name;
        this.sex = sex;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }*/

    /**
     * 而使用Lombok之后
     * 使用Lombok之后 只需要添加几个注解 就能够解决掉我们之前长长的代码
     */
    private Integer sid;
    private String name;
    private String sex;

}
