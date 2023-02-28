package Maven.maven2;

import Maven.maven2.mapper.TestMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * Maven依赖的作用域
 * 除了三个基本的属性用于定位坐标外 依赖还可以添加以下属性:
 *
 *      > type 依赖的功能 对于项目坐标定义的packaging 大部分情况下 该元素不必声明 其默认值为jar
 *      > scope 依赖的范围(作用域 着重讲解)
 *      > optional 标记依赖是否可选
 *      > exclusions 用来排除传递性依赖(一个项目有可能依赖于其他项目 就像我们的项目 如果别人要用我们的项目作为依赖 那么 就需要一起下载我们项目的依赖 如Lombok)
 *
 * 我们着重讲解一下scope属性 它决定了依赖的作用域范围:
 *
 *      > compile 为默认的依赖有效范围 如果在定义依赖关系的时后 没有明确指定依赖有效范围的话 则默认采用该依赖有效范围
 *                此种依赖 在编译 运行 测试时均有生效
 *      > provided 在编译 测试时有效 但是在运行时无效 也就是说项目在运行时 不需要此依赖比如我们声上面的Lombok 我们只需要在编译阶段使用它
 *                  编译完成后 实际上已经转换为对应的代码了 因此Lombok不需要在项目运行时也存在
 *      > runtime 在运行 测试时有效 但是在编译代码时无效 比如我们如果需要自己写一个JDBC实现 那么肯定要用到JDK为我们指定接口
 *                  但是实际上在运行时不用自带JDK的依赖 因此只保留我们自己写的内容即可
 *      > test 只在测试时有效 例如 JUnit 我们一般只会在测试阶段使用JUnit 而实际项目运行时 我们就用不到测试了 那么我们来看看 导入JUnit的依赖
 *
 * 同样的 我们可以在网站上搜索JUnit的依赖 我们这里导入最新的JUnit5作为依赖:
 *
 *                  <dependency>
 *                      <groupId>org.junit.jupiter</groupId>
 *                      <artifactId>junit-jupiter</artifactId>
 *                      <version>5.8.1</version>
 *                      <scope>test</scope>
 *                  </dependency>
 *
 * 我们所有的测试用例全部编写到Maven项目给我们划分的test目录下 位于此目录下的内容不会在最后被打包到项目中 只用作开发阶段测试使用:
 *
 *                  @Test
 *                  public void test1(){
 *                      log.info("测试");
 *
 *                      // Assert在Junit5时名称发生了变化Assertions
 *                      Assertions.assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2});
 *                  }
 *
 * 因此 一般仅用作测试的依赖如JUnit只保留在测试中即可 那么现在我们再来添加JDBC和Mybatis的依赖:
 *
 *                  <dependency>
 *                      <groupId>mysql</groupId>
 *                      <artifactId>mysql-connector-java</artifactId>
 *                      <version>5.1.43</version>
 *                  </dependency>
 *                  <dependency>
 *                      <groupId>org.mybatis</groupId>
 *                      <artifactId>mybatis</artifactId>
 *                      <version>3.5.7</version>
 *                  </dependency>
 *
 * 我们发现 Maven还给我们提供了一个resource文件夹 我们可以将一些静态资源 比如配置文件 放入到这个文件夹中 项目在打包时会将资源文件夹中文件一起打包到Jar中
 *
 * 现在我们创建一下测试用例 顺便带大家了解一下JUnit5的一些比较方便的地方:
 *
 *                  // 因为配置文件位于内部 我们需要使用Resources类的getResourceAsString来获取内部的资源文件
 *                  private static SqlSessionFactory factory;
 *
 *                  // 在JUnit5中@Before被废弃 它被细分了
 *                  @BeforeAll // 一次性开启所有测试案例只会执行一次(方法必须是static)
 *                  // @BeforeEach 一次性开启所有测试案例每个案例开始之前都会被执行一次
 *                  @SneakyThrows
 *                  public static void before(){
 *                      factory = new SqlSessionFactoryBuilder()
 *                              .build(Resources.getResourceAsStream("mybatis-config.xml"));
 *                  }
 *
 *                  @DisplayName("Mybatis数据库测试") // 自定义测试名称
 *                  // @RepeatedTest(3) // 自动执行多次测试
 *                  public void test2(){
 *                  try (SqlSession session = factory.openSession(true);){
 *                      TestMapper mapper = session.getMapper(TestMapper.class);
 *
 *                      System.out.println(mapper.getStudentBySid(6));
 *                      }
 *                  }
 *
 * 那么就有人问了 如果我需要的依赖没有上传的远程仓库 而是只有一个jar怎么办呢 我们可以使用第四种作用域:
 *      > system 作用域和provided是一样的 但是它不是从远程仓库获取 而是直接导入本地jar包:
 *
 *                  <dependency>
 *                      <groupId>javax.jntm</groupId>
 *                      <artifactId>yxsnb</artifactId>
 *                      <version>2.0</version>
 *                      <scope>system</scope>
 *                      <systemPath>D://xxxx/xxxx/test.jar</systemPath>
 *                  </dependency>
 *
 * 比如上面的例子 如果scope为system 那么我们需要添加systemPath来指定jar文件的位置 这里就不再演示了
 */
public class Test {

    static void test1() {
        try(SqlSession session = MybatisUtil.getSession(true);) {
            TestMapper mapper = session.getMapper(TestMapper.class);
            System.out.println(mapper.getStudentBySid(6));
        }
    }

    public static void main(String[] args){
        test1();
    }

}
