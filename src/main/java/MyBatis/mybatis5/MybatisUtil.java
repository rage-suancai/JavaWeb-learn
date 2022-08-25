package MyBatis.mybatis5;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MybatisUtil {
    // 由于SqlSessionFactory一般只需要创建一次 因此我们可以创建一个工具类来集中创建SqlSession 这样会更加方便一些
    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(new FileInputStream("mybatis-config.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个新的会话
     * @param autoCommit 是否开启自动提交 (跟JDBC是一样的 如果不自动提交 则会变成事务操作)
     * @return SqlSession对象
     */
    public static SqlSession getSession(boolean autoCommit) {
        return sqlSessionFactory.openSession(autoCommit);
    }
}
