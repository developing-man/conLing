package cn.tedu.store.service;

import cn.tedu.store.entity.Address;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public interface IAddressService {
    /**
     * 添加新的地址
     */
    void addnew(Integer uid, String username, Address address);


    /**
     * 查询某用户的收货地址数据列表
     *
     * @param uid 用户的id
     * @return 该用户的收货地址数据列表
     */
    List<Address> getByUid(Integer uid);

    /**
     * 设置默认地址
     *
     * @param aid 修改地址的aid
     * @param uid 当前用户的uid
     * @param username 当前用户名
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 删除收货地址
     * @param aid 地址id
     * @param uid 登录用户Id
     * @param username 登录用户名
     */
    void delete(Integer aid, Integer uid, String username);

    /**
     * 获取地址信息
     * @param aid 地址编号
     * @param uid 用户编号
     * @return
     */
    Address getByAid(Integer aid, Integer uid);
}
