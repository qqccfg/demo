package com.test.testbuytrade.product.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.google.gson.JsonArray;
import com.test.testbuytrade.common.constants.Constants;
import com.test.testbuytrade.product.dao.CategoryMapper;
import com.test.testbuytrade.product.dao.ProductMapper;
import com.test.testbuytrade.product.entity.Category;
import com.test.testbuytrade.product.entity.PageSearch;
import com.test.testbuytrade.product.entity.Product;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JackLei
 * @Title: ProductServiceImpl
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/1822:20
 **/
@Service
public class ProductServiceImpl implements ProductService{


    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private JestHttpClient esClient;
    private final int PAGESIZE=10;

    private final int SEARCHNUMBER=0;
    /**
     * @Author JackLei
     * @Date cteate in 2018/6/19 20:09
     * @Description 列出分类
    **/
    @Override
    @Cacheable(cacheNames = Constants.CACHE_PRODUCT_CATEGORY)
    public List<Category> listCategrory() {
        return categoryMapper.selectAll();
    }
    /**
     * @Author JackLei
     * @Date cteate in 2018/6/19 21:10
     * @Description 分类下的产品
    **/
    @Override
    public List<Product> listProduct(Long categoryId) {

        return productMapper.selectBycategoryId(categoryId);
    }


    /**
     * @Author JackLei
     * @Date cteate in 2018/6/19 21:25
     * @Description 检索产品
    **/

    @Override
    public List<Product> searchProduct(String searchName, int pageSize, int searchNumber) throws IOException {
        if (pageSize<=0){
            pageSize=PAGESIZE;
        }
        if (searchNumber<0){
            searchNumber=SEARCHNUMBER;
        }
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery()
        .must(QueryBuilders.matchQuery("spu_name",searchName))
        .must(QueryBuilders.matchQuery("status",1)))
        .from(pageSize)
        .size(searchNumber*pageSize+1);
        searchSourceBuilder.highlighter()
                .field("spu_name")
                .preTags("<em>").postTags("</em>")
                .fragmentSize(200);
        Search search=new Search.Builder(searchSourceBuilder.toString())
                .addIndex("test-buy-trade")
                .build();
        SearchResult response=esClient.execute(search);
        String jsonString=response.toString();
        return paraResult(jsonString);
    }
    /**
     * @Author JackLei
     * @Date cteate in 2018/6/19 22:31
     * @Description 产品详细
    **/
    @Override
    @Cacheable(cacheNames = Constants.CACHE_PRODUCT_DETAIL,key ="#productId")
    public Product productDetail(Long productId) {
        return productMapper.selectByPrimaryKeyWithSku(productId);
    }

    /**
     * @Author JackLei
     * @Date cteate in 2018/6/19 22:26
     * @Description 转实体
    **/
    private List<Product> paraResult(String jsonString) {
        JSONArray jsonArray=JSON.parseObject(jsonString).getJSONObject("hits").getJSONArray("hits");
        List<Product> list=new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            JSONObject productObj=jsonObject.getJSONObject("_source");
            Product product=new Product();
            product.setId(productObj.getLongValue("id"));
            product.setCategoryId(productObj.getLongValue("category_id"));
            product.setBrandId(productObj.getLongValue("brand_id"));
            product.setPrice((Integer) productObj.get("price"));
            product.setProductName(productObj.getString("spu_name"));
            list.add(product);

        }
        return list;

    }
}
