package JUnit.junit3;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Properties配置文件
 * Properties文件是java的一种配置文件 我们之前学习了XML 但是我们只发现XML配置文件读取实在是太麻烦
 * 那么能否有一种简单的配置文件呢 我们可以使用Properties文件:
 *
 *          name=Test
 *          desc=Description
 *
 * 该文件配置很简单 格式为配置项=配置值 我们可以直接通过Properties类来将其读取为一个类似于Map一样的对象:
 *
 *          Properties properties = new Properties();
 *          properties.load(new FileInputStream("test.properties"));
 *          System.out.println(properties);
 *
 * 我们发现 Properties类是继承自Hashtable 而Hashtable是实现的Map接口 也就是说 Properties本质上就是一个Map一样的结构
 * 它会把所有的配置项映射为一个Map 这样我们就可以快速地读取对应配置的值了
 *
 * 我们也可以将已经存在的Properties对象放入输出流进行保存 我们这里就不保存文件了 而是直接打印到控制台 我们只需要提供输出流即可:
 *
 *          Properties properties = new Properties();
 *          properties.setProperty("test", "nb");
 *          properties.put("yxs", "nb");
 *          properties.store(System.out, "????");
 *          properties.storeToXML(System.out, "????");
 *
 * 我们可以通过System.getProperties()获取系统的参数 我们来看看:
 *
 *          System.getProperties().store(System.out, "系统信息");
 *          System.getProperties().forEach((k, v)-> System.out.println(k + ":" + v));
 */
public class Test {

    static void test1() throws IOException{
        Properties properties = new Properties();
        properties.load(new FileInputStream("test.properties"));

        //System.out.println(properties);
        //System.out.println(properties.get("name"));
        //System.out.println(properties.getProperty("name"));
        //System.out.println(properties.getProperty("names", "yxs"));

        /*properties.put("yxs", "nb");
        System.out.println(properties);*/

        properties.put("yxs", null);
        System.out.println(properties);
        /*Map<String, String> map = new HashMap<>();
        map.put("yxs", null);
        System.out.println(map);*/
    }

    static void test2() throws IOException{
        Properties properties = new Properties();
        //properties.load(new FileInputStream("test.properties"));

        //properties.setProperty("test", "nb"); // 和put效果一样
        //properties.put("yxs", "nb");
        //properties.store(System.out, "test message");
        properties.storeToXML(System.out, "test message"); // 保存为XML格式
    }

    static void test3() throws IOException{
        System.getProperties().forEach((k, v)-> System.out.println(k + ":" + v));
    }

    public static void main(String[] args) throws IOException {
        //test1();
        //test2();
        test3();
    }

}
