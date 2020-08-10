package cn.tedu.store.mapper;

import cn.tedu.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressMapperTests {

    @Resource
    private AddressMapper mapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(9);
        address.setName("root");
        address.setProvinceName("湖北省");

        Integer rows = mapper.insert(address);
        System.out.println("影响的行数:" + rows);
    }

    @Test
    public void countByUid() {
        Integer count = mapper.countByUid(9);
        System.out.println("该用户添加的地址数量:" + count);
    }

    @Test
    public void findByUid() {
        Integer uid = 9;
        List<Address> list = mapper.findByUid(uid);
        System.err.println("count=" + list.size());
        for (Address item : list) {
            System.err.println(item);
        }
    }

    @Test
    public void updateNonDefaultByUid() {
        Integer uid = 9;
        Integer rows = mapper.updateNonDefaultByUid(uid);
        System.err.println("影响的行数:" + rows);
    }

    @Test
    public void updateDefaultByAid() {
        Integer aid = 14;
        Integer rows = mapper.updateDefaultByAid(aid, "管理员", new Date());
        System.err.println("影响的行数:" + rows);
    }

    @Test
    public void findByAid() {
        Integer aid = 14;
        Address address = mapper.findByAid(aid);
        System.err.println("对应的地址信息:" + address);
    }

    @Test
    public void deleteByAid() {
        Integer aid = 20;
        Integer rows = mapper.deleteByAid(aid);
        System.err.println("影响的行数:" + rows);
    }
}

