import com.wzj.mybatis.gernerator.MybatisGeneratorApplication;
import com.wzj.mybatis.gernerator.entity.User;
import com.wzj.mybatis.gernerator.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by wzj on 2023/3/26 12:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisGeneratorApplication.class)
public class MybatisGeneratorApplicationTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert(){
        User user = new User();
        user.setUsername("wzj");
        user.setPassword("wzj");
        user.setSex((byte)0);
        user.setDeleted((byte)0);
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        userMapper.insert(user);
    }
}
