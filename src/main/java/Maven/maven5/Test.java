package Maven.maven5;

/**
 * Maven常用命令
 * 我们可以看到在IDEA在右上角Maven板块中 每个Maven项目都有一个生命周期 实际上这些是Maven的一些插件 每个插件都有各自的功能 比如:
 *      > cleon命令 执行后清理整个target文件夹 在后编写Springboot项目时可以解决一些缓存没更新的问题
 *      > validate命令 可以验证项目的可用性
 *      > compile命令 可以将项目编译为.class文件
 *      > install命令 可以将当前项目安装到本地仓库 以供其他项目导入作为依赖使用
 *      > verify命令 可以按照顺序执行每个默认生命周期阶段(validate compile package等)
 *
 * Maven测试项目
 * 通过使用test命令 可以一键测试所有位于test目录下的测试案例 请注意由以下要求:
 *      > 测试类的名称必须是以Test结尾 比如MainTest
 *      > 测试方法上必须标注@Test注解 实测@RepeatedTest无效
 * 这是由于JUnit5比较新 我们需要重新配置插件升级到高版本 才能完美的兼容Junit5:
 *              <build>
 *                  <plugins>
 *                      <plugin>
 *                          <groupId>org.apache.maven.plugins</groupId>
 *                          <artifactId>maven-surefire-plugin</artifactId>
 *                          <!-- JUnit5 requires Surefire version 2.22.0 or higher -->
 *                          <version>2.22.0</version>
 *                      </plugin>
 *                  </plugins>
 *              </build>
 * 现在@RepeatedTest @BeforeAll也能使用了
 *
 * Maven打包项目
 * 我们的项目在编写完成之后 要么作为Jar依赖 供其他模型使用 要么就作为一个可执行的程序 在控制台运行
 * 我们只需要直接执行package命令就可以直接对项目的代码进行打包 生成jar文件
 *
 * 当然 以上方式仅适用于作为jar依赖的情况 如果我们需要打包一个可执行文件 那么我不仅需要将自己编写的类打包到jar中
 * 同时还需要将依赖也一并打包到Jar中 因为我们使用了别人为我们通过的框架 自然也需要运行别人的代码 我们需要使用另一个插件来实现一起打包:
 *              <plugin>
 *                  <artifactId>org.apache.maven-assembly-plugins</artifactId>
 *                  <version>3.1.0</version>
 *                  <configuration>
 *                      <descriptorRefs>
 *                          <descriptorRef>jar-with-dependencies</descriptorRef>
 *                      </descriptorRefs>
 *                      <archive>
 *                         <manifest>
 *                             <addClasspath>true</addClasspath>
 *                             <aminClass>Maven.maven5.Test</aminClass>
 *                         </manifest>
 *                     </archive>
 *                  </configuration>
 *                  <executions>
 *                      <execution>
 *                          <id>make-assembly</id>
 *                          <phase>package</phase>
 *                          <goals>
 *                              <goal>single</goal>
 *                          </goals>
 *                      </execution>
 *                  </executions>
 *              </plugin>
 *
 * 在打包之前也和执行一次test命令 来保证项目能够正常运行 当测试出现问题时 打包将无法完成 我们也可以手动跳过 选择执行Maven目标来手动执行Maven命令
 * 输入 mvn package -Dmaven.test.skip=true来以跳过测试的方法进行打包
 *
 * 最后得到我们的jar文件 在同级目录下输入 java -jar xxx.jar来运行我们打包好的jar可执行程序(xxx代表文件名称)
 *      > deploy命令 用于发布项目到本地仓库和远程仓库 一般情况下用不到 这里就不做讲解了
 *      > site命令 用于生成当前项目的发布站点 暂时不需要了解
 *
 * 我们之前还将讲解了多模块项目 那么多模块下父项目存在一个packing打包类型标签 所有的父级项目的packing都为pom packing默认是jar类型 如果不做配置
 * maven会将该项目打成jar包 作为父级项目 还有一个重要的属性 那就是modules 通过modules标签将项目的所有子项目引用进来 在build父级项目时
 * 会根据子模块的相互依赖关系整理一个build顺序 然后依次进行build
 */
public class Test {

    static void test1() {

    }

    public static void main(String[] args){
        test1();
    }

}
