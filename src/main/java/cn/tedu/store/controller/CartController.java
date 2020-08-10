package cn.tedu.store.controller;


import cn.tedu.store.service.ICartService;
import cn.tedu.store.util.JsonResult;
import cn.tedu.store.vo.CartVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 控制产品信息的控制器
 */
@RequestMapping("carts")
@RestController
public class CartController extends BaseController {
    @Resource
    private ICartService cartService;

    @RequestMapping("add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer num, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        cartService.addToCart(pid, num, uid, username);
        return new JsonResult<>(OK);
    }

    @GetMapping({"/", ""})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<CartVO> data = cartService.getVOByUid(uid);
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid,
                                      HttpSession session){
        //从session中取出uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //调用业务对象执行增加
        Integer data = cartService.addNum(cid, uid, username);
        //返回成功
        return new JsonResult<>(OK,data);
    }

    @GetMapping("get_by_cids")
    public JsonResult<List<CartVO>> getVOByCids(
            Integer[] cids, HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<CartVO> data = cartService.getVOByCids(cids, uid);
        return new JsonResult<>(OK, data);
    }

}



