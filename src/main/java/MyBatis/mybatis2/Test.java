package MyBatis.mybatis2;

import Lombok.lombok7.Student;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 初次使用Mybatis
 * 那么我们首先来感受一下Mybatis给我们带来的便捷 就从搭建环境开始 中文文档网站: https://mybatis.org/mybatis-3/zh/configuration.html
 *
 * 我们需要导入Mybatis的依赖 jar包要在github上下载 如果卡的一批 连不上可以用手机热点试试 同样的放入到项目的根目录下 右键作为依赖即可
 * (依赖变多之后 我们可以将其放到一个单独的文件夹 不然很繁杂)
 *
 * 依赖导入完成后 我们就可以编写Mybatis的配置文件了(现在不是在java代码中配置了 而是通过一个XML文件去配置 这样就使得硬编码的部分大大减少)
 * 项目后期打包成jar运行不方便修复 但是通过配置文件 我们随时都可以去修改 就变得很方便了 同时代码量也大幅度减少 配置文件填写完成后
 * 我们只需要关心项目的业务逻辑而不是如何去读取配置文件) 我们按照官方文档给定的提示 在项目根目录下新建名为mybatis-config.xml的文件 内容如下:
 *                  <?xml version="1.0" encoding="UTF-8" ?>
 *                  <!DOCTYPE configuration
 *                    PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
 *                    "http://mybatis.org/dtd/mybatis-3-config.dtd">
 *                  <configuration>
 *                    <environments default="development">
 *                      <environment id="development">
 *                        <transactionManager type="JDBC"/>
 *                        <dataSource type="POOLED">
 *                          <property name="driver" value="${driver}"/>
 *                          <property name="url" value="${url}"/>
 *                          <property name="username" value="${username}"/>
 *                          <property name="password" value="${password}"/>
 *                        </dataSource>
 *                      </environment>
 *                    </environments>
 *                    <mappers>
 *                      <mapper resource="org/mybatis/example/BlogMapper.xml"/>
 *                    </mappers>
 *                  </configuration>
 *
 * 我们发现 在最上面还引入了一个叫做DTD(文档类型定义)的东西 它提前帮助我们规定了一些标签 我们就需要使用Mybatis提前帮助我们规定好的标签来进行配置
 * (因为只有这样Mybatis才能正确识别我们配置的内容)
 *
 * 通过进行配置 我们就告诉了Mybatis我们连接数据库的一些信息 包括url 用户名 密码等这样Mybatis就知道该连接哪个数据库 使用哪个账号进行登录了
 * (也可以不使用配置文件 这里不做讲解 还请自行阅读官方文档)
 */
public class Test {

    /**
     * 配置文件完成后 我们需要在java程序启动时 让Mybatis对配置文件进行读取并得到一个sqlSessionFactory 对象
     * 直接运行即可 虽然没有干什么事情 但是不会出现错误 如果之前的配置文件编写错误 直接运行会产生报错 那么现在来看看SqlSessionFactory对象是什么
     *
     * SqlSessionFactoryBuilder -builder()> SqlSessionFactory -openSession()> SqlSession
     *
     * 每个基于Mybatis的应用都是以一个SqlSessionFactory的实例为核心的  我们可以通过SqlSessionFactory来创建多个新的会话 SqlSession对象
     * 每个会话就相当于我们不同地方登录一个账号去访问数据库 你也可以认为这就是之前JDBC中的Statement对象 会话之间相互隔离 没有任何关联
     *
     * 而通过SqlSession就可以完成几乎所有的数据库操作 我们发现这个接口中定义了大量数据库操作方法 因此 现在我们只需要通过一个对象就能完成数据库交互了 极大简化了之前的流程
     *
     * 我们来尝试一下直接读取实体类 读取实体类肯定需要一个映射规则 比如类中的哪个字段对应数据库中的哪个字段 在查询语句返回结果后
     * Mybatis就会自动将对应的结果填入到对象的应字段上 首先编写实体类 直接使用Lombok是不是就很方便了
     */
    public static void main(String[] args) throws FileNotFoundException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(new FileInputStream("mybatis-config.xml"));
        try (SqlSession session = sqlSessionFactory.openSession(true)) { // 表示事务自动提交 (false表示开启事务模式)
            /*List<Object> list = session.selectList("selectStudent");
            list.forEach(System.out::println);*/

            System.out.println((Object) session.selectOne("selectStudent", 3));
        }

    }

}
