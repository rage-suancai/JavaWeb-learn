package Maven.maven4;

/**
 * Maven继承关系
 * 一个Maven项目可以继承自另一个Maven项目 比如多个子项目都需要父项目的依赖 我们就可以使用继承关系来快速配置
 *
 * 我们右键左侧栏 新建一个模块 来创建一个子项目:
 *              <?xml version="1.0" encoding="UTF-8"?>
 *              <project xmlns="http://maven.apache.org/POM/4.0.0"
 *                       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *                       xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
 *                  <parent>
 *                      <artifactId>javaWeb</artifactId>
 *                      <groupId>javaWeb</groupId>
 *                      <version>1.8-SNAPSHOT</version>
 *                  </parent>
 *                  <modelVersion>4.0.0</modelVersion>
 *
 *                  <artifactId>javaWebInherit</artifactId>
 *
 *                  <properties>
 *                      <maven.compiler.source>14</maven.compiler.source>
 *                      <maven.compiler.target>14</maven.compiler.target>
 *                  </properties>
 *
 *              </project>
 * 我们可以看到 IDEA默认给我们添加了一个parent节点 表示Maven项目父Maven项目的子项目 子项目直接继承父项目的groupId 子项目会直接继承父项目的所有依赖
 * 除非依赖添加了optional标签 我们来编写一个测试用例尝试一下:
 *              @Log
 *              public class Main {
 *                   public static void main(String[] args) {
 *                      log.info("测试");
 *                   }
 *              }
 * 可以看到 子项目也成功继承了Lombok依赖
 *
 * 我们还可以让父Maven项目统一管理所有的依赖 包括版本号等 子项目可以选取需要的作为依赖 而版本全由父项目管理
 * 我们可以将dependencies全部放入dependencyManagement节点 这样父项目就完全作为依赖统一管理:
 *              <dependencyManagement>
 *                  // 依赖坐标
 *              </dependencyManagement>
 *
 * 我们发现 子项目的依赖失败了 因为现在父项目没有依赖 而是将所有的依赖进行集中管理 子项目需要上面再拿上面即可
 * 同时子项目无需无需指定版本 所有的版本全部由父项目决定 子项目只需要使用即可
 *
 * 当然 父项目如果还存在dependencies节点的话 里面的内依赖依然是直接继承
 */
public class Test {

    static void test1() {

    }

    public static void main(String[] args){
        test1();
    }

}
