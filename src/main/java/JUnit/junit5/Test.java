package JUnit.junit5;

import lombok.extern.java.Log;

import java.io.IOException;

/**
 * 使用Lombok快速开启日志
 * 我们发现 如果我们现在需要全面使用日志系统 而不是传统的直接打印 那么就需要在每个类都去编写Logger的代码
 * 这样显然是很冗余的 能否简化一下这个流程呢
 *
 * 前面我们学习了Lombok 我们也体会到Lombok给我们带来的便捷 我们可以通过一个注解快速生成构造方法 Getter和Setter
 * 同样的 Logger也可以使用Lombok快速生成
 *              @Log
 *              public class Main {
 *                  log.info("我是日志信息");
 *              }
 *
 * 只需要添加一个@Log注解即可 添加后 我们可以直接使用一个静态变量log 而它是自动生成的Logger 我们也可以手动指定名称:
 *              @Log(topic = "打工是不可能打工的")
 *              public class main {
 *                  System.out.println("自动生成的Logger名称: " + log.getName());
 *                  log.info("我是日志信息");
 *              }
 */
//@Log
@Log(topic = "打工是不可能打工的")
public class Test {
    
    static void test1(){
        log.info("我是日志信息");
    }

    static void test2(){
        System.out.println("自动生成的Logger名称: " + log.getName());
        log.info("我是日志信息");
    }
    
    public static void main(String[] args) throws IOException {
        test1();
        //test2();
    }

}
