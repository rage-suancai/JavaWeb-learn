package JDBC.jdbc7;

import java.sql.*;
import java.util.Scanner;

/**
 * 使用PreparedStatement
 * 我们发现 如果单纯地使用Statement来执行SQL命令 会存在严重的SQL注入攻击漏洞 而这种问题 我们可以使用PreparedStatement来解决:
 *
 *                  public static void main(String[] args) {
 *
 *                      try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234456");
 *                          PreparedStatement pst = conn.prepareStatement("select * from user where username=? and pwd=?;");
 *                          Scanner sc = new Scanner(System.in)) {
 *
 *                          pst.setString(1, sc.nextLine());
 *                          pst.setString(2, sc.nextLine());
 *                          System.out.println(pst); // 打印查看一下最终执行的
 *                          ResultSet res = pst.executeQuery();
 *                          while (res.next()) {
 *                              String username = res.getString(1);
 *                              System.out.println(username + " 登录成功");
 *                          }
 *                      } catch (SQLException e) {
 *                          e.printStackTrace();
 *                      }
 *
 *                  }
 *
 * 我们发现 我们需要提前给到PreparedStatement一个SQL语句 并且使用?作为占位符 它会预编译一个SQL语句
 * 通过直接将我们的内容进行替换的方式来填写数据 使用这种方式 我们之前的例子就失效了 我们来看看实际执行的SQL语句是什么:
 *
 *                  com.mysql.cj.jdbc.ClientPreparedStatement: select * from user where username= 'Test' and pwd='123456'' or 1=1; -- ';
 *
 * 我们发现 我们输入的参数一旦出现 ' 时 会被变成转义形式 \' 而最外层有一个真正的 ' 来将我们输入的内容进行包裹 因此它能够有效地防止SQL注入攻击
 */
public class Test {

    static void test1() {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234456");
             PreparedStatement pst = conn.prepareStatement("select * from user where username= ? and pwd=?;");
             Scanner sc = new Scanner(System.in)){

             pst.setString(1, sc.nextLine());
             pst.setString(2, sc.nextLine());
             System.out.println(pst); // 打印查看一下最终执行的
             ResultSet res = pst.executeQuery();
             while (res.next()){
                 String username = res.getString(1);
                 System.out.println(username+" 登陆成功");
             }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {



    }

}
