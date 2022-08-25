package Internet.Socket1;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        // 客户端
        try (Socket socket = new Socket("localhost", 8080)){
            System.out.println("已连接到服务器");
        } catch (IOException e){
            System.out.println("服务器连接失败");
            e.printStackTrace();
        }
    }
}
