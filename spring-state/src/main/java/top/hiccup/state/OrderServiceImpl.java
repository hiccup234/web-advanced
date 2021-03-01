package top.hiccup.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import top.hiccup.state.sm.OrderStatus;
import top.hiccup.state.sm.OrderStatusChangeEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * F
 *
 * @author wenhy
 * @date 2021/2/22
 */
@Service("OrderServiceImpl")
public class OrderServiceImpl {


//    @Autowired
//    private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;

    public static final String stateMachineId = "orderStateMachine";

    @Autowired
    private StateMachineFactory<OrderStatus, OrderStatusChangeEvent> orderStateMachineFactory;

    @Autowired
    private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, Order> persister;

    private int id = 1;
    private Map<Integer, Order> orders = new HashMap<>();

    public Order creat() {
        Order order = new Order();
        order.setStatus(OrderStatus.WAIT_PAYMENT);
        order.setId(id++);
        orders.put(order.getId(), order);
        return order;
    }

    public Order pay(int id) {
        Order order = orders.get(id);
        System.out.println(" 等待支付 -> 等待发货 id=" + id + " threadName=" + Thread.currentThread().getName());
        Message message = MessageBuilder.withPayload(OrderStatusChangeEvent.PAYED).setHeader("order", order).build();
        if (!sendEvent(message, order)) {
            System.out.println(" 等待支付 -> 等待发货 失败, 状态异常 id=" + id + " threadName=" + Thread.currentThread().getName());
        } else {
            System.out.println(" 等待支付 -> 等待发货 成功 id=" + id + " threadName=" + Thread.currentThread().getName());
        }
        return orders.get(id);
    }

    public Order deliver(int id) {
        Order order = orders.get(id);
        System.out.println(" 等待发货 -> 等待收货 id=" + id + " threadName=" + Thread.currentThread().getName());
        if (!sendEvent(MessageBuilder.withPayload(OrderStatusChangeEvent.DELIVERY).setHeader("order", order).build(), orders.get(id))) {
            System.out.println(" 等待发货 -> 等待收货 失败，状态异常 id=" + id + " threadName=" + Thread.currentThread().getName());
        } else {
            System.out.println(" 等待发货 -> 等待收货 成功 id=" + id + " threadName=" + Thread.currentThread().getName());
        }
        return orders.get(id);
    }

    public Order receive(int id) {
        Order order = orders.get(id);
        System.out.println(" 等待收货 -> 完成 收货 id=" + id + " threadName=" + Thread.currentThread().getName());
        if (!sendEvent(MessageBuilder.withPayload(OrderStatusChangeEvent.RECEIVED).setHeader("order", order).build(), orders.get(id))) {
            System.out.println(" 等待收货 -> 完成 失败，状态异常 id=" + id + " threadName=" + Thread.currentThread().getName());
        } else {
            System.out.println(" 等待收货 -> 完成 成功 id=" + id + " threadName=" + Thread.currentThread().getName());
        }
        return orders.get(id);
    }

    public Map<Integer, Order> getOrders() {
        return orders;
    }


    /**
     * 发送订单状态转换事件
     *
     * @param message
     * @param order
     * @return
     */
    private boolean sendEvent(Message<OrderStatusChangeEvent> message, Order order) {
        synchronized (String.valueOf(order.getId()).intern()) {
            boolean result = false;
            StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine = orderStateMachineFactory.getStateMachine(stateMachineId);
            System.out.println("id=" + order.getId() + " 状态机 orderStateMachine" + orderStateMachine);
            try {
                orderStateMachine.start();
                //尝试恢复状态机状态
                persister.restore(orderStateMachine, order);
                System.out.println("id=" + order.getId() + " 状态机 orderStateMachine id=" + orderStateMachine.getId());
                //添加延迟用于线程安全测试
                Thread.sleep(1000);
                result = orderStateMachine.sendEvent(message);
                //持久化状态机状态
                persister.persist(orderStateMachine, order);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                orderStateMachine.stop();
            }
            return result;
        }
    }

}
