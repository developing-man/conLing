package cn.tedu.store.service;

import cn.tedu.store.entity.District;
import cn.tedu.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictServiceTests {
    @Resource
    private IDistrictService service;

    @Test
    public void getByParent() {
        try {
            String parent = "86";
            List<District> list = service.getByParent(parent);
            System.err.println("count=" + list.size());
            for (District item : list) {
                System.err.println(item);
            }
        } catch (ServiceException e) {
            System.err.println(e.getClass().getSimpleName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void getNameByCode() {
        try {
            String code = "320411";
            String name = service.getNameByCode(code);
            System.err.println(name);
        } catch (ServiceException e) {
            System.err.println(e.getClass().getSimpleName());
            System.err.println(e.getMessage());
        }
    }
}