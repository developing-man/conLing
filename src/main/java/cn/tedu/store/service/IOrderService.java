package cn.tedu.store.service;


import cn.tedu.store.entity.Order;
import cn.tedu.store.vo.CartVO;

import java.util.List;

/**
 * 处理商品数据的业务层接口
 */
public interface IOrderService {

    Order create(Integer aid, Integer[] cids, Integer uid, String username);
}
