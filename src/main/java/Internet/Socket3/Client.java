package Internet.Socket3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 8080)){
            FileInputStream fileInputStream = new FileInputStream("test.txt");

            OutputStream stream = socket.getOutputStream();
            byte[] bytes = new byte[1024];
            int i;
            while ((i = fileInputStream.read(bytes)) != -1){
                stream.write(bytes, 0, i);
            }
            fileInputStream.close();
            stream.flush();
            stream.close();
        } catch (IOException e) {
            System.out.println("服务端连接失败");
            e.printStackTrace();
        }

    }

}
