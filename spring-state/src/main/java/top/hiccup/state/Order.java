package top.hiccup.state;

import lombok.Data;
import top.hiccup.state.sm.OrderStatus;

/**
 * F
 *
 * @author wenhy
 * @date 2021/2/22
 */
@Data
public class Order {

    private OrderStatus status;

    private Integer id;
}
