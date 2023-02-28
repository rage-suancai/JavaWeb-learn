package Internet.Socket1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 了解Socket技术
 * 通过Socket技术(它是计算机之间进行通信的"一种约定"或一种方式) 我们就可以实现两台计算机之间的通信 Socket也被翻译为套接字
 * 是操作系统底层提供的一项通信技术 它支持TCP和UDP 而java就对socket底层进行了一套完整的封装 我们就可以通过java来实现Socket通信
 *
 * 要实现Socket通信 我们必须创建一个数据发送者和一个数据接收者 也就是客户端和服务端 我们需要提前启动服务器 来等待客户端的连接 而客户端只需要随时启动去连接服务器即可
 *
 *                  // 服务端
 *                  try (ServerSocket server = new ServerSocket(8080)){ // 将服务端创建在端口8080上
 *                      System.out.println("正在等待客户端连接....");
 *                      Socket socket = server.accept(); // 当没有客户端连接时 线程会阻塞 直到有客户端连接为止
 *                      System.out.println("客户端已连接 ip地址为: " + socket.getInetAddress().getHostAddress());
 *                  } catch (IOException e){
 *                      e.printStackTrace();
 *                  }
 *
 *                  // 客户端
 *                  try (Socket socket = new Socket("localhost", 8080)){
 *                      System.out.println("已连接到服务器");
 *                  } catch (IOException e){
 *                      System.out.println("服务器连接失败");
 *                      e.printStackTrace();
 *                  }
 *
 * 实际上它就是一个TCP连接的过程
 *
 * 一旦TCP连接建立 服务端和客户但端之间就可以相互发送数据 直到客户端主动关闭连接 当然 服务端不仅仅只可以让一个客户端进行连接 我们可以尝试让服务端一直运行来不断接收客户端的连接:
 *
 *                  try (ServerSocket server = new ServerSocket(8080)) { // 将服务端创建在端口8080上
 *                       System.out.println("正在等待客户端连接...");
 *                       while (true) { // 无限循环等待客户端连接
 *                           Socket socket = server.accept();
 *                           System.out.println();
 *                       }
 *                  } catch (IOException e) {
 *                      e.printStackTrace();
 *                  }
 *
 * 现在我们就可以多次去连接此服务端了
 */
public class Server {
    public static void main(String[] args) {

        // 服务端
        /*try (ServerSocket server = new ServerSocket(8080)){ // 将服务端创建在端口8080上
            System.out.println("正在等待客户端连接....");
            Socket socket = server.accept(); // 当没有客户端连接时 线程会阻塞 直到有客户端连接为止
            System.out.println("客户端已连接 ip地址为: " + socket.getInetAddress().getHostAddress());
        } catch (IOException e){
            e.printStackTrace();
        }*/

        try (ServerSocket server = new ServerSocket(8080)){
            while (true){
                System.out.println("正在等待客户端连接....");
                Socket socket = server.accept();
                System.out.println("客户端已连接 ip地址为: " + socket.getInetAddress().getHostAddress());
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
