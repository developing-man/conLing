package cn.tedu.store.controller;


import cn.tedu.store.entity.Order;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.util.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 控制产品信息的控制器
 */
@RequestMapping("orders")
@RestController
public class OrderController extends BaseController {
    @Resource
    private IOrderService orderService;

    // http://localhost:8080/orders/create?aid=16&cids=1&cids=2&cids=3
    @RequestMapping("create")
    public JsonResult<Order> create(
            Integer aid, Integer[] cids, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Order data = orderService.create(aid, cids, uid, username);
        return new JsonResult<>(OK, data);
    }
}



