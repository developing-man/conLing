package cn.tedu.store.service;

import cn.tedu.store.controller.BaseController;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests extends BaseController {

    @Resource
    IUserService service;

    /**
     * 测试注册
     */
    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("service1");
            user.setPassword("123456");
            service.reg(user);
            System.err.println("ok!");
        } catch (ServiceException e) {
            System.err.println(e.getClass());
        }
    }

    /**
     * 测试登录
     */
    @Test
    public void login() {
        try {
            String username = "service1";
            String password = "123456";
            service.login(username, password);
            System.err.println("ok!");
        } catch (ServiceException e) {
            System.err.println(e.getClass());
        }
    }

    /**
     * 测试修改密码
     */
    @Test
    public void changePassword() {
        try {
            Integer uid = 5;
            String username = "密码管理员";
            String oldPassword = "1234";
            String newPassword = "12345";
            service.changePassword(uid, username, oldPassword, newPassword);
            System.err.println("ok!");
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * 测试查询
     */
    @Test
    public void getByUid() {
        try {
            Integer uid = 9;
            User user = service.getByUid(uid);
            System.err.println(user);
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * 测试修改用户信息
     */
    @Test
    public void changeInfo() {
        try {
            Integer uid = 8;
            String username = "数据管理员";
            User user = new User();
            user.setGender(0);
            user.setPhone("13900139008");
            user.setEmail("user20@tedu.cn");
            service.changeInfo(uid, username, user);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getSimpleName());
            System.err.println(e.getMessage());
        }
    }

    /**
     * 测试修改用户信息
     */
    @Test
    public void changeAvatar() {
        try {
            Integer uid = 8;
            String username = "数据管理员";
            String avatar = "121212";
            service.changeAvatar(uid, username, avatar);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getSimpleName());
            System.err.println(e.getMessage());
        }
    }

    /**
     * 测试MD5摘要算法
     */
    @Test
    public void md5Tests() {
        String pwd = "123456";
        // 生成一个MD5加密计算摘要
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 计算md5函数
        md.update(pwd.getBytes());
        System.out.println(new BigInteger(1, md.digest()).toString(16));

    }

}