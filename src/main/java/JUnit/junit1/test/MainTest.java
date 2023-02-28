package JUnit.junit1.test;

import JUnit.junit1.MybatisUtil;
import JUnit.junit1.entity.Student;
import JUnit.junit1.mapper.TestMapper;
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

    /**
     * 那么如果我们在进行所有的测试之前需要做一些前置操作该怎么办呢 一种办法是在所有的测试用例前面都加上前置操作 但是这样显然是很冗余的
     * 因为一旦发生修改就需要挨个进行修改 因此我们需要更加智能的方法 我们可以通过 @Before 注解来添加测试用例开始之前的前置操作
     *
     * 同理 在所有的测试完成之后 我们还想添加一个收尾的动作 那么只需要使用@After注解即可添加结束动作
     *
     *      @After
     *      public void ofter(){
     *          System.out.println("测试结束 收尾工作正在进行...");
     *      }
     */
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

    /**
     * 现在我们创建一个新的类 来编写我们的单元测试用例
     *
     * 我们可以点击类前面的测试按钮 或是单个方法前的测试按钮 如果点击类前面的测试按钮 会执行所有的测试用例
     *
     * 运行测试后 我们发现控制台得到了一个测试结果 显示为绿色表示测试通过
     *
     * 只需要通过打上 @Test 注解 即可将一个方法标记为测试案例 我们可以直接运行此测试案例 但是我们编写的测试方法有以下要求:
     *
     *      > 方法必须是public的
     *      > 不能是静态方法
     *      > 返回值必须是void
     *      > 必须是没有任何参数的方法
     *
     * 对于一个测试案例来说 我们肯定希望测试的结果是我们所期望的一个值 因此 如果测试的结果并不是我们所期望的结果 那么这个测试就应该没有成功通过
     *
     * 我们可以通过断言工具来进行判定:
     *      Assert.assertEquals(1, 2);
     */
    @Test
    public void method1(){
        System.out.println("我是测试用例1");
        Assert.assertEquals("断言表达式", 1, 2); // 参数1是期盼值 参数2是实际测试结果值
    }

    /**
     * 通过运行代码后 我们发现测试过程中抛出了一个错误 并且IDEA给我们显示了期盼结果和测试结果 那么现在我们来测试一个案例 比如我们想查看冒泡排序的编写是否正确:
     *
     *      for (int i = 0; i < arr.length - 1; ++i) {
     *             for (int j = 0; j < arr.length - 1 - i; ++j) {
     *                 if(arr[j] > arr[j + 1]) {
     *                     int tmp = arr[j];
     *                     arr[j] = arr[j+1];
     *                     //arr[j+1] = tmp;
     *                 }
     *             }
     *         }
     *
     * 通过测试 我们发现得到的结果并不是我们想要的结果 因此现在我们需要去修改为正确的冒泡排序 修改后 测试就能正确通过了
     */
    @Test
    public void method2(){
        int[] arr = {0, 4, 5, 2, 6, 9, 3, 1, 7, 8};

        for (int i = 0; i < arr.length - 1; ++i) {
            for (int j = 0; j < arr.length - 1 - i; ++j) {
                if(arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
        Assert.assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, arr);
    }

    /**
     * 我们还可以再通过一个案例更加深入地了解测试 现在我们想测试从数据库中读取数据是否为我们预期的数据
     */
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