package Lombok.lombok2;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 使用Lombok
 * 我们通过实战来演示一下Lombok的实用注解
 *  > 我们通过添加@Getter和@Setter来为当前类的所有字段生成get/set方法 它们可以添加到类或是字段上 注意静态字段不会生成 final字段无法生产set方法
 *      > 我们还可以使用@Accessors来控制生成Getter和Setter的样式
 *  > 我们通过添加@ToString来为当前类生成预设的toString方法
 *  > 我们可以通过添加@EqualsAndHashCode来快速生成比较和哈希值方法
 *  > 我们可以通过添加@AllArgsConstructor和@NoArgsConstructor来快速生成全参构造和无参构造
 *  > 我们可以添加@RequiredArgsConstructor来快速生成参数只包含final或被标记为@NonNull的成员字段
 *  > 使用@Date能代表@Setter @Getter @RequiredArgsConstructor @ToString @EqualsAndHashCode全部注解
 *      > 一旦使用@Date就不建议此类有继承关系 英文equal方法可能不符合预期结果(尤其是仅比较子类属性)
 *  > 使用@Cleanup作用与局部变量 在最后自动调用其close()方法 (可以自由更换)
 *  > 使用@SneakyThrows来自动生成try-catch代码块
 *  > 使用@Builder来快速生成建造者模式
 *      > 通过使用@Builder.Defoult来指定默认值
 *      > 通过使用@Builder.ObtoinVia来指定默认值的获取方式
 */

@Setter
@Getter
@Accessors(fluent = true)
public class Student {
    Integer sid;
    String name;
    String sex;
}
