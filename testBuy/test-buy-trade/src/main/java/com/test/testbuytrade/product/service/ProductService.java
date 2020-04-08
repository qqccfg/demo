package com.test.testbuytrade.product.service;

import com.test.testbuytrade.product.entity.Category;
import com.test.testbuytrade.product.entity.PageSearch;
import com.test.testbuytrade.product.entity.Product;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author JackLei
 * @Title: ProductService
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/1822:19
 **/
@Service
public interface ProductService {
    List<Category> listCategrory();

    List<Product> listProduct(Long categoryId);

    List<Product> searchProduct(String searchName, int pageSize, int searchNumber) throws IOException;

    Product productDetail(Long productId);
}
