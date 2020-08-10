package cn.tedu.store.mapper;

import cn.tedu.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 处理用户数据的持久层接口
 */
public interface UserMapper {
    /**
     * 插入用户数据
     *
     * @param user 用户数据
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户uid修改个人信息并记录修改人和修改密码
     *
     * @param user 用户输入的信息
     * @return 返回修改影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * 根据用户uid修改密码并记录修改人和修改密码
     *
     * @param uid          用户名
     * @param password     修改后的密码
     * @param modifiedUser 修改的人
     * @param modifiedTime 修改的时间
     * @return 返回修改影响的行数
     */
    Integer updatePasswordByUid(@Param("uid") Integer uid,
                                @Param("password") String password,
                                @Param("modifiedUser") String modifiedUser,
                                @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户uid修改头像并记录修改人和修改密码
     *
     * @param uid          用户uid
     * @param avatar       修改后的头像
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户uid查询用户数据
     *
     * @param uid 用户id
     * @return 匹配的用户数据，如果没有匹配的数据，则返回null
     */
    User findByUid(Integer uid);

    /**
     * 根据用户名查询用户数据
     *
     * @param username 用户名
     * @return 匹配的用户数据，如果没有匹配的数据，则返回null
     */
    User findByUsername(String username);
}
