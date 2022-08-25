package JDBC.jdbc5;

public class Student {
    Integer id;
    String name;
    String sex;

    public Student(Integer id, String name, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    public void say(){
        System.out.println("我叫: " + name + " " + "学号为: " + id + " " + "我的性别为: " + sex);
    }
}
