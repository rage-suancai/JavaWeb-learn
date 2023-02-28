package JDBC.jdbc5;

import java.lang.reflect.Constructor;
import java.sql.*;

/**
 * 将查询结果映射为对象
 * 既然我们现在可以从数据库中获取数据了 那么现在就可以将这些数据转换为一个类来进行操作 首先定义我们的实体类:
 *
 *                  public class Student {
 *
 *                      Integer id;
 *                      String name;
 *                      String sex;
 *
 *                      public Student(Integer id, String name, String sex) {
 *                          this.id = id;
 *                          this.name = name;
 *                          this.sex = sex;
 *                      }
 *
 *                      public void say(){
 *                          System.out.println("我叫: " + name + " " + "学号为: " + id + " " + "我的性别为: " + sex);
 *                      }
 *
 *                  }
 *
 * 现在我们来进行一个转换:
 *
 *                  while (set.next()) {
 *                      Student student = new Student(set.getInt(1), set.getString(2), set.getString(3));
 *                  }
 *
 * 注意: 列的下标是从1开始的
 *
 * 我们也可以利用反射机制来将查询结果映射为对象 使用反射的好处是 无论什么类型都可以通过我们的方法来进行实体类型的映射:
 *
 *                  private static <T> T convert(ResultSet set, Class<T> clazz){
 *
 *                      try {
 *                          Constructor<T> constructor = clazz.getConstructor(clazz.getConstructors()[0].getParameterTypes()); // 默认获取第一个构造方法
 *                          Class<?>[] param = constructor.getParameterTypes(); // 获取参数类型
 *                          Object[] object = new Object[param.length]; // 存放参数
 *
 *                          for (int i = 0; i < param.length; ++i) { // 结果集是从1开始的
 *                              object[i] = set.getObject(i+1);
 *                              if (object[i].getClass() != param[i])
 *                                  throw new SQLException("错误的类型转换: " + object[i].getClass() + " -> " + param[i]);
 *                          }
 *                          return constructor.newInstance(object);
 *                      } catch (ReflectiveOperationException | SQLException e){
 *                          e.printStackTrace();
 *                          return null;
 *                      }
 *
 *                  }
 *
 * 现在我们就依靠通过我们的方法来将查询结果转换为一个对象了:
 *
 *                  while () {
 *                      Student student = convert(set, Student.class);
 *                      if (student != null) student.say;
 *                  }
 *
 * 实际上 在后面我们会学习Mybatis框架 它对JDBC进行了深层次的封装 而它就进行类似上面反射的操作1来便于我们对数据库数据与实体类的转换
 */
public class Test {

    public static void main(String[] args) {

        /*try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
             Statement statement = connection.createStatement()){

            ResultSet set = statement.executeQuery("select * from student");
            while (set.next()){
                Student student = new Student(set.getInt(1), set.getString(2), set.getString(3));
                student.say();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }*/

        // 现在我们就可以通过我们的方法来将查询结果转换为一个对象了
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
             Statement statement = connection.createStatement()){

            ResultSet set = statement.executeQuery("select * from Student");
            while (set.next()){ // 现在我们来进行一个转换 (注意 列的下标是从1开始的)
                Student student = convert(set, Student.class);
                student.say();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private static <T> T convert(ResultSet set, Class<T> clazz){

        try {
            Constructor<T> constructor = clazz.getConstructor(clazz.getConstructors()[0].getParameterTypes()); // 默认获取第一个构造方法
            Class<?>[] param = constructor.getParameterTypes(); // 获取参数类型
            Object[] object = new Object[param.length]; // 存放参数

            for (int i = 0; i < param.length; ++i) { // 结果集是从1开始的
                object[i] = set.getObject(i+1);
                if (object[i].getClass() != param[i])
                    throw new SQLException("错误的类型转换: " + object[i].getClass() + " -> " + param[i]);
            }
            return constructor.newInstance(object);
        } catch (ReflectiveOperationException | SQLException e){
            e.printStackTrace();
            return null;
        }

    }

}
