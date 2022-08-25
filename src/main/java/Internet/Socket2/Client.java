package Internet.Socket2;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        // 客户端
        /*try (Socket socket = new Socket("localhost", 8080);
             Scanner scanner = new Scanner(System.in)){

            System.out.println("已连接到服务端");
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream()); // 通过转换流来帮助我们快速写入内容
            writer.write(scanner.nextLine() + "\n"); // 因为对方是readLine()这里加个换行符
            writer.flush(); // 刷新
            System.out.println("数据已发送");
            writer.close();
        } catch (IOException e) {
            System.out.println("客户端连接失败");
            e.printStackTrace();
        }*/

        // 同理 既然服务端可以读取客户端的内容 客户端也可以在发送后等待服务端给与响应
        /*try (Socket socket = new Socket("localhost", 8080);
             Scanner scanner = new Scanner(System.in)){

            System.out.println("已连接到服务端");
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write(scanner.nextLine() + "\n");
            writer.flush();

            System.out.println("数据已发送 等待服务端确认....");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("收到服务端响应" + reader.readLine());
            writer.close();
        } catch (IOException e) {
            System.out.println("客户端连接失败");
            e.printStackTrace();
        }*/

        /**
         * 可以手动关闭单向的流
         * socket.shutdownInput(); // 关闭输出方向的流
         * socket.shutdownOutput(); // 关闭输入方向的流
         */

        /**
         * 如果连接的双方发生意外而通知不到对方 导致一方还持有连接 这样就会占用资源 因此我们可以使用setKeepAlive()方法来防止情况发送
         *
         * 当客户端连接后 如果设置了KeepLive为true 当对方没有发送任何数过来 超过一个时间(看系统内核参数配置)
         * 那么我们这边会发送一个ack探测包到对方 探测双方的TCP/IP连接是否有效
         */
        try (Socket socket = new Socket();
             Scanner scanner = new Scanner(System.in)){
            socket.setKeepAlive(true);
            socket.connect(new InetSocketAddress("localhost", 8080));

            System.out.println("已连接到服务端");
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write(scanner.nextLine() + "\n");
            writer.flush();

            System.out.println("数据已发送 等待服务端确认....");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("收到服务端响应" + reader.readLine());
            writer.close();
        } catch (IOException e){
            System.out.println("客户端连接失败");
            e.printStackTrace();
        }

    }
}
