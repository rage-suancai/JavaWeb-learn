package Maven.maven1;

import Maven.maven1.entity.Student;

/**
 * 使用Maven管理项目
 * 注意 开始之前 看看你C盘空间够不够 最好预留2GB空间以上
 * 吐槽 很多电脑预装系统C盘都给得巨少 就算不装软件 一些软件的缓存文件也能给你塞满 建议有时间重装一下系统重新分配一下磁盘空间
 *
 * Maven翻译为"专家" "内行" 是Apache下的一个纯java开发的开源项目 基于项目对象模型(缩写 POM)概念 Maven利用一个中央信息片断管理一个项目的结构
 * 报告和文档等步骤 Maven是一个项目管理工具 可以对java项目进行构建 依赖管理 Maven也可以被用于构建和管理各自项目 例如C# Ruby Scala和其他语言编写的项目
 * Maven曾经jakarta项目的子项目 现为由Apache软件基金会支持的独立Apache项目
 *
 * 通过Maven 可以帮助我们做:
 *      > 项目的自动构建 包括代码的编译 测试 打包 安装 部署等操作
 *      > 依赖管理 项目使用到哪些依赖 可以快速完成导入
 *
 * 我们之前并没有讲解如何将我们的项目打包为jar文件运行 同时 我们导入依赖的时后 每次都要去下载对应的jar包 这样其实很麻烦的
 * 并且还有可能一个jar包依赖于另一个jar包 就像之前使用JUnit一样 因此我们需要一个更加方便的包管理机制
 *
 * Maven也需要安装环境 但是IDEA已经自带了Maven环境 因此我们不需要再去进行额外的环境安装
 * (无IDEA也能使用Maven 但是配置过程很麻烦 并且我们现在使用的都是IDEA的集成开发环境 所有这里就不讲解Maven命令行操作了) 我们直接创建一个新的Maven项目即可
 *
 * Maven项目构建
 * 那么首先 我们需要了解一下POM文件 它相当于是我们整个Maven项目的配置文件 它是使用XML编写的:
 *              <?xml version="1.0" encoding="UTF-8"?>
 *              <project xmlns="http://maven.apache.org/POM/4.0.0"
 *                       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *                       xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
 *                  <modelVersion>4.0.0</modelVersion>
 *
 *                  <groupId>groupId</groupId>
 *                  <artifactId>javaWeb</artifactId>
 *                  <version>1.0-SNAPSHOT</version>
 *
 *                  <properties>
 *                      <maven.compiler.source>14</maven.compiler.source>
 *                      <maven.compiler.target>14</maven.compiler.target>
 *                  </properties>
 *
 *                  </project>
 * 我们可以看到 Maven的配置文件是以project为根节点 而modelVersion定义了当前模型的版本 一般是4.0.0 我们不用去修改
 *
 * groupId artifactId version这三个元素合在一起 用于唯一区别每个项目 别人如果需要将我们编写的代码作为依赖 那么就必须通过这三个元素来定位我们的项目
 * 我们称为一个项目的基本坐标 所有的项目一般都有自己的Maven坐标 因此我们通过Maven导入其他的依赖只需要填写这三个基本元素就可以了 无需再下载Jar文件 而Maven自动帮助我们下载依赖并导入
 *    > groupId 一般用于指定组名称 命名规则一般和包名一致 比如我们这里使用的是org.example 一个组下面可以有很多个项目
 *    > artifactId 一般用于指定项目在当前组中唯一名称 也就是说在组中用于区分其他项目的标记
 *    > version 代表项目版本 随着我们项目的开发和改进 版本号也会不断更新 我们可以手动指定项目的版本号 其他人使用我们的项目作为依赖时 也可以跟本版本号进行选择
 *              (这里的SNAPSHOT代表快照 一般表示这是一个处于开发中的项目 正式发布项目一般只带版本号)
 *
 * properties中一般都是一些变量和选项的配置 我们这里指定了JDK的源代码和编译版本为14 不需要进行修改
 *
 * Maven依赖导入
 * 现在我们尝试使用Maven来帮助我们快速导入依赖 我们需要导入之前的JDBC JUnit Mybatis Lombok驱动依赖 那么如何使用Maven来管理依赖呢
 * 我们可以创建一个dependencies节点:
 *                  <dependencies>
 *                      // 里面填写的就是所有依赖
 *                  </dependencies>
 * 那么现在就可以向节点中填写依赖了 那么我们如何知道每个依赖的坐标呢 我们可以在https://mvnrepository.com/进行查询(可能打不开 建议用流量 或是直接百度某个项目的Maven依赖)
 * 我们直接搜索相关依赖名称即可 打开后可以看到已经给我们写出了依赖的坐标:
 *                  <dependency>
 *                      <groupId>org.projectlombok</groupId>
 *                      <artifactId>lombok</artifactId>
 *                      <version>1.18.22</version>
 *                      <scope>provided</scope>
 *                   </dependency>
 * 我们直接将其添加到dependencies节点中即可 现在我们来编写一个测试用列看看依赖导入成功了没有:
 *                  public static void main(String[] args) {
 *                      Student student = new Student(6, "春丽", "女");
 *                      System.out.println(student);
 *                  }
 *
 *                  @Data
 *                  @AllArgsConstructor
 *                  public class Student {
 *                      int sid;
 *                      String name;
 *                      String sex;
 *                  }
 * 项目运行成功 表示成功导入了依赖 那么 Maven是如何进行依赖管理呢 以致于如此便捷的导入依赖
 *
 * 一个项目依赖一般是存储在中央仓库中 也有可能存储在一些其他的远程仓库(私服) 几乎所有的依赖都被放到了中央仓库中 因此 Maven可以直接从中央仓库中下载大部分的依赖(Maven第一次导入依赖是需要联网的)
 * 远程仓库中下载之后 会暂时存储在本地仓库 我们会发现我们本地存在一个 .m2文件夹 这就是Maven本地仓库文件夹 默认建立在c盘 如果你c盘空间不足 会出现问题
 * 在下次导入依赖时 如果Maven发现本地仓库中就已经存在某个依赖 那么就不会再去远程仓库下载了
 *
 * 可能再导入依赖时 小伙伴们会出现卡顿的问题 我们建议配置一下IDEA自带的Maven插件远程仓库地址 我们打开IDEA的安装目录 找到安装根目录/plugins/maven/lib/maven3/conf文件夹 找到settings.xml文件 打开编辑:
 * 找到mirros标签 添加以下内容:
 *                  <mirror>
 *                      <id>nexus-aliyun</id>
 *                      <mirrorOf>*</mirrorOf>
 *                      <name>Nexus aliyun</name>
 *                      <url>http://maven.aliyun.com/nexus/content/groups/public</url>
 *                  </mirror>
 */
public class Test {

    static void test1(){
        Student student = new Student(6,"春丽", "女");
        System.out.println(student);
    }

    public static void main(String[] args) {
        test1();
    }

}
