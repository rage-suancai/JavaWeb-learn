package Database.MyBatis.mybatis2.entity;


import lombok.Data;

/**
 * 在根目录下重新创建一个mapper文件夹 新创名为TestMapper.xml的文件作为我们的映射器 并填写以下内容
 * <?xml verssion="1.0" encoding="UTF-8" ?>
 * <!DOCTYPE mapper
 *         PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
 *         "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
 * <mapper namespace="TestMapper.xml">
 *     <select id="selectStudent" resultType="com.test.entity.Student">
 *         select * from student
 *     </select>
 * </mapper>
 *
 * 其中namespace就是命名空间 每个Mapper都是唯一的 因此需要用一个命名空间来区分 它还可以用来绑定一个接口 我们在里面写入了一个select标签
 * 表示添加一个select操作 同时id作为操作的名称resultType指定为我们刚刚定义的实体类 表示将数据库结果映射为Student类 然后在标签中写入我们的查询语句即可
 */
@Data
public class Student {
    int id; // 名称最好和数据库字段名称保持一致 不然可能会映射失败导致查询结果丢失
    String name;
    String sex;
}
