package MyBatis.mybatis6;

import MyBatis.mybatis6.mapper.TestMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * Mybatis动态SQL
 * 动态sql是Mybatis的强大特性之一 如果你使用过jdbc或其他类似的框架 你应该能理解根据不同条件拼接sql有多痛苦
 * 例如拼接时要确保不能忘记添加必要的空格 还要注意去掉列表最后一个列名的逗号 利用动态sql 可以彻底摆脱这种痛苦
 *
 * 我们直接使用官网例子讲解:
 * choose when otherwise
 * 有时候 我们不想使用所有条件 而只是想从条件中选择一个使用 针对这种情况 Mybatis提供了choose元素 它有点像java中的switch语句
 * 还是上面的列子 但是策略变为: 传入了"title"就按"title"查找 传入了"author"就按"author"查找的情形
 * 若两者都没有传入 就返回标记为featured的BLOG(这可能是管理员认为 与其返回大量的无意义随机BLOG 还不如返回一些由管理员精选的BLOG)
 *
 * trim where set
 * 前面几个例子已经方便地解决了一个臭名昭著动态sql问题 现在回到之前的 'ir示列 这次我们将"state = 'ACTIVE'"设置成动态条件 看看会发生什么
 */
public class Test {

    // 事务关闭
    static void test1(){
        try (SqlSession session = MybatisUtil.getSession(true)) {
            TestMapper mapper = session.getMapper(TestMapper.class);
            System.out.println(mapper.getStudentBySid(7));
            //mapper.selectStudent(1).forEach(System.out::println);
        }
    }

    // 开启事务
    static void test2(){
        try (SqlSession session = MybatisUtil.getSession(false)){
            TestMapper mapper = session.getMapper(TestMapper.class);
            System.out.println(mapper.getTeacherById(1));

            session.rollback();
            session.commit();
        }
    }

    public static void main(String[] args) {
        test1();
        //test2();
    }

}
