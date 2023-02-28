package MyBatis.mybatis3;

import MyBatis.mybatis3.mapper.TestMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * 配置Mybatis
 * 在了解了Mybatis为我们带来的便捷之后 现在我们就可以正式的去学习使用Mybatis了
 *
 * 由于SqlSessionFactory一般只需要创建一次 因此我们可以创建一个工具类来集中创建SqlSession 这样会更加方便一些:
 *
 *                  public class MybatisUtil {
 *
 *                      // 在类加载时就进行创建
 *                      private static SqlSessionFactory sqlSessionFactory;
 *                      static {
 *                          try {
 *                              sqlSessionFactory = new SqlSessionFactoryBuilder().build(new FileInputStream("mybatis-config.xml"));
 *                          } catch (FileNotFoundException e) {
 *                              e.printStackTrace();
 *                          }
 *                      }
 *
 *                      // 获取一个新的会话
 *                      // 是否开启自动提交(跟JDBC是一样的 如果不自动提交 则会变成事务操作)
 *                      public static SqlSession getSession(boolean autoCommit) {
 *                          return sqlSessionFactory.openSession(autoCommit);
 *                      }
 *
 *                  }
 *
 * 现在我们只需要在main方法中这样写即可查询结果了:
 *
 *                  public static void main(String[] args) {
 *
 *                      try (SqlSession sqlSession = MybatisUtil.getSession(true)) {
 *                          List<Student> student = sqlSession.selectList("selectStudent");
 *                          student.forEach(System.out::println);
 *                      }
 *
 *                  }
 *
 * 之前我们演示了 如何创建一个映射器来将结果快速转换为实体类 但是这样可能还是不够方便 我们每次都需要去映射器对应操作的名称
 * 而且还要知道对应的返回类型 再通过SqlSession来执行对应的方法 能不能再方便一点呢?
 *
 * 现在 我们可以通过namespace来绑定到一个接口上 利用接口的特性 我们可以直接指明方法的行为 而实际实现则是由Mybatis来完成:
 *
 *                  public interface TestMapper {
 *                      List<Student> selectStudent();
 *                  }
 *
 * 将Mapper文件的命名空间修改为我们的接口 建议同时将其放到同名包中 作为内部资源:
 *
 *                  <mapper namespace="MyBatis.mybatis3.mapper.TestMapper">
 *                      <select id="selectStudent" resultType="MyBatis.mybatis3.entity.Student">
 *                          select * from student
 *                      </select>
 *                  </mapper>
 *
 * 作为内部资源后 我们需要修改一下配置文件中的mapper定义 不使用url而是resource表示是Jar内部的文件:
 *
 *                  <mappers>
 *                      <mapper resource="MyBatis/mapper/TestsMapper.xml"/>
 *                  </mappers>
 *
 * 现在我们就可以直接通过SqlSession获取对应的实现类 通过接口定义的行为来直接获取结果:
 *
 *                  public static void main(String[] args) {
 *
 *                      try (SqlSession sqlSession = MybatisUtil.getSession(true)) {
 *                          TestMapper testMapper = sqlSession.getMapper(testMapper.class);
 *                          List<Student> student = testMapper.selectStudent();
 *                          student.forEach(System.out::println);
 *                      }
 *
 *                  }
 *
 * 那么肯定有人好奇 TestMapper明明是一个我们自己定义的接口 Mybatis也不可能提前帮我们写了实现类
 * 那这接口怎么就出现了一个实现类呢 我们可以通过调用getClass()方法来看看实现了什么
 *
 *                  TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
 *                  System.out.println(testMapper.getClass());
 *
 * 我们发现 实现类名称很奇怪 名称为com.sun.proxy.$Proxy4 它是通过动态代理生成的 相当于动态生成了一个实现类 而不是预先定义好的 有关Mybatis这一部分的原理 我们最后在说
 *
 * 接下来 我们再来看看配置文件 之前我们并没有对配置文件进行详细介绍:
 *
 *                  <configuration>
 *                      <environments default="development">
 *                          <environment id="development">
 *                              <transactionManager type="JDBC"/>
 *                              <dataSource type="POOLED">
 *                                  <property name="driver" value="com.mysql.jdbc.Driver"/>
 *                                  <property name="url" value="jdbc:mysql://localhost:3306/study"/>
 *                                  <property name="username" value="root"/>
 *                                  <property name="password" value="123456"/>
 *                              </dataSource>
 *                          </environment>
 *                      </environments>
 *                      <mappers>
 *                          <package name="Maven.maven.mapper"/>
 *                      </mappers>
 *                  </configuration>
 *
 * 首先就从environments标签说起 一般情况下 我们在开发中 都需要指定一个数据库的配置信息 包含连接URL 用户 密码等信息
 * 而environment就是用于进行这些配置的 实际情况下可能会不止一个数据库连接信息 比如开发过程中我们一般会使用本地的数据库
 * 而如果需要将项目上传到服务器或是其他人电脑上运行时 我们可能就需配置另一个数据库的信息 因此 我们可以提前定义好所有的数据库信息 该什么时候用什么即可
 *
 * 在environments标签上有一个default属性 来指定默认的环境 当然如果我们希望使用其他环境 可以修改这个默认环境 也可以在创建工厂时选择环境:
 *
 *                  sqlSessionFactory = new SqlSessionFactoryBuilder()
 *                           .build(new FileInputStream("mybatis-config.xml"), "环境ID");
 *
 * 我们还可以给类型起一个别名 以简化Mapper的内容:
 *
 *                  <!-- 需要在environments的上方 -->
 *                  <typeAliases>
 *                      <typeAlias type="MyBatis.mybatis3.entity.Student" alias="Student"/>
 *                  </typeAliases>
 *
 * 现在Mapper就可以直接使用别名了:
 *
 *                  <mapper namespace="MyBatis.mybatis3.mapper.TestMapper">
 *                      <select id="selectStudent" resultType="Student">
 *                          select * from student
 *                      </select>
 *                  </mapper>
 *
 * 如果这样还是很麻烦 我们也可以直接让Mybatis去扫描一个包 并将包下的所有类自动起别名(别名为首字母小写的类名):
 *
 *                  <typeAliases>
 *                      <package name="MyBatis.mybatis3.entity"/>
 *                  </typeAliases>
 *
 *也可以为指定实体类添加一个注解 来指定别名:
 *
 *                  @Alias("student")
 *
 *当然 Mybatis也包含许多的基础配置 通过使用:
 *
 *                  <settings>
 *                      <setting name="" value=""/>
 *                  </settings>
 *
 * 所有的配置项可以在中文文档外处查询 本文不会进行详细介绍 在后面我们会提出一些比较重要的配置项
 *
 * 有关配置文件的介绍就暂时到这里为止 我们讨论的重心应该是Mybatis的应用 而不是配置文件 所以省略了一部分内容的讲解
 */
public class Test {

    public static void main(String[] args) {

        try (SqlSession session = MybatisUtil.getSession(true)) {

            TestMapper mapper = session.getMapper(TestMapper.class);
            System.out.println(mapper.getClass());
            mapper.selectStudent().forEach(System.out::println);

        }

    }

}
