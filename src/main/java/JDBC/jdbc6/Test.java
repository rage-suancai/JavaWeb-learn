package JDBC.jdbc6;

import java.sql.*;
import java.util.Scanner;

/**
 * jdbc实现登录与SQL注入攻击
 * 在使用之前我们先来看看如果我们想模拟登录一个用户 我们该怎么写
 *
 * 用户可以通过自己输入用户名和密码来登录 乍一看好像没啥问题 那如果我输入是是以下内容呢:
 *
 *                  Test
 *                  1111; or 1=1; --
 *                  # Test 登录成功
 *
 * 1=1一定是true 那么我们原来的SQL语句会变为:
 *
 *                  select * from user where username='Test' and pwd='1111' or 1=1; -- '
 *
 * 我们发现 如果允许这样的数据插入 那么我们原有的SQL语句结构就遭到了破坏 使得用户能够随意登录别人的账号
 * 因此我们可能需要限制用户的输入来防止用户输入一些SQL语句关键字 但是关键字非常多 这并不是解决问题的最好办法
 */
public class Test {

    public static void main(String[] args) {

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
