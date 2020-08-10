package cn.tedu.store.mapper;


import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;


/**
 * 处理购物车数据的持久层接口
 */
public interface CartMapper {
    /**
     * 插入一条购物车数据
     *
     * @param cart 购物车数据
     * @return 影响行数
     */
    Integer insert(Cart cart);

    /**
     * 修改购物车某条数据的数量
     *
     * @param cid 购物车Id
     * @param num 新增数量
     * @return 影响行数
     */
    Integer updateNumByCid(
            @Param("cid") Integer cid,
            @Param("num") Integer num,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

    /**
     * 查找是否有该商品信息
     *
     * @param uid
     * @param pid
     * @return 找到的购物车数据
     */
    Cart findByUidAndPid(@Param("uid") Integer uid,
                         @Param("pid") Integer pid);

    /**
     * 显示购物车列表
     *
     * @param uid
     * @return
     */
    List<CartVO> findVOByUid(Integer uid);

    /**
     * 查询需要操作的购物车数据
     * @param cid
     * @return
     */
    Cart findByCid(Integer cid);

    /**
     * 查询多个购物车数据
     * @param cids
     * @return
     */
    List<CartVO> findVOByCids(Integer[] cids);
}
