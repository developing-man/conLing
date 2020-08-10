package cn.tedu.store.service;

import cn.tedu.store.entity.User;

public interface IUserService {
    /**
     * 用户注册
     */
    void reg(User user);

    /**
     * 用户登录
     */
    User login(String username, String password);

    /**
     * 用户修改密码
     */
    void changePassword(Integer uid, String username, String oldPassword, String newPassword);

    /**
     * 获取用户信息
     */
    User getByUid(Integer uid);

    /**
     *修改用户信息
     */
    void changeInfo(Integer uid,String username,User user);

    /**
     * 用户修改头像
     */
    void changeAvatar(Integer uid, String username, String avatar);
}
