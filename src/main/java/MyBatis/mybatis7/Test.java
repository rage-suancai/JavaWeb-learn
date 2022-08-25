package MyBatis.mybatis7;

import MyBatis.mybatis7.entity.Student;
import MyBatis.mybatis7.mapper.TestMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * Mybatis缓存机制
 * Mybatis内置了一个强大的事务性查询缓存机制 它可以非常方便地配置和定制
 *
 * 其实缓存机制我们在之前学习IO流的时候已经提及过了 我们可以提前将一部分内容放入缓存 下次需要获取数据时 我们就可以直接从缓存中读取
 * 这样的话相当于直接从内存中获取而不是再去向数据索取要的数据 效率会更高
 * 因此Mybatis内置了一个缓存机制 我们查询结果时 如果缓存中存在数据 那么我们就可以直接从缓存中获取 而不是再去向数据库进行请求
 *
 * Mybatis存在一级缓存和二级缓存 我们首先来看一下一级缓存 默认情况下 只启用了本地的会话缓存 它仅仅对一个会话中的数据进行缓存(一级缓存无法关闭 只能调整) 我们来看看下面这段代码:
 *      try (SqlSession session = MybatisUtil.geSession(true)) {
 *          TestMapper mapper = session.getMapper(TestMapper.class);
 *          Student student1 = testMapper.getStudentBySid(1);
 *          Student student2 = testMapper.getStudentBySid(2);
 *          System.out.println(student1 == student2);
 *      }
 * 我们发现 两次得到的都是同一个Student对象 也就是说我们第二次查询并没有重新去构造对象 而是直接得到之前创建好的对象 如果还不是很明显 我们可以修改一下实体类:
 *      public Student(){
 *         System.out.println("我被构造了");
 *     }
 * 我们通过前面的学习得知Mybatis再映射为对象时 在只有一个构造方法的情况下 无论你构造方法写成什么样 都会去调用一次构造方法
 * 如果存在多个构造方法 那么就会去找匹配的构造方法 我们可以通过查看构造方法来验证对象被创建了几次
 *
 * 结果显而易见 只创建了一次 也就是说当第二次进行同样的查询时 会直接使用第一次的结果 因此第一次的结果已经被缓存了
 *
 * 那么如果我们修改了数据库中的内容 缓存还会生效吗:
 *      try (SqlSession Session = MybatisUtil.getSession(true)) {
 *          TestMapper mapper = session.getMapper(TestMapper.class);
 *          Student student1 = mapper.getStudentBySid(1);
 *          mapper.addStudent(new Student().setName("xxx").setSex("x"));
 *          Student student2 = mapper.getStudentBySid(1);
 *          System.out.println(student1 == student2);
 *      }
 * 我们发现 当我们进行了插入操作后 缓存就没有生效了 我们再次进行查询得到的是一个新创建的对象
 *
 * 也就是说 一级缓存 在进行DML操作后 会使得缓存失败 也就是说Mybatis知道我们对数据库里面的数据进行了修改 所有之前缓存的内容可能就不是当前数据库里面最新的内容了
 * 还有一种情况就是 当前会话结束后 也会清理全部的缓存 因此已经不会再用到了 但是一定注意 一级缓存只针对与单个会话 多个会话之间不相通
 */
public class Test {

    static void test1(){
        try (SqlSession session = MybatisUtil.getSession(true)) {
            TestMapper mapper = session.getMapper(TestMapper.class);
            Student student1 = mapper.getStudentBySid(1);
            mapper.addStudent(new Student().setName("佐助").setSex("男"));
            Student student2 = mapper.getStudentBySid(1);
            //System.out.println(student2.toString());
            System.out.println(student1 == student2);
        }
    }

    static void test2(){
        /**
         * 注意 一个会话的DML操作只会重置当前会话的缓存 不会重置其他会话的缓存 也就是说 其他会话缓存是不会更新的
         *
         * 一级缓存给我们提供了高速的访问效率 但是它的作用范围实在是有限 如果一个会话结束 那么之前的缓存就全部失效了 但是我们希望缓存能够扩展到所有会话都能使用
         * 因此我们可以通过二级缓存来实现 二级缓存默认是关闭状态 要开启二级缓存 我们需要在映射器XML文件中添加: <cache/>
         *
         * 可见二级缓存是Mapper级别的 也就是说 当一个会话失败时 它的缓存依然还存在于二级缓存中 因此如果我们再次创建一个新的会话会直接使用之前的缓存 我们首先根据官方文档进行一些配置:
         *      <cache>
         *          eviction="FIFO"
         *          flushInterval="60000"
         *          size="512"
         *          readOnly="true"/>
         */
        /*try (SqlSession session = MybatisUtil.getSession(true)) {
            TestMapper mapper = session.getMapper(TestMapper.class);

            Student student2;
            try (SqlSession Session2 = MybatisUtil.getSession(true)) {
                TestMapper mapper2 = Session2.getMapper(TestMapper.class);
                student2 = mapper2.getStudentBySid(1);
            }

            Student student1 = mapper.getStudentBySid(1);
            System.out.println(student1 == student2);
        }*/

        /**
         * 我们来编写一段代码
         * 我们可以看到 下面的代码中首先是第一个会话在进行读操作 完成后会结束会话 而第二个操作重新创建了一个新的会话 再次执行了同样的查询 我们发现得到的依然是缓存的结果
         *
         * 我们也可以使用flushCache="false"在每次执行后清空缓存 通过这个我们还可以控制DML操作完成之后不清空缓存
         *      <select id="getStudentBySid" resultType="Student" flushCache="false" useCache="true">
         *          slect * from student where sid = #{sid}
         *      </select>
         */
        Student student1;
        try (SqlSession session1 = MybatisUtil.getSession(true)) {
            TestMapper mapper1 = session1.getMapper(TestMapper.class);
            student1 = mapper1.getStudentBySid(1);
        }

        try (SqlSession session2 = MybatisUtil.getSession(true)) {
            TestMapper mapper2 = session2.getMapper(TestMapper.class);
            Student student2 = mapper2.getStudentBySid(1);
            System.out.println(student1 == student2);
        }
    }

    static void test3(){
        /**
         * 添加了二级缓存之后 会先从二级缓存中查找数据 当二级缓存中没有时 才会从一级缓存中获取 当一级缓存中都还没有数据时
         * 才会请求数据库 因此我们再来执行下面代码
         *
         * 得到的结果就会是同一个对象了 因为现在是优先从二级缓存中获取 读取顺序: 二级缓存 => 一级缓存 => 数据库
         */
        /*try (SqlSession session = MybatisUtil.getSession(true)) {
            TestMapper mapper = session.getMapper(TestMapper.class);

            Student student2;
            try (SqlSession session2 = MybatisUtil.getSession(true)){
                TestMapper mapper2 = session2.getMapper(TestMapper.class);
                student2 = mapper2.getStudentBySid(1);
            }

            Student student1 = mapper.getStudentBySid(1);
            System.out.println(student1 == student2);
        }*/

        try (SqlSession session1 = MybatisUtil.getSession(true);
             SqlSession session2 = MybatisUtil.getSession(true)) {
            TestMapper mapper1 = session1.getMapper(TestMapper.class);
            TestMapper mapper2 = session2.getMapper(TestMapper.class);

            Student student1 = mapper1.getStudentBySid(1);
            Student student2 = mapper2.getStudentBySid(1);

            System.out.println(student1 == student2);
        }
    }

    static void test4(){
        /**
         * 虽然缓存机制给我们提供了很大的性能提升 但是缓存存在一个问题 我们之前在计算机原理中可能学习过缓存一致性问题
         * 也就是说当多个CPU在操作自己的缓存时 可能会出现各自的缓存内容不同步的问题 而Mybatis也会这样 我们来看看这个例子
         *
         * 我们现在循环地每一秒钟读取一次 而在这个过程中 我们使用idea手动修改数据库中的数据 将1号同学的信息改掉
         * 那么理想情况下 下一次读取将无法获取到小明 因为小明的学号已经发生改变了
         *
         * 但是结果却是依然能够读取 并且信息并没有发生改变 这也证明了Mybatis缓存存在生效 因此我们是从外部进行修改 Mybatis不知道我们修改了数据 所有依然在使用缓存中的数据
         * 但是这样很明显是不正确的 因此如果存在多台服务器或者多个程序都在使用Mybatis操作同一个数据库 并且都开启了缓存 需要解决这个问题 要么就得关闭Mybatis的缓存来保证一致性
         *      <settings>
         *          <setting name="cacheEnabled" value="false"/>
         *      </settings>
         *
         *      <select id="getStudentBySid" resultType="Student" useCache="false" flushCache="true">
         *          select * from student where sid = #{sid}
         *      </select>
         *
         * 要么就需要实现缓存共用 也就是让所有的Mybatis都使用一个缓存进行数据存取 在后面 我们会继续学习 Redis Ehcache Memcache 等缓存框架 通过使用这些工具 就能够很好地解决缓存一致性问题
         */
        try (SqlSession session = MybatisUtil.getSession(true)) {
            TestMapper mapper = session.getMapper(TestMapper.class);
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(mapper.getStudentBySid(1));
            }
        }
    }

    public static void main(String[] args) {
        //test1();
        //test2();
        //test3();
        test4();
    }

}
