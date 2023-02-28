package JDBC.jdbc8;

import java.sql.*;

/**
 * jdbc管理事务
 * jdbc默认的事务处理行为是自动提交 所以前面我们执行一个sql语句就会被直接提交(相当于没有启动事务)
 * 所以jdbc需要进行事务管理时 首先要通过Connection对象调用setAutoCommit(false)方法 将sql语句的提交(commit)由驱动程序转交给应用程序负责:
 *
 *                  con.setAutoCommit(); // 关闭自动提交后相当与开启事务
 *                  // sql语句
 *                  // sql语句
 *                  // sql语句
 *                  con.commit(); 或 con.rollback();
 *
 * 一旦关闭自动提交 那么现在执行所有的操作如果在最后不进行commit()来提交事务的话 那么所有的操作都会丢失
 * 只有提交之后 所有的操作才会被保存 也可以使用rollback来手动回滚之前的全部操作:
 *
 *                  try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
 *                       Statement statement = connection.createStatement()) {
 *                       connection.setAutoCommit(false); // 关闭自动提交 现在变为我们手动提交
 *
 *                       statement.executeUpdate("insert into user values ('a', 1234)");
 *                       statement.executeUpdate("insert into user values ('b', 1234)");
 *                       statement.executeUpdate("insert into user values ('c', 1234)");
 *
 *                       connection.rollback(); // 回滚 撤销前面的全部操作
 *                       statement.executeUpdate("insert into user values ('c', 1234)");
 *                       connection.commit(); // 如果前面任何操作出现异常 将不会执行commit() 之前的操作也就不会生效
 *                  } catch (SQLException e){
 *                      e.printStackTrace();
 *                  }
 *
 * 我们来接着尝试一下使用回滚操作:
 *
 *                  try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
 *                       Statement statement = connection.createStatement()) {
 *
 *                       connection.setAutoCommit(false);  // 关闭自动提交 现在将变为我们手动提交
 *                       statement.executeUpdate("insert into user values ('a', 1234)");
 *                       statement.executeUpdate("insert into user values ('b', 1234)");
 *
 *                       connection.rollback();   // 回滚 撤销前面全部操作
 *
 *                       statement.executeUpdate("insert into user values ('c', 1234)");
 *
 *                       connection.commit();   //提交事务 (注意 回滚之前的内容都没了)
 *                  }catch (SQLException e){
 *                      e.printStackTrace();
 *                  }
 *
 * 同样的 我们也可以去创建一个回滚点来实现定点回滚
 *
 *                  try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
 *                       Statement statement = connection.createStatement()) {
 *                       connection.setAutoCommit(false);
 *
 *                       statement.executeUpdate("insert into user values ('a', 1234)");
 *
 *                       Savepoint savepoint = connection.setSavepoint(); // 创建回滚点
 *                       statement.executeUpdate("insert into user values ('b', 1234)");
 *                       statement.executeUpdate("insert into user values ('c', 1234)");
 *
 *                       connection.rollback(savepoint); // 回滚到回滚点 撤销前面全部操作
 *                       connection.commit();
 *                  } catch (SQLException e){
 *                      e.printStackTrace();
 *                  }
 *
 * 通过开启事务 我们就可以更加谨慎地进行一些操作了 如果我们想从事务模式切换为原有的自动提交模式 我们可以直接将其设置回去:
 *
 *                  try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");
 *                       Statement statement = connection.createStatement()) {
 *
 *                       connection.setAutoCommit(false); // 关闭自动提交 现在将变为我们手动提交
 *                       statement.executeUpdate("insert into user values ('a', 1234)");
 *                       connection.setAutoCommit(true); // 重新开启自动提交 开启时把之前的事务模式下的内容给提交了
 *                       statement.executeUpdate("insert into user values ('d', )")
 *                       // 没有commit也成功了
 *                  } catch (SQLException e) {
 *                      e.printStackTrace(e);
 *                  }
 *
 * 通过学习JDBC 我们现在就可以通过Java来访问和操作我们的数据库了 为了更好地衔接 我们还会接着讲解主流持久层框架——Mybatis 加深JDBC的记忆
 */
public class Test {

    public static void main(String[] args) {

        /*try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
               Statement statement = connection.createStatement()) {

               connection.setAutoCommit(false); // 关闭自动提交 现在变为我们手动提交

               statement.executeUpdate("insert into user values ('a', 1234)");
               statement.executeUpdate("insert into user values ('b', 1234)");
               statement.executeUpdate("insert into user values ('c', 1234)");

               connection.rollback(); // 回滚 撤销前面的全部操作
               statement.executeUpdate("insert into user values ('c', 1234)");
               connection.commit(); // 如果前面任何操作出现异常 将不会执行commit() 之前的操作也就不会生效
        } catch (SQLException e){
            e.printStackTrace();
        }*/

        /*try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
               Statement statement = connection.createStatement()) {

               connection.setAutoCommit(false);  // 关闭自动提交 现在将变为我们手动提交
               statement.executeUpdate("insert into user values ('a', 1234)");
               statement.executeUpdate("insert into user values ('b', 1234)");
               connection.rollback();
               statement.executeUpdate("insert into user values ('c', 1234)");
               connection.commit();   // 提交事务 (注意 回滚之前的内容都没了)
        }catch (SQLException e){
            e.printStackTrace();
        }*/

        /*try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
               Statement statement = connection.createStatement()) {
               connection.setAutoCommit(false);

               statement.executeUpdate("insert into user values ('a', 1234)");

               Savepoint savepoint = connection.setSavepoint(); // 创建回滚点
               statement.executeUpdate("insert into user values ('b', 1234)");
               statement.executeUpdate("insert into user values ('c', 1234)");

               connection.rollback(savepoint); // 回滚到回滚点 撤销前面全部操作
               connection.commit();
        } catch (SQLException e){
            e.printStackTrace();
        }*/

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");
             Statement statement = conn.createStatement()) {

             conn.setAutoCommit(false); // 关闭自动提交 现在将变为我们手动提交
             statement.executeUpdate("insert into user values ('a', 1234)");
             conn.setAutoCommit(true); // 重新开启自动提交 开启时把之前的事务模式下的内容给提交了
             statement.executeUpdate("insert into user values ('d', 1234)");
             // 没有commit也成功了
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
