package com.test.tyrelocation.repository;

import com.test.tyrelocation.common.page.OrderPageBean;
import com.test.tyrelocation.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> selectByUserId(@Param("userId") Long userId);

    /**
     * @Description：更新支付状态使用
     * @Date 2019/11/22 17:57
     */
    List<Order> selectByUserIdAndUnpaid(Long userId);

    int updateStatusByPrimaryKey(Order record);

    List<Order> selectByUserIdAndPaid(Long userId);

    /**
     *
     * @param dt 时间  如 19（年）， 19-11（月） 19-11-29（日）
     * @param ft 格式
     * @return 消费总额
     */
    Integer selectSumByYMD(@Param("dt") String dt,
                       @Param("ft") String ft, @Param("userId") Long userId);

    Integer selectCountByUserIdAndStatus(Long userId, int status);

    List<Order> selectPageByUserIdAndStatus(Long userId, OrderPageBean bean);

    Order selectByUserIdAndId(Long userId, Long orderId);
}