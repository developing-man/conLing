package cn.tedu.store.service;

import cn.tedu.store.entity.District;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {
    @Resource
    private ICartService service;

    @Test
    public void getVOByUid() {
        Integer uid = 9;
        List<CartVO> list = service.getVOByUid(uid);
        System.err.println("count=" + list.size());
        for (CartVO item : list) {
            System.err.println(item);
        }
    }

    @Test
    public void addNum() {
        try {
            Integer cid = 1;
            Integer uid = 9;
            String username = "不知道";
            Integer num = service.addNum(cid, uid, username);
            System.err.println("OK. New num=" + num);
        } catch (ServiceException e) {
            System.err.println(e.getClass().getSimpleName());
            System.err.println(e.getMessage());
        }
    }
}