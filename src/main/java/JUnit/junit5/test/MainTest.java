package JUnit.junit5.test;

import JUnit.junit5.mapper.TestMapper;
import lombok.extern.java.Log;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Mybatis日志系统
 * Mybatis也有日志系统 它详细记录了所有的数据库操作等 但是我们在前面的学习中没有开启它 现在我们学习了日志之后 我们就可以尝试开启Mybatis的日志系统
 *              <setting name="logImpl" value="STDOUT_LOGGING" />
 *
 * logImpl包括很多种配置项 包括SLF4J|LOG4J|LOG4J2|JDK_LOGGING|COMMONS_LOGGING|STDOUT_LOGGING|NO_LOGGING 而默认情况下是未配置 也就是说不打印
 * 我们这里将其设定为STDOUT_LOGGING表示直接使用标准输出将日志信息打印到控制台 我们编写一个测试案例来看看效:
 *              @Test
 *              public void method(){
 *              try (SqlSession session = sqlSessionFactory.openSession(true)) {
 *                   TestMapper mapper = session.getMapper(TestMapper.class);
 *
 *                   mapper.getStudentBySidAndSex(6, "女");
 *                   mapper.getStudentBySidAndSex(7, "男");
 *                  }
 *              }
 * 我们发现 两次获取学生信息 两次都打开了数据库连接 这就是前面我们学习的一级缓存和二级缓存问题
 *
 * 现在我们学习了日志系统 那么我们来测试使用日志系统输出Mybatis的日志信息:
 *              <setting name="logImpl" value="JDK_LOGGING" />
 * 将其配置为JDK_LOGGING表示使用JUL进行日志打印 因为Mybatis的日志级别都比较低 因此我们需要设置一下logging.properties默认的日志级别:
 *              handlers= java.util.logging.ConsoleHandler
 *              .level= ALL
 *              java.util.logging.ConsoleHandler.level = ALL
 * 代码编写如下:
 *              try (SqlSession session = sqlSessionFactory.openSession(true)) {
 *                   TestMapper mapper = session.getMapper(TestMapper.class);
 *
 *                   log.info(mapper.getStudentBySidAndSex(6, "女").toString());
 *                   log.info(mapper.getStudentBySidAndSex(7, "男").toString());
 *              }
 *
 *
 */
@Log
public class MainTest {

    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void before(){
        System.out.println("测试前置正在初使化...");
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder()
                    .build(new FileInputStream("mybatis-config.xml"));
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        System.out.println("初始化完成 正在开始测试案例...");
    }

    @Test
    public void method1(){
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            TestMapper mapper = session.getMapper(TestMapper.class);

            mapper.getStudentBySidAndSex(6, "女");
            mapper.getStudentBySidAndSex(7, "男");
        }
    }

    @Test
    public void method2(){
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            TestMapper mapper = session.getMapper(TestMapper.class);

            log.info(mapper.getStudentBySidAndSex(6, "女").toString());
            log.info(mapper.getStudentBySidAndSex(7, "男").toString());
        }
    }

    @After
    public void ofter(){
        System.out.println("测试结束 收尾工作正在进行...");
    }
}