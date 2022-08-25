package Lombok.lombok7;

public class Test {

    public static void main(String[] args) {
        //Student student = null;

        Student student = Student
                .builder()
                .sex("男")
                .name("小明")
                .sid(1)
                .build();
        System.out.println(student);

    }

}
