package MyBatis.mybatis1;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * XML语言概述
 * 在开始介绍Mybatis之前 XML语言发明最初是用于数据的存储和传输 它可以长这样:
 *
 *                  <?xml version="1.0" encoding="UTF-8" ?>
 *                  <outer>
 *                      <name>阿伟</name>
 *                      <desc>怎么又在玩电动啊</desc>
 *                      <inner type="1">
 *                          <age>20</age>
 *                          <sex>男</sex>
 *                      </inner>
 *                  </outer>
 *
 * 如果你学习过前端知识 你会发现它和HTML几乎一模一样 但是注意 虽然它们长的差不多 但是他们的意义不同
 * HTML主要用于通过编排来展示数据 而XML主要是存放数据 它更像是一个配置文件 当然 浏览器也是可以直接打开XML文件的
 *
 * 一个XML文件存在以下的格式规范
 *
 *      > 必须存在一个根节点 将所有的子标签全部包含
 *      > 可以但不必须包含一个头部声明(主要是可以设定编码格式)
 *      > 所有的标签必须成对出现 可以嵌套但是不能交叉嵌套
 *      > 区分大小写
 *      > 标签中可以存在属性 比如上面的type="1"就是inner标签的一个属性 属性的值由单引号或双引号包括
 *
 * XML文件也可以使用注释:
 *
 *                  <?xml version="1.0" encoding="UTF-8" ?>
 *                  <!-- 注释内容 -->
 *
 * 通过IDEA我们可以使用 Ctrl + / 来快速添加注释文本(不仅仅适用于XML 还支持很多种类型的文件)
 *
 * 那如果我们的内容中出现了 < 或是 > 字符 那该怎么办呢? 我们就可以使用XML的转义字符来代替:
 *
 *                  &amp: 或 &       &      和
 *                  &1t; 或 <        <      小于号
 *                  &gt; 或 >        >      大于号
 *                  &quot;          "       双引号
 *                  &nbsp;          ©       版权符
 *                  &reg;           ®       注册符
 *
 * 如果嫌一个一个该太麻烦 也可以使用CD来快速创建不解析区域:
 *
 *                  <test>
 *                      <name><![CDATA[我看你<><><>是一点都不懂哦>>>]]></name>
 *                  </test>
 *
 * 那么 我们现在了解了XML文件的定义 现在该如何去解析一个XML文件呢? 比如我们希望将定义好的XML文件读取到Java程序中 这时该怎么做呢?
 *
 * JDK为我们内置了一个叫做 org.w3c 的XML解析库 我们来看看如何使用它来进行XML解析文件内容:
 *
 *                  // 创建DocumentBuilderFactory对象
 *                  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 *
 *                  // 创建DocumentBuilder对象
 *                  try {
 *                      DocumentBuilder builder = factory.newDocumentBuilder();
 *                      Document d = builder.parse("file:test.xml");
 *                      // 每一个标签都作为一个节点
 *                      NodeList nodeList = d.getElementsByTagName("outer"); // 可能有很多个名字为test的标签
 *                      Node rootNode = nodeList.item(0); // 获取首个
 *
 *                      NodeList childNodes = rootNode.getChildNodes(); // 一个节点下可能会有很多个节点 比如根节点下就囊括了所有的节点
 *                      // 节点可以是一个带有内容的标签(它内部就还有子节点) 也可以是一段文本内容
 *
 *                      for (int i = 0; i < childNodes.getLength(); ++i){
 *                          Node child = childNodes.item(i);
 *                          if(child.getNodeType() == Node.ELEMENT_NODE) // 过滤换行符之类的内容 因为它们都被认为是一个文本节点
 *                              System.out.println(child.getNodeName() + ": " + child.getFirstChild().getNodeValue());
 *                          // 输出节点名称 也就是标签名称 以及标签内部的文本(内容的内容都是子节点 所有要获取内部的节点)
 *                      }
 *                  } catch (Exception e){
 *                      e.printStackTrace();
 *                  }
 *
 * 当然 学习和使用XML只是为了更好地去认识Mybatis的工作原理 以及如何使用XML来作为Mybatis的配置文件 这是在开始之前必须要掌握的内容
 * (使用java读取XML内容不要求掌握 但是要知道Mybatis就是通过这种方式来读取配置文件的) 不仅仅是mybatis 包括后面的Spring等众多框架都会用到XML作为框架的配置文件
 */
public class Test {

    public static void main(String[] args) {

        // 创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 创建DocumentBuilder对象
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d = builder.parse("file:test.xml");
            // 每一个标签都作为一个节点
            NodeList nodeList = d.getElementsByTagName("outer"); // 可能有很多个名字为test的标签
            Node rootNode = nodeList.item(0); // 获取首个

            NodeList childNodes = rootNode.getChildNodes(); // 一个节点下可能会有很多个节点 比如根节点下就囊括了所有的节点
            // 节点可以是一个带有内容的标签(它内部就还有子节点) 也可以是一段文本内容

            for (int i = 0; i < childNodes.getLength(); ++i){
                Node child = childNodes.item(i);
                if(child.getNodeType() == Node.ELEMENT_NODE) // 过滤换行符之类的内容 因为它们都被认为是一个文本节点
                    System.out.println(child.getNodeName() + ": " + child.getFirstChild().getNodeValue());
                // 输出节点名称 也就是标签名称 以及标签内部的文本(内容的内容都是子节点 所有要获取内部的节点)
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
