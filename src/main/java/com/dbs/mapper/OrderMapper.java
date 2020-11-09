package com.dbs.mapper;


import com.dbs.entry.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface OrderMapper {

    @Options(useGeneratedKeys = true, keyProperty = "OrderId", keyColumn = "order_id")
    @Insert("INSERT INTO t_order (\n" +
            "        order_id,user_id, status\n" +
            "        )\n" +
            "        VALUES (\n" +
            "        #{orderId,jdbcType=BIGINT},\n" +
            "        #{userId,jdbcType=BIGINT},\n" +
            "        #{status,jdbcType=VARCHAR}\n" +
            "        )")
    Long insert(Order entity);

    void delete(Long id);

    @Select("select o.order_id orderId,o.user_id userId,o.status status  from t_order o")
    List<Order> selectAll();

    List<Order> selectRange();

    @Select("select o.order_id orderId,o.user_id userId,o.status status  from t_order o where o.status=#{status}")
    List<Order> selectAllByStatus(@Param("status") String status);

    @Select("select o.order_id orderId,o.user_id userId,o.status status  from t_order o where o.order_id=#{orderId}")
    List<Order> selectAllByOderId(@Param("orderId") long orderId);

    @Select("select o.order_id orderId,o.user_id userId,o.status status  from t_order o order by user_id")
    List<Order> selectAllOrderByuserId();

    @Select("select o.order_id orderId,o.user_id userId,o.status status  from t_order o limit #{pages},#{size}")
    List<Order> selectAllPageing(@Param("pages") int pages, @Param("size") int size);

    @Select("select o.order_id orderId,o.user_id userId,o.status status  from t_order o where o.status=#{status} and o.order_id=#{orderId}")
    List<Order> selectAllByOderIdAndStatus(@Param("orderId") long orderId, @Param("status") String status);

    @Delete("delete from t_order where order_id=#{orderId}")
    void deleteByOrderId(@Param("orderId") long orderId);
}
