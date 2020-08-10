package cn.tedu.store.mapper;

import cn.tedu.store.entity.Cart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTests {

    @Resource
    private CartMapper mapper;

    @Test
    public void findByUsername() {
        Cart cart = new Cart();
        cart.setUid(9);
        cart.setPid(10000001);
        cart.setPrice(200);
        cart.setNum(20);
        cart.setCreatedUser("root");
        Date date = new Date();
        cart.setCreatedTime(date);
        cart.setModifiedUser("root");
        cart.setModifiedTime(date);

        Integer rows = mapper.insert(cart);
        System.err.println("影响的行数:" + rows);
    }

    @Test
    public void updateNumByCid() {
        Integer rows = mapper.updateNumByCid(1,1,"11",new Date());
        System.err.println("影响的行数:" + rows);
    }

    @Test
    public void findByUidAndPid() {
        Cart result = mapper.findByUidAndPid(9, 10000001);
        System.err.println("影响的行数:" + result);
    }

}