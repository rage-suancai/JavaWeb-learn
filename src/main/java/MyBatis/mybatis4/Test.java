package MyBatis.mybatis4;

import MyBatis.mybatis4.entity.Student;
import MyBatis.mybatis4.mapper.TestMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * Mybatis数据库 增删改查
 * 在了解了Mybatis的一些基本配置后 我们就可以正式的来使用Mybatis来进行数据库操作了
 *
 * 在前面我们演示了如何快速进行查询 我们只需要编写一个对应的映射器就可以了 当然 如果你不喜欢使用实体类
 * 那么这些属性还可以被映射到一个Map上 Map中就会以键值对的形式来存放这些结果了
 *
 * 通过设定一个resultType属性 让Mybatis知道查询结果需要映射为哪个实体类 要求字段名称保持一致
 * 那么如果我们不希望按照这样的规则来映射呢 我们可以自定义resultMap来设定映射规则:
 *      <resultMap id="Test" type="MyBatis.mybatis4.entity.Student">
 *          <result column="id" property="xxx"/>
 *          <result column="sex" property="name"/>
 *          <result column="name" property="sex"/>
 *      </resultMap>
 * 通过指定映射规则 我们现在名称和性别一栏就发生了交换 因为我们将其映射字段进行了交换
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
