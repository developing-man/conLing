package cn.tedu.store.service;


import cn.tedu.store.entity.Product;

import java.util.List;

/**
 * 处理商品数据的业务层接口
 */
public interface IProductService {
    /**
     * 获取热销排行的前4个商品
     *
     * @return 热销排行的前4个商品
     */
    List<Product> getHostList();

    /**
     * 根据Id获取商品信息
     *
     * @param id 商品Id
     * @return 商品信息
     */
    Product getById(Integer id);
}
