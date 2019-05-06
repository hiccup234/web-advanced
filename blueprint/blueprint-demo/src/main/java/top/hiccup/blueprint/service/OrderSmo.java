package top.hiccup.blueprint.service;

import top.hiccup.blueprint.dao.IOrderDao;
import top.hiccup.blueprint.entity.po.Order;
import top.hiccup.blueprint.entity.po.OrderItem;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

/**
 * Created by wenhy on 2018/1/23.
 */
public class OrderSmo {

    private IOrderDao orderDao;

    public void testDao() throws IOException {

        // 单表查询
        orderDao = SessionUtils.getSession().getMapper(IOrderDao.class);
        List<Order> orders = orderDao.queryOrderById(234001L);
        for(Order order : orders) {
            System.out.println(order);
        }

        // 插入（更新、删除类似）CURD
        SqlSession sqlSession = SessionUtils.getSession();
        orderDao = sqlSession.getMapper(IOrderDao.class);
        Order order = new Order();
        order.setOrderId(234002L);
        order.setRemark("插入测试");
        orderDao.saveOrder(order);
        // 记得要提交事务
        sqlSession.commit();

        // 一对多：多表关联查询
        orderDao = SessionUtils.getSession().getMapper(IOrderDao.class);
        List<Order> orders2 = orderDao.queryOrderByOrderId(234001L);
        for(Order o : orders2) {
            System.out.println(o);
        }

        // 一对多：单表关联多次查询
        orderDao = SessionUtils.getSession().getMapper(IOrderDao.class);
        List<Order> orders3 = orderDao.queryOrderByOrderId2(234002L);
        for(Order o : orders3) {
            System.out.println(o);
        }

        // 多对一：多表关联查询
        orderDao = SessionUtils.getSession().getMapper(IOrderDao.class);
        List<OrderItem> orderItems = orderDao.queryOrderItemByOrderId(234002L);
        for(OrderItem orderItem : orderItems) {
            System.out.println(orderItem);
        }

        // 多对一：单表关联多次查询
        SqlSession sqlSessiono = SessionUtils.getSession();
        orderDao = sqlSession.getMapper(IOrderDao.class);
        List<OrderItem> orderItems2 = orderDao.queryOrderItemByOrderId2(234001L);

        // 线程休眠3秒，测试延迟加载
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 可能是由于java是编译型语言，mybatis可以根据编译好的中间码查看哪些属性被调用
         * 导致在这里断点时order属性被加载了，而去掉断点则会按正常顺序打印
         */
        // 如果关掉侵入式延迟加载，在这里断点也会引起Mybatis加载关联的order属性
        for(OrderItem orderItem : orderItems) {
            System.out.println(orderItem.getOrderItemId()+":"+orderItem.getRemark());
        }

        for(OrderItem orderItem : orderItems) {
            // 这里访问延迟加载的属性
            System.out.println(orderItem.getOrder().getRemark());
        }


        /**
         * 缓存底层由Map实现，key：查询依据  value：查询结果对象
         * MyBatis查询依据：SQL的id + SQL语句 + hash值
         * Hibernate查询依据：查询结果对象的id ？
         */
        /**
         * 增删改操作会引起清空一级缓存，无论SqlSession是否提交
         */
        Order order3 = new Order();
        order3.setOrderId(234004L);
        order3.setUserId(1002L);
        order3.setAmount(500L);
        order3.setRemark("插入测试222");
        orderDao.saveOrder(order3);
        sqlSession.commit();

        // 验证MyBatis一级缓存
        orderItems = orderDao.queryOrderItemByOrderId2(234001L);


        /**
         * 开启二级缓存（MyBatis二级缓存）：
         * 1）对实体进行序列化
         * 2）在映射文件头部添加<cache/>标签
         */
        /**
         * 1）增删改操作同样会引起二级缓存的情况，无论SqlSession是否提交
         * 2）对二级缓存的情况实际上是将所查找的key对应的value置为null，而并非直接删除Map的Entry对象
         * 3）从DB进行select的条件是：
         *      1、缓存中不存在当前key
         *      2、缓存中存在当前key，但其对应Entry对象的value为null
         */
        // 关掉SqlSession，使一级缓存失效，验证二级缓存
        sqlSession.close();
        sqlSession = SessionUtils.getSession();
        orderDao = sqlSession.getMapper(IOrderDao.class);
        // Cache Hit Ratio [com.ocean.ssm.mybatis.dao.IOrderDao]: 0.2 (命中率)
        orderItems = orderDao.queryOrderItemByOrderId2(234001L);


    }

    public static void main(String[] args) {
        OrderSmo orderInfoSmo = new OrderSmo();
        try {
            orderInfoSmo.testDao();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
