package MyBatis.mybatis3;

import MyBatis.mybatis3.mapper.TestMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * 配置Mybatis
 * 在了解了Mybatis为我们带来的便捷之后 现在我们就可以正式的去学习使用Mybatis了
 *
 * 之前我们演示了 如何创建一个映射器来将结果快速转换为实体类 但是这样可能还是不够方便 我们每次都需要去映射器对应操作的名称
 * 而且还要知道对应的返回类型 再通过SqlSession来执行对应的方法 能不能再方便有点呢
 *
 * 现在 我们可以通过namespace来绑定到一个接口上 利用接口的特性 我们可以直接指明方法的行为 而实际实现则是由Mybatis来完成
 *
 * 那么还是有人好奇 TestMapper明明是一个我们自己定义的接口 Mybatis也不可能提前帮我们写了实现类
 * 那这接口怎么就出现了一个实现类呢 我们可以通过调用getClass()方法来看看实现了什么
 * 我们发现 实现类名称很奇怪 名称为com.sun.proxy.$Proxy4  它是通过动态代理生成的 相当于动态生成了一个实现类 而不是预先定义好的 有关Mybatis这一部分的原理 我们最后在说
 *
 * 接下来 我们再来看看配置文件 之前我们并没有对配置文件进行详细介绍
 *     首先就从environments标签说起 一般情况下 我们在开发中 都需要指定一个数据库的配置信息 包含连接URL 用户 密码等信息
 *     而environment就是用于进行这些配置的 实际情况下可能会不止一个数据库连接信息 比如开发过程中我们一般会使用本地的数据库
 *     而如果需要将项目上传到服务器或是其他人电脑上运行时 我们可能就需配置另一个数据库的信息 因此 我们可以提前定义好所有的数据库信息 该什么时候用什么即可
 *
 *     在environments标签上有一个default属性 来指定默认的环境 当然如果我们希望使用其他环境 可以修改这个默认环境 也可以在创建工厂时选择环境
 *
 *     我们还可以给类型起一个别名 以简化Mapper的内容
 *     <typeAliases>
 *         <typeAlias type="MyBatis.mybatis3.entity.Student" alias="Student"/>
 *     </typeAliases>
 *
 *     如果这样还是很麻烦 我们也可以直接让Mybatis去扫描一个包 并将包下的所有类自动起别名(别名为首字母小写的类名)
 *     <typeAliases>
 *         <package name="MyBatis.mybatis3.entity"/>
 *     </typeAliases>
 *
 *     也可以为指定实体类添加一个注解 来指定别名
 *     @Alias("student")
 *
 *     当然 Mybatis也包含许多的基础配置 通过使用
 *     <settings>
 *         <setting name="" value=""/>
 *     </settings>
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
