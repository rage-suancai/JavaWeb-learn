package JUnit.junit4;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * 编写日志配置文件
 * 我们可以通过进行配置文件来规定日志打印器的一些默认值:
 *          # RootLogger 的默认处理器为
 *          handlers = java.util.logging.ConsoleHandler
 *          # RootLogger 的默认的日志级别
 *          .level = WARNING
 *
 * 我们来尝试使用配置文件来进行配置:
 *          LogManager manager = LogManager.getLogManager();
 *          manager.readConfiguration(new FileInputStream("test.properties"));
 *
 *          Logger logger = Logger.getLogger(Test.class.getName());
 *          logger.log(Level.INFO, "我是普通日志消息");
 *
 * 我们也可以去修改ConsoleHandler的默认配置:
 *          # 指定默认日志级别
 *          java.util.logging.ConsoleHandler.level = ALL
 *          # 指定默认日志消息格式
 *          java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
 *          # 指定默认的字符集
 *          java.util.logging.ConsoleHandler.encoding = UTF-8
 *
 * 其实 我们阅读ConsoleHandler的源码就会发现 它就是通过读取配置文件来进行某些参数设置
 */
public class Test {

    static void test1() throws IOException {
        LogManager manager = LogManager.getLogManager();
        manager.readConfiguration(new FileInputStream("test.properties"));

        Logger logger = Logger.getLogger(Test.class.getName());
        logger.log(Level.CONFIG, "我是普通日志消息");
    }

    public static void main(String[] args) throws IOException {
        test1();
    }

}
