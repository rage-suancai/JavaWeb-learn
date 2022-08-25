package JUnit.junit2;

import java.io.IOException;
import java.util.logging.*;

/**
 * JUL日志系统
 * 我们之前一直都在使用System.out.println来打印信息 但是 如果项目中存在大量的控制台输出语句 会显得很凌乱 而且日志的粒度是不够细的
 * 假如我们现在希望 项目只在debug的情况下打印某些日志 而在实际运行时不打印日志 采用直接输出的方式就很难实现了 因此我们需要使用日志框架来规范化日志输出
 *
 * 而JDK为我们提供了一个自带的日志框架 位于java.util.longging包下 我们可以使用此框架来实现日志的规范化打印 使用起来非常简单:
 *          Logger logger = Logger.getLogger(Test.class.getName());
 *          logger.info("");
 * 我们发现 通过日志输出的结果会更加规范
 *
 * JUL日志讲解
 * 日志分为7个级别 详细信息我们可以在Level类中查看:
 *      > SEVERE(最高值) 一般用于代表严重错误
 *      > WARNING 一般用于表示某些警告 但是不足以判断为错误
 *      > INFO(默认级别) 常规消息
 *      > CONFIG
 *      > FINE
 *      > FINER
 *      > FINEST(最低值)
 *
 * 我们之前通过info方法直接输出的结果就是使用的默认级别的日志 我们可以通过log方法来设定该条日志的输出级别:
 *          Logger logger = Logger.getLogger(Test.class.getName());
 *          logger.log(Level.SEVERE, "我是出现严重错误日志", new IOException("我就是错误"));
 *          logger.log(Level.WARNING, "警告的内容");
 *          logger.log(Level.INFO, "普通的信息");
 *          logger.log(Level.CONFIG, "级别低于普通信息");
 *
 * 我们发现 级别低于默认级别的日志信息 无法输出到控制台 我们可以通过设置来修改日志的打印级别:
 *          // 修改日志级别
 *         logger.setLevel(Level.ALL);
 *         // 不使用父日志处理器
 *         logger.setUseParentHandlers(false);
 *         // 使用自定义日志处理器
 *         ConsoleHandler handler = new ConsoleHandler();
 *         handler.setLevel(Level.FINEST);
 *         logger.addHandler(handler);
 *
 * 每个Logger都有一个父日志打印器 我们可以通过getParent来获取:
 *          Logger logger = Logger.getLogger(Test.class.getName());
 *          System.out.println(logger.getParent().getClass());
 * 我们发现 得到得是class java.util.logging.LogManager$RootLogger这个类 它默认使用的是ConsoleHandler 且日志级别为INFO
 * 由于每一个日志打印器都会直接使用父类的处理器 因此我们之前需要关闭父类然后使用我们自己的处理器
 *
 * 我们通过使用自己日志处理器来自定义级别的信息打印到控制台 当然 日志处理器不仅仅只有控制台打印 我们也可以使用文件处理器来处理日志信息 我们继续添加一个处理器:
 *          FileHandler fileHandler = new FileHandler("test.log");
 *          fileHandler.setLevel(Level.WARNING);
 *          fileHandler.setFormatter(formatter);
 *          logger.addHandler(fileHandler);
 * 注意 这个时候就有了两个日志处理器了 因此控制台和文件都会生效 如果日志的打印格式我们不喜欢 我们还可以自定义打印格式
 * 比如我们控制台处理器就默认使用的是SimpleFormatter 而文件处理器则是使用的XMLFormatter 我们可以自定义:
 *          SimpleFormatter formatter = new SimpleFormatter();
 *
 *          handler.setFormatter(new XMLFormatter());
 *          fileHandler.setFormatter(formatter);
 *
 * 日志可以设置过滤器 如果我们不希望某些日志信息被流出 我们可以配置过滤规则:
 *         Logger logger = Logger.getLogger(Test.class.getName());
 *         logger.setFilter(record -> record.getMessage().contains("普通"));
 *         logger.log(Level.SEVERE, "严重的错误", new IOException("我就是错误"))
 *         logger.log(Level.WARNING, "警告的内容")
 *         logger.log(Level.INFO, "普通的信息")
 */
public class Test {

    static void test1(){
        // 首先获取日志打印器
        Logger logger = Logger.getLogger(Test.class.getName());
        // 调用info来输出一个普通的信息 直接填写字符串即可
        logger.info("我是普通的日志");
    }

    static void test2(){
        Logger logger = Logger.getLogger(Test.class.getName());
        logger.log(Level.SEVERE, "我是出现严重错误日志", new IOException("我就是错误"));
        logger.log(Level.WARNING, "警告的内容");
        logger.log(Level.INFO, "普通的信息");
        logger.log(Level.CONFIG, "级别低于普通信息");
    }

    static void test3(){
        Logger logger = Logger.getLogger(Test.class.getName());
        // 修改日志级别
        logger.setLevel(Level.ALL);
        // 不使用父日志处理器
        logger.setUseParentHandlers(false);
        // 使用自定义日志处理器
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);

        logger.log(Level.SEVERE, "我是出现严重错误日志", new IOException("我就是错误"));
        logger.log(Level.WARNING, "警告的内容");
        logger.log(Level.INFO, "普通的信息");
        logger.log(Level.CONFIG, "级别低于普通信息");
        logger.log(Level.FINE, "低级别日志信息");
        logger.log(Level.FINER, "较低级别详细信息");
        logger.log(Level.FINEST, "最低级别日志信息");
    }

    static void test4(){
        Logger logger = Logger.getLogger(Test.class.getName());
        System.out.println(logger.getParent().getClass());
    }

    static void test5() throws IOException{
        Logger logger = Logger.getLogger(Test.class.getName());
        // 修改日志级别
        logger.setLevel(Level.ALL);
        // 不使用父日志处理器
        logger.setUseParentHandlers(false);
        // 文件格式化器
        SimpleFormatter formatter = new SimpleFormatter();
        // 使用自定义日志处理器
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        //handler.setFormatter(new XMLFormatter());
        logger.addHandler(handler);

        // 添加输出到本地文件
        FileHandler fileHandler = new FileHandler("test.log");
        fileHandler.setLevel(Level.WARNING);
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);

        logger.log(Level.SEVERE, "我是出现严重错误日志", new IOException("我就是错误"));
        logger.log(Level.WARNING, "警告的内容");
        logger.log(Level.INFO, "普通的信息");
        logger.log(Level.CONFIG, "级别低于普通信息");
        logger.log(Level.FINE, "低级别日志信息");
        logger.log(Level.FINER, "较低级别详细信息");
        logger.log(Level.FINEST, "最低级别日志信息");
    }

    static void test6(){
        // 自定义过滤规则
        Logger logger = Logger.getLogger(Test.class.getName());
        //logger.setFilter(record -> false);
        logger.setFilter(record -> record.getMessage().equals("2"));
        logger.info("1");
        logger.info("2");
    }

    public static void main(String[] args) throws IOException {
        //test1();
        //test2();
        //test3();
        //test4();
        //test5();
        test6();
    }

}
