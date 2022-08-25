package Lombok.lombok6;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.InputStream;

public class Test {

    static void test1(){
        Student student = null;
    }

    //@SneakyThrows
    @SneakyThrows(Exception.class)
    static void test2(){
        /*InputStream inputStream = new FileInputStream("");
        inputStream.close();*/

        @Cleanup
        InputStream inputStream = new FileInputStream("lombok");
        //inputStream.close();
    }

    public static void main(String[] args) {
        //test1();
        test2();
    }

}
