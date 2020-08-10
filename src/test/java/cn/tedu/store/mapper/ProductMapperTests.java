package cn.tedu.store.mapper;

import cn.tedu.store.entity.Product;
import cn.tedu.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperTests {

    @Resource
    private ProductMapper mapper;



    @Test
    public void findByUsername() {
        List<Product> list = mapper.findHotList();
        for (Product product : list) {

            System.err.println(product);
        }
    }

}