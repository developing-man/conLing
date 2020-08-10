package cn.tedu.store.service.impl;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * 处理用户数据的业务层实现类
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    UserMapper userMapper;

    @Override
    public void reg(User user) {
        // 从参数user中获取username
        String username = user.getUsername();
        // 根据username查询用户数据：
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否不为null
        if (result != null) {
            // 抛出异常：
            throw new UsernameDuplicateException("您尝试注册的用户名'" + username + "'已被占用,请换一个试试!");
        }

        //创建当前时间对象
        Date now = new Date();
        //补全数据:加密后的密码
        //补全数据:盐值
        String salt = UUID.randomUUID().toString();
        user.setSalt(salt);
        String password = user.getPassword();
        String md5Password = getMd5Password(password, salt);
        user.setPassword(md5Password);
        //补全数据:isDelete(0)
        user.setIsDelete(0);
        //补全数据:4项日志属性
        user.setCreatedUser(username);
        user.setCreatedTime(now);
        user.setModifiedUser(username);
        user.setModifiedTime(now);
        // 执行插入用户数据，获取返回值：
        Integer rows = userMapper.insert(user);
        // 判断返回的受影响行数是否不为1
        if (rows != 1) {
            // 抛出异常：
            throw new InsertException("注册失败!请联系管理员!");
        }
    }

    @Override
    public User login(String username, String password) {
        // 根据参数username查询用户数据
        User result = userMapper.findByUsername(username);
        if (result == null) {
            // 判断查询结果是否为null
            // 是：没有与username匹配的数据，用户名不存在，抛出UserNotFoundException，并描述错误
            throw new UserNotFoundException("该用户不存在!");
        }
        if (result.getIsDelete() == 1) {
            // 判断查询结果中的isDelete是否为1
            // 是：用户数据被标记为“已删除”，抛出UserNotFoundException
            throw new UserNotFoundException("该用户已删除!");
        }

        // 从查询结果中获取盐值
        String salt = result.getSalt();
        // 根据参数password和盐值得到加密后的密码(md5Password)
        String md5Password = getMd5Password(password, salt);
        // 判断查询结果中的密码与md5Password是否不一致
        if (!result.getPassword().equals(md5Password)) {
            // 是：密码错误，抛出PasswordNotMatchException
            throw new PasswordNotMatchException("密码不正确!");
        }
        // 创建新的User对象
        User user = new User();
        // 将查询结果中的uid、username、avatar封装到新对象中
        user.setUid(result.getUid());
        user.setUsername(username);
        user.setAvatar(result.getAvatar());
        // 将新User对象返回
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        // 根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        if (result == null) {
            // 判断查询结果是否为null
            // 是：UserNotFoundException
            throw new UserNotFoundException("该用户不存在!");
        }
        if (result.getIsDelete() == 1) {
            // 判断查询结果中的isDelete是否为1
            // 是：UserNotFoundException
            throw new UserNotFoundException("该用已被删除!");
        }

        // 从查询结果中获取盐值
        String salt = result.getSalt();
        // 将参数oldPassword结合盐值加密得到oldMd5Password
        String oldMd5Password = getMd5Password(oldPassword, salt);
        // 判断查询结果中的password和oldMd5Password是否不一致
        if (!result.getPassword().equals(oldMd5Password)) {
            // 是：PasswordNotMatchException
            throw new PasswordNotMatchException("原密码不正确!");
        }

        // 将参数newPassword结合盐值加密得到newMd5Password
        String newMd5Password = getMd5Password(newPassword, salt);
        // 执行更新密码，获取操作的返回值
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
        // 判断返回的受影响行数是否不为1
        if (rows != 1) {
            // 是：UpdateException
            throw new UpdateException();
        }
    }

    @Override
    public User getByUid(Integer uid) {
        // 根据参数uid，调用userMapper的findByUid()，查询数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException();
        }
        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete() == 1) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException();
        }

        // 创建新的User对象
        User user = new User();
        // 将以上查询结果中的username/phone/email/gender封装到新User对象中
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        // 返回新User对象
        return user;
    }

    /**
     * 修改用户信息
     *
     * @param uid      当前用户的uid
     * @param username 当前用户的用户名
     * @param user     新输入的用户信息
     */
    @Override
    public void changeInfo(Integer uid, String username, User user) {
        // 根据参数uid，调用userMapper的findByUid()，查询数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException();
        }
        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete() == 1) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException();
        }

        // 向参数user中补全数据:uid
        user.setUid(uid);
        // 向参数user中补全数据:modifiedUser(username)
        user.setModifiedUser(username);
        // 向参数user中补全数据:modifiedTime(new Date())
        user.setModifiedTime(new Date());
        // 调用userMapper的updateInfoByUid(User user)方法执行修改，并获取返回值
        Integer rows = userMapper.updateInfoByUid(user);
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1) {
            // 是：抛了UpdateException
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }

    }

    /**
     * 修改用户头像
     *
     * @param uid      当前用户uid
     * @param username 当前用户用户名
     * @param avatar   上传的头像地址路径
     */
    @Override
    public void changeAvatar(Integer uid, String username, String avatar) {
        // 根据参数uid，调用userMapper的findByUid()，查询数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException();
        }
        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete() == 1) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException();
        }

        //创建当前时间对象
        Date modifiedTime = new Date();
        //调用userMapper的updateAvatarByUid
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, modifiedTime);
        //判断是否不为1
        if (rows != 1) {
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }

    }


    /**
     * 执行密码加密
     *
     * @param password 原密码
     * @param salt     盐值
     * @return 加密后的密文
     */
    private String getMd5Password(String password, String salt) {
        //加密规则
        //1.无视原密码的强度
        //2.使用UUID作为盐值,在原始密码的左右两侧拼接
        //3.循环加密3次
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
