package JUnit.junit2.test;

import JUnit.junit1.entity.Student;
import JUnit.junit1.mapper.TestMapper;
import JUnit.junit2.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

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
    public void method3(){
        try (SqlSession session = MybatisUtil.getSession(true)) {
            TestMapper mapper = session.getMapper(TestMapper.class);

            Student student = mapper.getStudentBySidAndSex(6, "女");
            Assert.assertEquals(new Student().setName("春丽").setSex("女").setSid(6), student);
        }
    }

    @After
    public void ofter(){
        System.out.println("测试结束 收尾工作正在进行...");
    }
}