package Lombok.lombok2;

public class Test {

    public static void main(String[] args) {
        Student student = new Student();
        student
                .sex("543")
                .name("小红")
                .sid(1);

        System.out.println(student.sex);
        System.out.println(student.name);
        System.out.println(student.sid);
    }

}
