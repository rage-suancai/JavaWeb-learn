package Internet.Socket3;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 使用Socket传输文件
 * 既然Socket为我们提供了I/O流便于数据传输 那么我们就可以轻松地实现文件传输了
 */
public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8080);
             FileOutputStream fileOutputStream = new FileOutputStream("net/data.txt")){
            Socket socket = server.accept();
            InputStream stream = socket.getInputStream();

            byte[] bytes = new byte[1024];
            int i;
            while ((i = stream.read(bytes)) != -1){
                fileOutputStream.write(bytes, 0, i);
            }
            fileOutputStream.flush();
            stream.close();
        } catch (IOException e) {
            System.out.println("客户端连接失败");
            e.printStackTrace();
        }
    }
}
