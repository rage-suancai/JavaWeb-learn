package Internet.Socket2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 使用Socket进行数据传输
 * 通过Socket对象 我们就可以获取到对应的I/O流进行网络数据传输:
 *
 *                  try (Socket socket = new Socket("localhost", 8080);
 *                       Scanner sc = new Scanner(System.in)) {
 *
 *                       System.out.println("已连接到服务端");
 *                       OutputStream stream = socket.getOutputStream();
 *                       OutputStreamWriter writer = new OutputStreamWriter(stream); // 通过转换流来帮助我们快速写入内容
 *                       System.out.println("请输入要发送给服务端的内容: ");
 *                       String text = sc.nextLine();
 *                       writer.write(text + "\n"); // 因为对方是readLine()这里加个换行符
 *                       writer.flush();
 *                       System.out.println("数据已发送: " + text);
 *                  } catch (IOException e) {
 *                      System.out.println("服务端连接失败");
 *                      throw new RuntimeException(e);
 *                  } finally {
 *                      System.out.println("客户端断开连接");
 *                  }
 *
 *                  try (ServerSocket server = new ServerSocket(8080)) {
 *                       System.out.println("正在等待客户端连接...");
 *                       Socket socket = server.accept();
 *                       System.out.println("客户端已连接 IP地址为: " + socket.getInetAddress().getHostAddress());
 *                       BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 *                       System.out.println("接收到客户端数据: ");
 *                       System.out.println(reader.readLine());
 *                       socket.close(); // 和服务端TCP连接完成之后 记得关闭socket
 *                  } catch (IOException e) {
 *                      throw new RuntimeException(e);
 *                  }
 *
 * 同理 既然服务端可以读取客户端的内容 客户端也可以在发送后等待服务端给予响应:
 *
 *                  try (Socket socket = new Socket("localhost", 8080);
 *                       Scanner sc = new Scanner(System.in)) {
 *
 *                       System.out.println("已连接到服务端");
 *                       OutputStream stream = socket.getOutputStream();
 *                       OutputStreamWriter writer = new OutputStreamWriter(stream); // 通过转换流来帮助我们快速写入内容
 *                       System.out.println("请输入要发送给服务端的内容: ");
 *                       String text = sc.nextLine();
 *                       writer.write(text + "\n"); // 因为对方是readLine()这里加个换行符
 *                       writer.flush();
 *                       System.out.println("数据已发送: " + text);
 *                       BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 *                       System.out.println("收到服务器返回: " + reader.readLine());
 *                  } catch (IOException e) {
 *                      System.out.println("服务端连接失败");
 *                      throw new RuntimeException(e);
 *                  } finally {
 *                      System.out.println("客户端断开连接");
 *                  }
 *
 *                  try (ServerSocket server = new ServerSocket(8080)) {
 *
 *                       System.out.println("正在等待客户端连接...");
 *                       Socket socket = server.accept();
 *                       System.out.println("客户端已连接 IP地址为: " + socket.getInetAddress().getHostAddress());
 *                       BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 *                       System.out.println("接收到客户端数据: ");
 *                       System.out.println(reader.readLine());
 *                       OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
 *                       writer.write("已收到");
 *                       writer.flush();
 *                  } catch (IOException e) {
 *                      throw new RuntimeException(e);
 *                  }
 *
 * 我们可以手动关闭单向的流:
 *
 *                  socket.shutdownOutput(); // 关闭输出方向的流
 *                  socket.shutdownInput(); // 关闭输入方向的流
 *
 * 如果我们不希望服务端等待太长的时间 我们可以通过调用setSoTimeout()方法来设定I超时时间:
 *
 *                  socket。setSoTimeout(3000);
 *
 * 当超过设定时间都依然没有收到客户端或是服务端的数据时 会抛出异常:
 *
 *                  java.net.SocketTimeoutException: Read timed out
 *
 * 我们之前使用的都是通过构造方法直接连接服务端 那么是否可以等到我们想要的时候再去连接呢?
 *
 *                  try (Socket socket = new Socket();
 *                       Scanner sc = new Scanner(System.in)) {
 *
 *                       socket.connect(new InetSocketAddress("localhost", 8080), 1000); // 手动调用connect方法进行连接
 *
 * 如果连接的双方发生意外而通知不到对方 导致一方还持有连接 这样就会占用资源 因此我们可以使用setKeepAlive()方法来防止此类情况发生:
 *
 *                  socket.setKeepAlive(true);
 *
 * 当客户端连接后 如果设置了keeplive为true 当对方没有发生任何数据过来 超过一个时间(看系统内核参数配置) 那么我们这边会发送一个ack探测包发到对方 探测双方的TCP/IP连接是否有效
 *
 * TCP在传输过程中 实际上会有一个缓冲区用于数据的发送和接收:
 *
 *                          应用层            -------[应用A]-------
 *                                           |                  |
 *                         TCP/IP         发送数据            接收数据
 *                                           |                  |
 *                                           |                  |
 *                                     [TCP发送缓存区]      [TCP接收缓冲区]
 *                                           |                  |
 *                                           |                  |
 *                                        发送数据            发送数据
 *                                           |                  |
 *                                           |                  |
 *                         TCP/IP      [TCP接收缓冲区]      [TCP发送缓冲区]
 *                                           |                  |
 *                                           |                  |
 *                                        接收数据             发送数据
 *                                           |                  |
 *                         应用层             -------[应用B]-------
 *
 * 此缓冲区大小为: 8192 我们可以手动调整其大小来优化传输效率:
 *
 *                  socket.setReceiveBufferSize(25565); // TCP接收缓冲区
 *                  socket.setSendBufferSize(25565); // TCP发送缓冲区
 */
public class Server {

    static void test1() {

        try (ServerSocket server = new ServerSocket(8080)) {
             System.out.println("正在等待客户端连接...");
             Socket socket = server.accept();
             System.out.println("客户端已连接 IP地址为: " + socket.getInetAddress().getHostAddress());
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             System.out.println("接收到客户端数据: ");
             System.out.println(reader.readLine());
             socket.close(); // 和服务端TCP连接完成之后 记得关闭socket
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static void test2() {

        try (ServerSocket server = new ServerSocket(8080)) {

             System.out.println("正在等待客户端连接...");
             Socket socket = server.accept();
             System.out.println("客户端已连接 IP地址为: " + socket.getInetAddress().getHostAddress());
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             System.out.println("接收到客户端数据: ");
             System.out.println(reader.readLine());
             OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
             writer.write("已收到");
             writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {



    }

}