package JDBC.jdbc3;

import java.sql.*;

/**
 * 了解Connection
 * Connection是数据库连接对象 可以通过连接对象来创建一个Statement用于执行sql语句:
 *
 *                  Statement createStatement() throws SQLException();
 *
 * 我们发现除了普通的Statement 还存在PreparedStatement 在后面会介绍PreparedStatement的使用 它能有效预防sql注入式攻击 它还支持事务的处理
 *
 *                  PreparedStatement prepareStatement(String sql) throws SQLException
 *
 * 在后面我们会不详细介绍PreparedStatement的使用 它能够有效地预防SQL注入式攻击
 *
 * 它还支持事务的处理 也放到后面来详细进行讲解
 *
 * 了解Statement
 * 我们之前使用了executeQuery()方法来执行select语句 此方法返回给我们一个ResultSet对象 查询得到数据 就放在ResultSet中
 *
 * Statement除了执行这样的DQL语句外 我们还可以使用executeUpdate()方法来执行一个DML或是DDL语句 它会返回一个int类型 表示执行后受影响的行数 可以通过它来判断DML语句是否执行成功
 *
 * 也可以通过excute()来执行任意的sql语句 它会返回一个boolean来表示执行结果是一个ResultSet还是一个int 我们可以通过使用getResultSet()或是getUpdateCount()来获取
 */
public class Test {

    public static void main(String[] args) {
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
