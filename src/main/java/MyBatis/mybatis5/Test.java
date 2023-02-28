package MyBatis.mybatis5;

import MyBatis.mybatis5.mapper.TestMapper;

/**
 * Mybatis复杂查询和事务操作
 *
 * 映射为Teacher对象时 同时将其教授的所有学生一并映射为List列表 显然这是一种一对多的查询 那么这时就需要进行复杂查询
 * 而我们之前编写的都非常简单 直接就完成映射 因此我们现在需要使用resultMap来自定义映射规则:
 *
 *      <select id="getTeacherById" resultMap="asTeacher">
 *          select *, teacher.name as tname from student inner join teach on student.sid = teach.sid
 *                                inner join teacher on teach.tid = teacher.tid where teach.tid = #{tid}
 *      </select>
 *
 *      <resultMap id="asTeacher" type="Teacher">
 *         <id column="tid" property="tid"/>
 *         <result column="tname" property="name"/>
 *         <collection property="studentList" ofType="Student">
 *             <id property="sid" column="sid"/>
 *             <result column="name" property="name"/>
 *             <result column="sex" property="sex"/>
 *         </collection>
 *     </resultMap>
 *
 * 可以看到 我们的查询结果是一个多表联查的结果 而联查的数据就是我们需要映射的数据(比如一个老师有N个学生 联查的结果也是这一个老师对应N个学生的N条记录)
 * 其中id标签用于在多条记录中辨别是否为同一个对象的数据 比如上面的查询语句得到的结果中 tid这一行始终为1 因此所有的记录应该是 tid=1 的教授的数据
 * 而不应该变为多个教授的数据 如果不加id进行约束 那么会被识别成多个教师的数据
 *
 * 通过使用collection来表示将得到的所有结果合并为一个集合 比如上面的数据中每个学生都有单独的一条记录 因此tid相同的全部学生的记录就可以最后合并为一个List
 * 得到最终的映射结果 当然 为了区分 最好也设置一个id 只不过这个例子中可以当做普通的result使用
 *
 * 了解了一对多 那么多对一又该如何查询呢 比如每个人学生都有一个对应的老师 现在Student新增了一个Teacher对象 那么现在有该如何去处理呢:
 *
 *      public class Student {
 *          int sid;
 *          Sting name;
 *          String sex;
 *          Teacher teacher;
 *      }
 *
 *      public class Teacher {
 *          int tid;
 *          String name;
 *      }
 *
 * 现在我们希望的是 每次查询到一个Student对象时都带上它的老师 同样的 我们也可以使用resultMap来实现(先修改一下老师的类定义 不然会很麻烦):
 *
 *      <select id="selectStudent" resultMap="test2">
 *          select *, teacher.name as tname from student left join teach on student.sid = teach.sid
 *                                                       left join teacher on teach.tid = teacher.tid
 *      </select>
 *
 *      <resultMap id="test2" type="Student">
 *          <id column="sid" property="sid"/>
 *          <result column="name" property="name"/>
 *          <result column="sex" property="sex"/>
 *          <association property="teacher" javaType="Teacher">
 *              <id column="tid" property="tid"/>
 *              <result column="tname" property="name"/>
 *          </association>
 *      </resultMap>
 *
 * 通过使用association进行关联 形成多对一的关系 实际上和一对多同理的 都是对查询结果的一种处理方式罢了
 *
 * 事务操作
 * 我们可以在获取SqlSession关闭自动提交来开启事务模式 和JDBC其实差不多:
 *
 *      try (SqlSession sqlSession = MybatisUtil.getSession(false)) {
 *          TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
 *
 *          testMapper.addStudent(new Student().setSex("男").setName("小王"));
 *
 *          testMapper.selectStudent().forEach(System.out.println);
 *      }
 */
public class Test {

    // 事务关闭
    static <SqlSession> void test1(){
        try (SqlSession session = MybatisUtil.getSession(true)) {
            TestMapper mapper = session.getMapper(TestMapper.class);
            System.out.println(mapper.getTeacherById(1));
            //mapper.selectStudent(1).forEach(System.out::println);
        }
    }

    // 开启事务
    static <SqlSession> void test2(){
        try (SqlSession session = MybatisUtil.getSession(false)){
            TestMapper mapper = session.getMapper(TestMapper.class);
            System.out.println(mapper.getTeacherById(1));

            session.rollback();
            session.commit();
        }
    }

    public static void main(String[] args) {
        //test1();
        test2();
    }

}
