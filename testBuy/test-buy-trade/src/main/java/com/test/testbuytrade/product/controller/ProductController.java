package com.test.testbuytrade.product.controller;

import com.test.testbuytrade.common.rese.ApiResult;
import com.test.testbuytrade.product.entity.Category;
import com.test.testbuytrade.product.entity.PageSearch;
import com.test.testbuytrade.product.entity.Product;
import com.test.testbuytrade.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author JackLei
 * @Title: ProductController
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/1822:18
 **/
@RestController
@RequestMapping("Product")
public class ProductController {
    @Autowired
    private ProductService productService;
    /**
     * @Author JackLei
     * @Date cteate in 2018/6/18 23:33
     * @Description 列出分类
    **/
    @RequestMapping("/category")
    public ApiResult<List<Category>> listCategory(){
        ApiResult<List<Category>> result=new ApiResult<>();
        List<Category> list=productService.listCategrory();
        result.setMessage("查询成功");
        result.setData(list);
        return result;
    }


    /**
     * @Author JackLei
     * @Date cteate in 2018/6/18 23:34
     * @Description 列出分类下的产品
    **/
    @RequestMapping("/list/{categoryId}")
    public ApiResult<List<Product>> listProduct(@PathVariable Long categoryId){
        ApiResult<List<Product>> result=new ApiResult<>(200,"查询成功");
        List<Product> listProduct=productService.listProduct(categoryId);
        result.setData(listProduct);
        return result;
    }
    /**
     * @Author JackLei
     * @Date cteate in 2018/6/18 23:37
     * @Description 检索商品
    **/
    @RequestMapping("/search")
    public ApiResult<List<Product>> searchProduct(@RequestBody PageSearch pageSearch) throws IOException {
        ApiResult<List<Product>> result=new ApiResult<>(200,"搜索成功");
        List<Product> list=productService.searchProduct(pageSearch.getSearchName(),pageSearch.getPageSize(),pageSearch.getPageNumber());
        result.setData(list);
        return result;
    }
    /**
     * @Author JackLei
     * @Date cteate in 2018/6/19 22:27
     * @Description 产品详细
    **/
    @RequestMapping("detail/{ProductId}")
    public ApiResult<Product> productDetail(@PathVariable Long ProductId){
        ApiResult<Product> result=new ApiResult<>(200,"查询产品详细成功");
        Product product=productService.productDetail(ProductId);
        return result;
    }
}
