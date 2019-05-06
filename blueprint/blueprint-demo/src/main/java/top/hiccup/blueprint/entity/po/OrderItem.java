package top.hiccup.blueprint.entity.po;

import java.io.Serializable;

/**
 * Created by wenhy on 2018/1/23.
 */
public class OrderItem implements Serializable {

    private Long orderItemId;
    private Long orderId;
    private Long price;
    private String remark;

    // 多对一：持有引用
    private Order order;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", orderId=" + orderId +
                ", price=" + price +
                ", remark='" + remark + '\'' +
                ", order=" + order +
                '}';
    }
}
