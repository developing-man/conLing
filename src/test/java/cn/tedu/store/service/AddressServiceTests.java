package cn.tedu.store.service;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTests {
    @Resource
    IAddressService service;

    @Test
    public void addNew() {
        try {
            Integer uid = 8;
            String username = "zhangsan";
            Address address = new Address();
            address.setProvinceName("湖北省");
            address.setCityName("武汉市");
            service.addnew(uid, username, address);
        } catch (ServiceException e) {
            System.err.println(e.getClass() + "/n" + e.getMessage());
        }
    }

    @Test
    public void setDefault() {
        try {
            Integer aid = 13;
            Integer uid = 9;
            String username = "root";
            service.setDefault(aid, uid, username);

        } catch (ServiceException e) {
            System.err.println(e.getClass() + "/n" + e.getMessage());
        }
    }
}
