package com.test.tyreadmin.service;

import com.test.tyreadmin.common.page.PageQueryBean;
import com.test.tyreadmin.entity.Api;
import com.test.tyreadmin.repository.ApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Date: 2020/4/22 19:28
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class APiServiceImpl implements APiService{

    @Autowired
    private ApiMapper apiMapper;

    @Override
    public PageQueryBean getApis(PageQueryBean pageQueryBean) {
        Integer count = apiMapper.selectAllCount();
        if (Objects.isNull(count)){
            count = 0;
        }
        pageQueryBean.setTotalRows(count);
        List<Api> apis = apiMapper.selectPage(pageQueryBean);
        pageQueryBean.setItems(apis);
        return pageQueryBean;
    }

    @Override
    public Api getApi(Long id) {
        return apiMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateApi(Api api) {
        apiMapper.updateByPrimaryKeySelective(api);
    }
}
