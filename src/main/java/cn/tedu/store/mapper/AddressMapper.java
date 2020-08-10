package cn.tedu.store.mapper;

import cn.tedu.store.entity.Address;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.List;


public interface AddressMapper {
    /**
     * 添加一条地址数据
     *
     * @param address 新的地址信息
     * @return 影响的行数
     */
    Integer insert(Address address);


    /**
     * 删除一条收货地址信息
     *
     * @param aid 地址信息编号
     * @return
     */
    Integer deleteByAid(Integer aid);

    /**
     * 将不是默认的设置为默认
     *
     * @param uid 用户的UID
     * @return
     */
    Integer updateNonDefaultByUid(Integer uid);

    /**
     * 设置用户需要的默认地址
     *
     * @param aid
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateDefaultByAid(
            @Param("aid") Integer aid,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);


    /**
     * 根据用户UID查找地址数据的数量
     *
     * @param uid 用户UID
     * @return 地址信息数量
     */
    Integer countByUid(Integer uid);

    /**
     * 根据当前登录用户的UID查找所有的收货地址
     *
     * @param uid
     * @return
     */
    List<Address> findByUid(Integer uid);

    /**
     * 查询某用户最后修改的收货地址详情
     *
     * @param uid 用户的id
     * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
     */
    Address findLastModifiedByUid(Integer uid);

    /**
     * 根据aid找到地址信息
     *
     * @param aid
     * @return
     */
    Address findByAid(Integer aid);
}
