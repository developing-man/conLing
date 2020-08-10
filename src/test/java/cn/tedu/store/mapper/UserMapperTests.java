package cn.tedu.store.mapper;

import cn.tedu.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {

    @Resource
    private UserMapper mapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("root");
        user.setPassword("1234");
        Integer rows = mapper.insert(user);
        System.err.println("rows=" + rows);
    }

    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(9);
        user.setPhone("13800138019");
        user.setEmail("root@tedu.cn");
        user.setGender(1);
        user.setModifiedUser("系统管理员");
        user.setModifiedTime(new Date());
        Integer rows = mapper.updateInfoByUid(user);
        System.err.println("rows=" + rows);
    }

    @Test
    public void updateAvatarByUid() {
        Integer uid = 9;
        String avatar = "123456";
        String username = "我是超管";
        Date date = new Date();
        Integer rows = mapper.updateAvatarByUid(uid, avatar, username, date);
        System.err.println("影响的行数:" + rows);
    }

    @Test
    public void findByUid() {
        User user = mapper.findByUid(5);
        System.err.println(user);
    }

    @Test
    public void findByUsername() {
        String username = "admin";
        User user = mapper.findByUsername(username);
        System.err.println(user);
    }

}