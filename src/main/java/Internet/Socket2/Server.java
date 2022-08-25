package Internet.Socket2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 使用Socket进行数据传输
 * 通过Socket对象 我们就可以获取到对应的I/O流进行网络数据传输
 */
public class Server {
    public static void main(String[] args) {
        // 服务端
        /*try (ServerSocket server = new ServerSocket(8080)){
            while (true){
                System.out.println("正在等待客户端连接....");
                Socket socket = server.accept();
                System.out.println("客户端已连接 ip地址为: " + socket.getInetAddress().getHostAddress());
                System.out.println("读取客户端数据: ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(reader.readLine());
                socket.close(); // 和服务端TCP连接完成后 记得关闭socket
            }
        } catch (IOException e){
            System.out.println("客户端连接失败");
            e.printStackTrace();
        }*/

        // 同理 既然服务端可以读取客户端的内容 客户端也可以在发送后等待服务端给与响应
        /*try (ServerSocket server = new ServerSocket(8080)){
            while (true){
                System.out.println("正在等待客户端连接....");
                Socket socket = server.accept();
                System.out.println("客户端已连接 ip地址为: " + socket.getInetAddress().getHostAddress());
                System.out.println("读取客户端数据: ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(reader.readLine());

                OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
                writer.write("已接收到数据\n");
                writer.flush();
                socket.close();
            }
        } catch(IOException e){
            System.out.println();
            e.printStackTrace();
        }*/

        // 如果我们不希望服务端等待太长的时间 我们可以通过调用setSoTimeout()方法来设定I/O超时时间
        try (ServerSocket server = new ServerSocket(8080)){
            while(true){
                System.out.println("等待客户端连接....");
                Socket socket = server.accept();
                socket.setSoTimeout(10000); // 通过调用setSoTimeout()方法来设定I/O超时时间
                System.out.println("客户端已连接 ip地址为: " + socket.getInetAddress().getHostAddress());
                System.out.println("读取客户端数据: ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(reader.readLine());

                OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
                writer.write("已接受到数据\n");
                writer.flush();
                writer.close();
            }
        } catch (IOException e){
            System.out.println();
            e.printStackTrace();
        }

    }
}
