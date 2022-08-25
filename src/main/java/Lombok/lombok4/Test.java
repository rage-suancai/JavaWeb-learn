package Lombok.lombok4;

public class Test {

    public static void main(String[] args) {
        Student student = new Student();
        System.out.println(student);

        /**
         * javaGoto语句(打标签方式)
         */
        /*label1:
        for (int i = 0; i < 10; ++i) {
            System.out.println("外层" + i);
            for (int j = 0; j < 10; ++j){
                System.out.println("内层" + j);
                //if (j == 1) break label1;
                if (j == 1) continue label1;
            }
        }*/
        /*label1: { // 代码块
            for (int  i = 0; i < 10; ++i) {
                System.out.println("外层" + i);
                for (int j = 0; j < 10; ++j) {
                    System.out.println("内层" + j);
                    if (j == 1) break label1;
                }
            }
            System.out.println(student);
        }*/

    }

}
