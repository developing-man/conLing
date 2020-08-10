package cn.tedu.store.controller;


import cn.tedu.store.entity.Product;
import cn.tedu.store.service.IProductService;
import cn.tedu.store.util.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 控制产品信息的控制器
 */
@RequestMapping("products")
@RestController
public class ProductController extends BaseController {
    @Resource
    private IProductService productService;

    @GetMapping("hot_list")
    public JsonResult<List<Product>> getHotList() {
        List<Product> data = productService.getHostList();
        return new JsonResult<>(OK, data);
    }

    @GetMapping("{id}/details")
    public JsonResult<Product>  getById(@PathVariable("id") Integer id) {
        // 调用业务对象执行获取数据
        Product product = productService.getById(id);
        // 返回成功和数据
        return new JsonResult<>(OK,product);

    }



}
