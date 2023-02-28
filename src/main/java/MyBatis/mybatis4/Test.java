package MyBatis.mybatis4;

import MyBatis.mybatis4.entity.Student;
import MyBatis.mybatis4.mapper.TestMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * Mybatis数据库 增删改查
 * 在了解了Mybatis的一些基本配置后 我们就可以正式的来使用Mybatis来进行数据库操作了
 *
 * 在前面我们演示了如何快速进行查询 我们只需要编写一个对应的映射器就可以了:
 *
 *                  <mapper namespace="MyBatis.mybatis4.mapper.TestMapper">
 *                      <select id="selectStudent" resultType="Student">
 *                          select * from student
 *                      </select>
 *                  </mapper>
 *
 * 当然 如果你不喜欢使用实体类 那么这些属性还可以被映射到一个Map上
 *
 *                  <select id="selectStudent" resultType="Map">
 *                      select * from student
 *                  </select>
 *
 *                  public interface TestMapper {
 *                      List<Map> selectStudent();
 *                  }
 *
 * Map中就会以键值对的形式来存放这些结果了
 *
 * 通过设定一个resultType属性 让Mybatis知道查询结果需要映射为哪个实体类 要求字段名称保持一致 那么如果我们不希望按照这样的规则来映射呢 我们可以自定义resultMap来设定映射规则:
 *
 *                  <resultMap id="Test" type="MyBatis.mybatis4.entity.Student">
 *                      <result column="id" property="xxx"/>
 *                      <result column="sex" property="name"/>
 *                      <result column="name" property="sex"/>
 *                  </resultMap>
 *
 * 通过指定映射规则 我们现在名称和性别一栏就发生了交换 因为我们将其映射字段进行了交换
 *
 * 如果一个类中存在多个构造方法 那么很有可能会出现这样的错误:
 *
 *                  ### Error querying database.  Cause: org.apache.ibatis.executor.ExecutorException: No constructor found
 *                  ### The error may exist in MyBatis/mybatis4/mapper/TestMapper.xml
 *                  ### The error may involve MyBatis.mybatis4.mapper.TestMapper.selectStudent
 *                  ### The error occurred while handling results
 *                  ### SQL: select * from student
 *                  ### Cause: org.apache.ibatis.executor.ExecutorException: No constructor found in MyBatis.mybatis4.entity.Student
 *
 * 这时就需要使用constructor标签来指定构造方法:
 *
 *              <resultMap id="test" type="Student">
 *                  <constructor>
 *                       <arg column="id" javaType="Integer"/>
 *                       <arg column="name" javaType="String"/>
 *                  </constructor>
 *              </resultMap>
 *
 * 值得注意的是 指定构造方法后 若此字段被填入了构造方法作为参数 将不会通过反射给字段单独赋值 而构造方法中没有传入的字段 依然被反射赋值 有关resultMap的内容 后面还会继续讲解
 *
 * 如果数据库中存在一个带下划线的字段 我们可以通过设置让其映射为以驼峰命名的字段 比如 my_test 映射为 myTest:
 *
 *              <settings>
 *                  <setting name="mapUnderscoreToCamelCase" value="true"/>
 *              </settings>
 *
 * 如果不设置 默认为不开启 也就是默认需要名称保持一致
 *
 * 我们接着来看看条件查询 既然是条件查询 那么肯定需要我们传入查询条件 比如现在我们想通过id字段来通过学号查找信息:
 *
 *              Student getStudentById(int id);
 *
 *              <select id="getStudentById" parameterType="int" resultType="Student">
 *                  select * from student where id = #{id}
 *              </select>
 *
 * 我们通过使用 #{xxx} 或是 ${xxx} 来填入我们给定的属性 实际上Mybatis本质也是通过PreparedStatement首先进行一次预编译
 * 有效的防止SQL注入问题 但是如果使用${xxx}就不再是通过预编译 而是直接传递 因此我们一般都是用#{xxx}来进行操作
 *
 * 使用parameterType属性来指定参数类型(非必须 可以不用 推荐不用)
 *
 * 接着我们来看插入 更新和删除操作 其实与查询操作差不多 不过需要使用对应的标签 比如插入操作:
 *
 *              <insert id="addStudent" parameterType="Student">
 *                  insert into student(name, sex) values(#{name}, #{sex})
 *              </insert>
 *
 *              int addStudent(Student student);
 *
 * 我们这里使用的是一个实体类 我们可以直接使用实体类里面对应属性替换到SQL语句中 只需要填写属性名称即可 和条件查询是一样的
 */
public class Test {

    public static void main(String[] args) {

        try (SqlSession session = MybatisUtil.getSession(true)) {

            TestMapper mapper = session.getMapper(TestMapper.class);
            /*System.out.println(mapper.selectStudent());
            System.out.println(mapper.getStudentById(3));*/

            /*System.out.println(mapper.addStudent(new Student().setName("帅哥杰克").setSex("男")));
            System.out.println(mapper.selectStudent());*/

            /*System.out.println(mapper.deleteStudent(4));
            System.out.println(mapper.selectStudent());*/

            System.out.println(mapper.updateStudent(new Student().setName("血腥玛丽").setSex("女").setId(5)));
            System.out.println(mapper.selectStudent());

        }

    }

}
