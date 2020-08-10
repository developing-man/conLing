package cn.tedu.store.mapper;

import cn.tedu.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictMapperTests {
    @Resource
    private DistrictMapper mapper;

    @Test
    public void findByParent(){
        String parent = "86";
        List<District> list = mapper.findByParent(parent);

        for (District district : list) {
            System.out.println(district);
        }

    }
}
