package cn.tedu.store.mapper;


import cn.tedu.store.entity.Product;

import java.util.List;

/**
 * 处理商品数据的持久层接口
 */
public interface ProductMapper {
    /**
     * 获取热销排行的前4个商品
     *
     * @return 热销排行的前4个商品
     */
    List<Product> findHotList();

    /**
     * 根据Id获取商品信息
     *
     * @param id 商品Id
     * @return 商品信息
     */
    Product findById(Integer id);
}
