package Lombok.lombok1;

public class Test {

    public static void main(String[] args) {
        Student student = new Student(1, "小明", "20");
        System.out.println(student.getSid());
        System.out.println(student.getName());
        System.out.println(student.getSex());
    }

}
