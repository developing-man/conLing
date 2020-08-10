package cn.tedu.store.service.impl;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.entity.Product;
import cn.tedu.store.mapper.CartMapper;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IProductService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.vo.CartVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 处理商品数据的业务层实现类
 */
@Service
public class CartServiceImpl implements ICartService {
    @Resource
    private CartMapper cartMapper;
    @Resource
    private IProductService productService;

    @Override
    public void addToCart(Integer pid, Integer amount, Integer uid, String username) {
        // 根据参数pid和uid查询购物车中的数据
        Cart result = findByUidAndPid(uid, pid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：表示该用户并未将该商品添加到购物车
            // -- 创建Cart对象
            Cart cart = new Cart();
            // -- 封装数据：uid,pid,amount
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            // -- 调用productService.getById()查询商品数据，得到商品价格
            Product product = productService.getById(pid);
            Long price = product.getPrice();
            // -- 封装数据：price
            cart.setPrice(price);
            // -- 封装数据：4个日志
            cart.setCreatedUser(username);
            Date date = new Date();
            cart.setCreatedTime(date);
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);
            // -- 调用insert(cart)执行将数据插入到数据表中
            insert(cart);
        }
        // 否：表示该用户的购物车中已有该商品
        // -- 从查询结果中取出原数量，与参数amount相加，得到新的数量
        Integer num = result.getNum();
        num += amount;
        // -- 执行更新数量
        updateNumByCid(result.getCid(), num, username, new Date());

    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        // 调用findByCid(cid)根据参数cid查询购物车数据
        Cart result = findByCid(cid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出CartNotFoundException
            throw new CartNotFoundException(
                    "尝试访问的购物车数据不存在");
        }

        // 判断查询结果中的uid与参数uid是否不一致
        if (!result.getUid().equals(uid)) {
            // 是：抛出AccessDeniedException
            throw new AccessDeniedException("非法访问");
        }

        // 可选：检查商品的数量是否大于多少(适用于增加数量)或小于多少(适用于减少数量)
        // 根据查询结果中的原数量增加1得到新的数量num
        Integer num = result.getNum() + 1;

        // 创建当前时间对象，作为modifiedTime
        Date now = new Date();
        // 调用updateNumByCid(cid, num, modifiedUser, modifiedTime)执行修改数量
        updateNumByCid(cid, num, username, now);

        // 返回新的数量
        return num;
    }

    @Override
    public List<CartVO> getVOByCids(Integer[] cids, Integer uid) {
        // 基于参数cids查询数据
        List<CartVO> carts = findVOByCids(cids);
        // 通过Iterator遍历查询结果
        Iterator<CartVO> it = carts.iterator();
        while(it.hasNext()) {
            // 检查列表项中的uid与参数uid是否一致
            CartVO cart = it.next();
            if (!cart.getUid().equals(uid)) {
                // 不一致，则从查询结果中移除该数据
                it.remove();
            }
        }
        // 返回查询结果
        return carts;
    }

    /**
     * 插入一条购物车数据
     *
     * @param cart 购物车数据
     */
    private void insert(Cart cart) {
        Integer rows = cartMapper.insert(cart);
        if (rows != 1) {
            throw new InsertException("插入数据异常!请联系管理员!");
        }
    }

    /**
     * 修改购物车某条数据的数量
     *
     * @param cid 购物车Id
     * @param num 新增数量
     * @return 影响行数
     */
    private void updateNumByCid(Integer cid, Integer num, String username, Date now) {
        Integer rows = cartMapper.updateNumByCid(cid, num, username, now);
        if (rows != 1) {
            throw new InsertException("更新数据异常!请联系管理员!");
        }
    }

    /**
     * 查找是否有该商品信息
     *
     * @param uid
     * @param pid
     * @return 找到的购物车数据
     */
    private Cart findByUidAndPid(Integer uid, Integer pid) {
        Cart cart = cartMapper.findByUidAndPid(uid, pid);
        return cart;
    }

    /**
     * 查询某用户的购物车数据
     *
     * @param uid 用户id
     * @return 该用户的购物车数据的列表
     */
    private List<CartVO> findVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    /**
     * 根据购物车数据id查询购物车数据详情
     *
     * @param cid 购物车数据id
     * @return 匹配的购物车数据详情，如果没有匹配的数据，则返回null
     */
    private Cart findByCid(Integer cid) {
        return cartMapper.findByCid(cid);
    }

    /**
     * 查询多个购物车数据
     * @param cids 多个购物车数据的id
     * @return 匹配的购物车数据列表
     */
    private List<CartVO> findVOByCids(Integer[] cids) {
        return cartMapper.findVOByCids(cids);
    }
}
