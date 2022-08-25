package Maven.maven3;

/**
 * Maven可选依赖
 * 当项目在的某些依赖不希望被使用此项目作为依赖的项目使用时 我们可以给依赖添加optional标签表示此依赖是可选的 默认在导入依赖时 不会导入可选依赖:
 *                 <optional>true</optional>
 *
 * 比如Mybatis的POM文件中 就存在大量的可选依赖:
 *                 <dependency>
 *                      <groupId>org.slf4j</groupId>
 *                      <artifactId>slf4j-api</artifactId>
 *                      <version>1.7.30</version>
 *                      <optional>true</optional>
 *                 </dependency>
 *                 <dependency>
 *                      <groupId>org.slf4j</groupId>
 *                      <artifactId>slf4j-log4j12</artifactId>
 *                      <version>1.7.30</version>
 *                      <optional>true</optional>
 *                 </dependency>
 *                 <dependency>
 *                      <groupId>log4j</groupId>
 *                      <artifactId>log4j</artifactId>
 *                      <version>1.2.17</version>
 *                      <optional>true</optional>
 *                 </dependency>
 * 由于Mybatis要支持多种类型的日志 需要用到很多种不同的日志框架 因此需要导入这些依赖来做兼容 但是我们项目中并不一定会使用这些日志框架作为Mybatis的日志打印器
 * 因此这些日志框架仅Mybatis内部做兼容需要导入使用 而我们可以选择不使用这些框架或是选择其中一个即可 也就是说我们导入Mybatis之后想用上面日志框架再自己加就可以了
 *
 * Maven排除依赖
 * 我们了解了可选依赖 现在我们可以让使用此项目作为依赖的项目默认不适用可选依赖 但是如果存在那种不是可选依赖 但是我们导入此项目不希望使用此依赖该怎么办呢
 * 这个时候我们就可以通过排除依赖来防止添加不必要的依赖:
 *                  <dependency>
 *                      <groupId>org.junit.jupiter</groupId>
 *                      <artifactId>junit-jupiter</artifactId>
 *                      <version>5.8.1</version>
 *                      <scope>test</scope>
 *                      <exclusions>
 *                          <exclusion>
 *                              <groupId>org.junit.jupiter</groupId>
 *                              <artifactId>junit-jupiter-engine</artifactId>
 *                          </exclusion>
 *                      </exclusions>
 *                  </dependency>
 * 我们这里演示了排除Junit的一些依赖 我们可以在外部库中观察排除之后和之前的效果
 */
public class Test {

    static void test1() {

    }

    public static void main(String[] args){
        test1();
    }

}
