package com.test.tyreadmin.service;

import com.test.tyreadmin.common.page.PageQueryBean;
import com.test.tyreadmin.entity.Api;
import java.util.List;

/**
 * @Date: 2020/4/22 19:25
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface APiService {

    PageQueryBean getApis(PageQueryBean pageQueryBean);

    Api getApi(Long id);

    void updateApi(Api api);
}
