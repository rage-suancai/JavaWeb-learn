package MyBatis.mybatis8;

import MyBatis.mybatis8.entity.Student;
import MyBatis.mybatis8.mapper.TestMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * Mybatis使用注解开发
 * 在之前的开发中 我们已经体验到Mybatis为我们带来的便捷了 我们只需要填写对应的映射器 并将其绑定到一个接口上 即可直接通过该接口执行我们的SQL语句
 * 极大的简化了我们之前JDBC那样的代码编写模式 那么 能否实现无需xml映射器配置 而是直接使用注解在接口是进行配置呢 答案是可以的 也是现在推荐的一种方式
 * (也不是说XML就不去用了 由于java注解的表达能力和灵活性十分有限 可能相对于XML配置某些功能实现起来会不太好办 但是在大部分场景下 直接使用注解开发已经绰绰有余了)
 *
 * 首先我们来看一下 使用XML进行映射器编写时 我们需要现在XML中定义映射规则和SQL语句 然后再将其绑定到一个接口的方法定义上 然后再使用接口来执行:
 *      <insert id="addStudent">
 *          insert into student(name, sex) values(#{name}, #{sex})
 *      </insert>
 *      int addStudent(Student student);
 *
 * 而现在 我们可以直接使用注解来实现 每个操作都有一个对应的注解:
 *      @Insert("insert into student(name, sex) values(#{name}, #{sex})")
 *      int addStudent(Student student);
 *
 * 当然 我们还需要修改一下配置文件中的映射器注册:
 *      <mappers>
 *          <mapper class="MyBatis.mybatis8.mapper.MyMapper"/>
 *          <!-- 也可以直接注册整个包下的 -->
 *          <package name="MyBatis.mybatis8.mapper"/>
 *      </mappers>
 * 通过直接指定Class 来让Mybatis知道我们这里有一个通过注解实现的映射器
 *
 * 我们接着来看一下 如何使用注解进行自定义映射规则:
 *      @Results({
 *            @Result(id = true, column = "sid", property = "sid"),
 *            @Result(id = true, column = "sex", property = "name"),
 *            @Result(id = true, column = "name", property = "sex")
 *      })
 *      @Select("select * from student")
 *      List<Student> getAllStudent
 * 直接通过@Results注解 就可以直接进行配置了 此注解的value是一个@Result注解数组 每个@Result注解都是一个单独的字段配置 其实就是我们之前在XML映射器中写的一样
 *
 * 现在我们就可以通过注解来自定义映射规则了 那么如何使用注解来完成复杂查询呢 我们还是使用一个老师多个学生的列子:
 *      @Results({
 *             @Result(column = "tid", property = "tid", id = true),
 *             @Result(column = "name", property = "name"),
 *             @Result(column = "tid", property = "studentList", many =
 *                 @Many(select = "getStudentByTid")
 *             )
 *     })
 *     @Select("select * from teacher where tid = #{tid}")
 *     Teacher getTeacherByTid(int tid);
 *
 *     @Select("select * from student inner join teach on student.sid = teach.sid where tid = #{tid}")
 *     List<Student> getStudentByTid(int tid);
 * 我们发现 多出了一个子查询 而这个子查询是单独查询该老师所属学生的信息 而子查询结果作为@Result注解的一个many结果 代表子查询的所有结果都归入此集合中(也就是之前的collection标签)
 *
 * 同理 @Result也提供了@One子注解来实现一对一的关系表示 类似于之前的assocation标签:
 *     @Results({
 *             @Result(column = "sid", property = "sid", id = true),
 *             @Result(column = "name", property = "name"),
 *             @Result(column = "sex", property = "sex"),
 *             @Result(column = "sid", property = "teacher", one =
 *                 @One(select = "getTeacherBySid")
 *             )
 *     })
 *     @Select("select * from student")
 *     List<Student> getAllStudent();
 *
 * 如果现在我希望直接使用注解编写SQL语句但是我希望映射规则依然使用XML来实现 这时该怎么办呢
 *     @ResultMap("test")
 *     @Select("select * from student")
 *     Lsst<Student> getAllStudent();
 * 提供了@ResultMap注解 直接指定ID即可 这样我们就可以使用XML中编写的映射规则了
 *
 * 那么如果出现之前的两个构造方法的情况 且没有任何一个构造方法匹配的话 该怎么处理呢
 *      public Student(int sid){
 *         System.out.println("我是一号构造方法" + sid);
 *     }
 *
 *     public Student(int sid, String name){
 *         System.out.println("我是二号构造方法" + sid + name);
 *     }
 * 我们可以通过@ConstructorArgs注解来指定构造方法:
 *      @ConstructorArgs({
 *             @Arg(column = "sid", javaType = int.class),
 *             @Arg(column = "name", javaType = String.class)
 *     })
 *      @Select("select * from student where sid = #{sid}")
 *      Student getStudentBySid(int sid);
 * 得到的结果和使用constructor标签效果一致 这里就不多做讲解了
 *
 * 我们发现 当参数列表中出现两个以上的参数时 会出现错误:
 *      @Select("select * from student where sid = #{sid} and sex = #{sex}")
 *      Student getStudentBySidAndSex(int sid, String sex);
 *
 * 原因是Mybatis不明确到底哪个参数是上面 因此我们可以添加@Param来指定参数名称:
 *      @Select("select * from student where sid = #{sid} and sex = #{sex}")
 *      Student getStudentBySidAndSex(@Param("sid") int sid, @Param("sex") String sex);
 *
 * 探究: 要是我两个参数一个是基本类型一个是对象类型呢
 *      System.out.println(mapper.addStudentAndSid(888, new Student().setName("唐纳德").setSex("未知")));
 *
 *      @Insert("insert into student(sid, name, sex) values(#{sid}, #{name}, #{sex})")
 *      int addStudent(@Param("sid") int sid, @Param("student") Student student);
 * 那么这个时候 就出现问题了 Mybatis就不能明确这些属性是从哪里来的:
 *      ### SQL: insert into student(sid, name, sex) values(?, ?, ?)
 *      ### Cause: org.apache.ibatis.binding.BindingException: Parameter 'name' not found. Available parameters
 *      are [student, param1, sid, param2]
 * 那么我们就通过参数名称.属性的方法去让Mybatis知道我们要用的是哪个属性:
 *      @Insert("insert into student(sid, name, sex) values(#{sid}, #{student.name}, #{student.sex})")
 *
 * 那么如何通过注解控制缓存机制呢
 *      @CacheNamespace(readWrite = false)
 *      public interface MyMapper {
 *          @Select("select * from student")
 *          @Options(useCache = false)
 *          List<Student> getAllStudent();
 *      }
 * 使用@CacheNamespace注解直接定义在接口上即可 然后我们可以通过使用@Options来控制单个操作的缓存启用
 */
public class Test {

    static void test1(){
        try (SqlSession session = MybatisUtil.getSession(true)) {
            TestMapper mapper = session.getMapper(TestMapper.class);
            //System.out.println(mapper.getStudentBySid(6));
            //mapper.addStudent(new Student().setName("佐助").setSex("男"));

            /*System.out.println(mapper.getTeacherByTid(1));
            System.out.println(mapper.getStudentByTid(1));*/

            /*System.out.println(mapper.getStudentBySid(1));
            System.out.println(mapper.getTeacherBySid(1));*/

            //System.out.println(mapper.getStudentBySidAndSex(6, "男"));

            System.out.println(mapper.addStudentAndSid(888, new Student().setName("唐纳德").setSex("未知")));
        }
    }

    public static void main(String[] args) {
        test1();
    }

}
