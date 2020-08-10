package cn.tedu.store.service.impl;

import cn.tedu.store.entity.Product;
import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.ProductMapper;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IProductService;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 处理商品数据的业务层实现类
 */
@Service
public class ProductServiceImpl implements IProductService {
    @Resource
    ProductMapper productMapper;

    @Override
    public List<Product> getHostList() {
        List<Product> products = findHotList();
        for (Product product : products) {
            product.setStatus(null);
            product.setPriority(null);
            product.setCreatedTime(null);
            product.setCreatedUser(null);
            product.setModifiedTime(null);
            product.setModifiedUser(null);
        }
        return products;
    }


    @Override
    public Product getById(Integer id) {
        Product product = findById(id);
        product.setStatus(null);
        product.setPriority(null);
        product.setCreatedTime(null);
        product.setCreatedUser(null);
        product.setModifiedTime(null);
        product.setModifiedUser(null);
        return product;
    }

    /**
     * 获取热销排行的前4个商品
     *
     * @return 热销排行的前4个商品
     */
    private List<Product> findHotList() {
        return productMapper.findHotList();
    }

    /**
     * 根据Id获取商品信息
     *
     * @param id 商品Id
     * @return 商品信息
     */
    private Product findById(Integer id) {
        return productMapper.findById(id);
    }
}
