package Maven.maven;

import Maven.maven.mapper.TestMapper;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.*;

@Log
public class MainTest {

    // 因为配置文件位于内部 我们需要使用Resources类的getResourceAsString来获取内部的资源文件
    private static SqlSessionFactory factory;

    // 在JUnit5中@Before被废弃 它被细分了
    @BeforeAll // 一次性开启所有测试案例只会执行一次(方法必须是static)
    // @BeforeEach 一次性开启所有测试案例每个案例开始之前都会被执行一次
    @SneakyThrows
    public static void before(){
        factory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"));
    }

    @Test
    public void test1(){
        log.info("测试");

        // Assert在Junit5时名称发生了变化Assertions
        Assertions.assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3});
    }

    @DisplayName("Mybatis数据库测试") // 自定义测试名称
    // @RepeatedTest(3) // 自动执行多次测试
    public void test2(){
        try (SqlSession session = factory.openSession(true);){
            TestMapper mapper = session.getMapper(TestMapper.class);

            System.out.println(mapper.getStudentBySid(6));
        }
    }

}
