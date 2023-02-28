package Internet.Socket4;

import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 使用浏览器访问Socket服务器
 * 在了解了如何使用Socket传输文件后 我们来看看 浏览器是如何向服务器发起请求的:
 *
 *                  try (ServerSocket server = new ServerSocket(8080)){
 *                      System.out.println("正在等待用户端连接....");
 *                      Socket socket = server.accept();
 *                      System.out.println("客户端已连接 ip地址为: " + socket.getInetAddress().getHostAddress());
 *
 *                      InputStream in = socket.getInputStream(); // 通过
 *                      System.out.println("接收到客户端数据: ");
 *                      while (true){
 *                          int i = in.read();
 *                          if (i == -1) break;
 *                          System.out.print((char) i);
 *                      }
 *                  } catch (Exception e){
 *                      System.out.println("客户端连接失败");
 *                      e.printStackTrace();
 *                  }
 *
 * 我们现在打开浏览器 输入http://localhost:8080或是http://127.0.0.1:8080/ 来连接我们本地开放的服务器
 *
 * 我们发现浏览器是无法打开这个链接的 但是我们服务器却收到了不少信息
 *
 * 实际上这些内容都是http协议规定的请求头内容 HTTP是一种应用层协议 全称为超文本传输协议 它本质也是基于TCP协议进行数据传输
 * 因此我们的服务端就能够读取HTTP请求 但是HTTP协议并不会保持长连接 在得到我们响应的数据后会立即关闭TCP连接
 *
 * 既然使用的是HTTP连接 如果我们的服务器要支持响应HTTP请求 那么就需要按照HTTP协议的规定 返回一个规范的响应文本 首先是响应头 它至少要包含一个响应码
 *
 *                 HTTP/1.1 200 Accpeted
 *
 * 然后就是响应内容(注意一定要换行再写) 我们来尝试来编写一下支持HTTP协议的响应内容:
 *
 *                  try (ServerSocket server = new ServerSocket(8080)){
 *                      System.out.println("正在等待客户端连接....");
 *                      Socket socket = server.accept();
 *                      System.out.println("客户端已连接 IP地址为: " + socket.getInetAddress().getHostAddress());
 *                      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(socket.getInputStream())); // 通过
 *                      System.out.println("接收到客户端数据: ")
 *                      while (reader.ready()) System.out.println(reader.readLine()); // ready是判断当前流中是否还有可读内容
 *                      OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
 *                      writer.write("HTTP/1.1 200 Accepted\r\n"); //
 *                      writer.write("\r\n"); // 在请求头写完后还要进行一次换行 然后写入我们的响应实体(会在浏览器是展示一的内容)
 *                      writer.write("yxsnb");
 *                      writer.flush();
 *                  } catch (Exception e){
 *                      e.printStackTrace();
 *                  }
 *
 * 我们可以可打开浏览器的开发者模式(这里推荐使用Chrome/Edge浏览器 按下F12即可打开) 我们来观察一下浏览器的实际请求过程
 */
public class Server {

    public static void main(String[] args) {

        /*try (ServerSocket server = new ServerSocket(8080)){
            System.out.println("正在等待用户端连接....");
            Socket socket = server.accept();
            System.out.println("客户端已连接 ip地址为: " + socket.getInetAddress().getHostAddress());

            InputStream in = socket.getInputStream(); // 通过
            System.out.println("接收到客户端数据: ");
            while (true){
                int i = in.read();
                if (i == -1) break;
                System.out.print((char) i);
            }
        } catch (Exception e){
            System.out.println("客户端连接失败");
            e.printStackTrace();
        }*/

        try (ServerSocket server = new ServerSocket(8080)){
            System.out.println("正在等待客户端连接....");
            Socket socket = server.accept();
            System.out.println("客户端已连接 IP地址为: " + socket.getInetAddress().getHostAddress());

            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write("HTTP/1.1 200 Accepted\r\n"); // 200是响应码 HTTP响应规定200为接受请求 400为错误的请求 404 为找不到此资源(不止这些 还有很多)
            //writer.write("Content-Type:text/html");
            writer.write("\r\n"); // 在请求头写完后还要进行一次换行 然后写入我们的响应实体(会在浏览器是展示一的内容)
            writer.write(" what's up ");
            writer.flush();

            writer.close();
            socket.close();
        } catch (Exception e){
            System.out.println("客户端连接失败");
            e.printStackTrace();
        }

    }

}
