package JDBC.jdbc4;

import java.sql.*;

public class Test {
    public static void main(String[] args) {
        /**
         * 执行DML操作
         * 通过几个例子来向数据库中操作数据
         */
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
             Statement statement = connection.createStatement()){

            //int i1 = statement.executeUpdate("insert into student values(2, '希特勒')"); // 插入
            //System.out.println("成功插入: " + i1 + "行");

            //int i2 = statement.executeUpdate("update student set name = '小吵闹' where id = 3 "); // 修改
            //System.out.println("成功修改: " + i2 + "行");

            //int i3 = statement.executeUpdate("delete from student where id = 2"); // 删除
            //System.out.println("成功删除: " + i3 + "行");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        /**
         * 执行DQL操作
         * 执行DQL操作会返回一个ResultSet对象 我们来看看如何从ResultSet中去获取数据
         */
        /*try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
             Statement statement = connection.createStatement()){

            ResultSet set = statement.executeQuery("select * from student");
            while (set.next()){
                System.out.println(set.getInt("id"));

                System.out.println(set.getString(1));
                System.out.println(set.getString(2));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }*/

        /**
         * 执行批处理操作
         * 当我们执行很多条语句时 可以不用一次一次地提交 而是一口气全部交给数据库处理 这样会节省很多的时间
         */
        /*try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
             Statement statement = connection.createStatement()){

            statement.addBatch("insert into teach values (1, 'A教授')");
            statement.addBatch("insert into teach values (2, 'B教授')");
            statement.addBatch("insert into teach values (3, 'C教授')");
            statement.executeBatch();
        } catch (SQLException e){
            e.printStackTrace();
        }*/
    }
}
