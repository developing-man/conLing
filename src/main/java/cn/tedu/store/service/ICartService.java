package cn.tedu.store.service;


import cn.tedu.store.entity.Product;
import cn.tedu.store.vo.CartVO;

import java.util.List;

/**
 * 处理商品数据的业务层接口
 */
public interface ICartService {

    /**
     * 添加商品到购物车
     *
     * @param pid
     * @param amount
     * @param uid
     * @param username
     */
    void addToCart(Integer pid, Integer amount, Integer uid, String username);

    /**
     * 显示购物车列表
     *
     * @param uid
     * @return
     */
    List<CartVO> getVOByUid(Integer uid);

    /**
     * 增加商品的数量
     *
     * @param cid
     * @param uid
     * @param username
     * @return
     */
    Integer addNum(Integer cid, Integer uid, String username);

    /**
     * 查询多个购物车数据
     * @param cids
     * @return
     */
    List<CartVO> getVOByCids(Integer[] cids, Integer uid);
}
