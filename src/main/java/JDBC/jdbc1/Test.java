package JDBC.jdbc1;

import java.io.PrintWriter;
import java.sql.*;

/**
 * 认识JDBC
 * JDBC（Java Data Base Connectivity,java数据库连接）是一种用于执行SQL语句的Java API 可以为多种关系数据库提供统一访问 它由一组用Java语言编写的类和接口组成
 * JDBC提供了一种基准 据此可以构建更高级的工具和接口 使数据库开发人员能够编写数据库应用程序 同时JDBC也是个商标名
 *
 * 有了JDBC向各种关系数据库发送SQL语句就是一件很容易的事 换言之有了JDBC API就不必为访问Sybase 数据库专门写一个程序为访问 Oracle 数据库又专门写一个程序为访问 Informix 数据库又写另一个程序等等
 * 您只需用 JDBC API 写一个程序就够了它可向相应数据库发送SQL语句 而且使用 Java 编程语言编写的应用程序就无须去忧虑要为不同的平台编写不同的应用程序
 * 将Java 和 JDBC 结合起来将使程序员只须写一遍程序就可让它在任何平台上运行
 *
 * 我们可以发现 JDK自带了一个java.sql包 而这里面就定义了大量接口 不同类型的数据库 都可以通过实现此接口 编写适用与自己数据库的实现类 而不同的数据库厂商实现的这套标准 我们称为数据库驱动
 *
 * 准备工作
 * 那么我们首先来进行一些准备 以便开始JDBC的学习
 *
 *      > 将idea连接到我们的数据库 以便以后调试
 *      > 将mysql驱动jar依赖导入到项目中(推荐6.0版本以上)
 *
 * 一个java程序并不是一个人的战斗 我们可以在别人开发的基础上继续向上开发 其他的开发者可以将自己编写的java代码打包为jar
 * 我们只需要导入这个jar作为依赖 即可直接使用别人的代码 就像我们直接去使用JDK提供的类一样
 *
 * 使用JDBC连接数据库
 * 注意 6.0版本以上 不用手动加载驱动 直接使用即可
 *
 * 其中 连接的URL如果记不住格式 我们可以打开idea的数据库连接配置 复制一份即可
 * (其实idea本质也是使用的JDBC 整个idea程序都是由java编写的 实际上idea就是一个java程序)
 */
public class Test {

    public static void main(String[] args) {

        DriverManager.setLogWriter(new PrintWriter(System.out));

        // 1. 通过DriverManager来获取数据库连接
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
             // 2. 创建一个用于执行sql的Statement对象
             Statement statement = connection.createStatement()){ // 注意: 前两步都放在try()中 因为最后需要释放资源
            // 3. 执行sql语句 并得到结果集
            ResultSet set = statement.executeQuery("select * from Student");
            // 4. 查看结果
            while (set.next()){
                System.out.println(set.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 5.释放资源 try-with-resource语法会自动帮助释放资源

    }

}
