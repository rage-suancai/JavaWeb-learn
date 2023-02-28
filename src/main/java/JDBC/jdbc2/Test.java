package JDBC.jdbc2;

import java.io.PrintWriter;
import java.sql.*;

/**
 * 了解DriverManager
 * 我们首先来了解一下DriverManager是什么 它其实就是管理我们的数据库驱动的
 *
 * 我们可以通过调用getConnection()来进行数据库的链接:
 *
 *                  public static void main(String[] args) throws ClassNotFoundException {
 *                      // Class.forName("com.mysql.jdbc.Driver");
 *
 *                      DriverManager.setLogWriter(new PrintWriter(System.out)); // 我们可以手动为驱动管理器添加一个日志打印
 *                      try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
 *                           Statement statement = connection.createStatement()){
 *                          ResultSet set = statement.executeQuery("select * from Student");
 *                          while (set.next()){
 *                              System.out.println(set.getString(1));
 *                          }
 *                      } catch (SQLException e){
 *                          e.printStackTrace();
 *                      }
 *                  }
 *
 * 我们可以手动为驱动管理器添加一个日志打印:
 *
 *                  static {
 *                      DriverManager.setLogWriter(new PrintWriter(System.out));
 *                  }
 *
 * 现在我们执行的数据库操作日志会在控制台实时打印
 */
public class Test {

    public static void main(String[] args) throws ClassNotFoundException {
        // Class.forName("com.mysql.jdbc.Driver");

        DriverManager.setLogWriter(new PrintWriter(System.out)); // 我们可以手动为驱动管理器添加一个日志打印
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
             Statement statement = connection.createStatement()){
            ResultSet set = statement.executeQuery("select * from Student");
            while (set.next()){
                System.out.println(set.getString(1));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
