package JDBC.jdbc6;

import java.sql.*;
import java.util.Scanner;

/**
 * jdbc实现登录与SQL注入攻击
 */
public class Test {

    public static void main(String[] args) {
        /**
         * 在使用之前我们先来看看如果我们想模拟登录一个用户 我们该怎么写
         */
        /*try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
             Statement statement = connection.createStatement();
             Scanner scanner = new Scanner(System.in)){

            ResultSet res = statement.executeQuery("select * from user where username = '" + scanner.nextLine() + "' and pwd = '" + scanner.nextLine() + "';");
            while (res.next()){
                String username = res.getString(1);
                System.out.println(username + "登陆成功 欢迎");
            }

        }  catch (SQLException e){
            e.printStackTrace();
        }*/

        /**
         * 用户可以通过自己输入用户名和密码来登录 乍一看好像没啥问题 那如果我输入是是以下内容呢 (' or 1='1) (1111' or 1='1 --) (1111' or 1=1; -- )
         *
         * 我们发现 如果允许这样的数据插入 那么我们原有的sql语句结构就遭到了破环 使得用户能够随意登录别人的账号
         * 因此我们可可能需要限制用户的输入来防止用户输入一些sql语句关键字 但是关键字非常多 这并不是解决问题的最好办法
         */
        /*try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
             Statement statement = connection.createStatement();
             Scanner scanner = new Scanner(System.in)){

            String sql = "select * from user where username = '" + scanner.nextLine() + "' and pwd = '" + scanner.nextLine() + "';";
            System.out.println(sql); // 1=1一定是true 那么我们原本的sql语句会变成这样
            ResultSet res = statement.executeQuery(sql);

            while (res.next()){
                String username = res.getString(1);
                System.out.println(username + "登陆成功 欢迎");
            }

        }  catch (SQLException e) {
            e.printStackTrace();
        }*/

        /**
         * 使用PreparedStatement
         *
         * 如果单纯地使用Statement来执行sql命令 会存在严重的sql注入攻击漏洞 而这种问题 我们可以使用PreparedStatement来解决
         *
         * 我们需要提前给到PreparedStatement一个sql语句 并且使用 ? 作为占位符 它会预编译一个sql语句 通过直接将我们的内容进行替换的方式来填写数据 使用这种方式 我们之前的例子就失效了
         */
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
             PreparedStatement preparedStatement = connection.prepareStatement("select * from user where username = ? and pwd = ?;");
             Scanner scanner = new Scanner(System.in)){

            preparedStatement.setString(1, scanner.nextLine());
            preparedStatement.setString(2, scanner.nextLine());

            // 我们输入的参数一旦出现 ' 时会被变为转义形式\' 而是外层有一个真正的 ' 来将我们输入的内容进行包裹 因此它能够有效地防止sql注入攻击
            System.out.println(preparedStatement.toString());

            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                String username = res.getString(1);
                System.out.println(username + " 登录成功 欢迎 ");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

}
