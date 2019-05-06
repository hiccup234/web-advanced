package top.hiccup.blueprint.dao;

import top.hiccup.blueprint.entity.po.Order;
import top.hiccup.blueprint.entity.po.OrderItem;

import java.io.IOException;
import java.util.List;

/**
 * Created by wenhy on 2018/1/19.
 */
public interface IOrderDao {

    public List<Order> queryOrderById(Long orderId) throws IOException;

    // 注解式开发
//    @Insert(value={" insert into `order`(order_id, cust_id, amount, remark) values (#{orderId}, #{custId}, #{amount}, #{remark})"})
//    public Integer saveOrder(Order orderInfo) throws IOException;

    public Integer saveOrder(Order orderInfo) throws IOException;

    public List<Order> queryOrderByOrderId(Long orderId) throws IOException;
    public List<Order> queryOrderByOrderId2(Long orderId) throws IOException;

    public List<OrderItem> queryOrderItemByOrderId(Long orderId) throws IOException;
    public List<OrderItem> queryOrderItemByOrderId2(Long orderId) throws IOException;


}
