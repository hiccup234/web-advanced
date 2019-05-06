package top.hiccup.blueprint.dao;//package com.ocean.ssm.mybatis.dao;
//
//import com.ocean.ssm.mybatis.SessionUtils;
//import com.ocean.ssm.mybatis.entity.OrderInfo;
//import org.apache.ibatis.session.SqlSession;
//
//import java.io.IOException;
//import java.util.List;
//
///**
// * Created by wenhy on 2018/1/19.
// */
//public class OrderDaoImpl implements IOrderDao {
//
//    /**
//     * 使用Mybatis的动态代理后就不再用创建DAO的实现类了
//     */
//
//    public List<OrderInfo> queryOrderInfo(Long orderId) throws IOException {
//        List<OrderInfo> orderList = null;
//        SqlSession sqlSession = null;
//        try {
////            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
////            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
////            sqlSession = sqlSessionFactory.openSession();
//            sqlSession = SessionUtils.getSession();
//            orderList = sqlSession.selectList("selectOrderById", orderId);
////            sqlSession.commit();
//        } finally {
//            if(null != sqlSession) {
//                sqlSession.close();
//            }
//        }
//        return orderList;
//    }
//
//    public static void main(String[] args) {
//        final IOrderInfoDao orderDao = new OrderInfoDaoImpl();
//
////        try {
////            List<OrderInfo> orderInfos = orderDao.queryOrderInfo(234001L);
////            for(OrderInfo orderInfo : orderInfos) {
////                System.out.println(orderInfo);
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//        Thread[] threads = new Thread[20];
//        for(int i=0; i<20; i++) {
//            threads[i] = new Thread(new Runnable() {
//                public void run() {
//                    try {
//                        List<OrderInfo> orderInfos = orderDao.queryOrderInfo(234001L);
//                        for(OrderInfo orderInfo : orderInfos) {
//                            System.out.println(orderInfo);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//        for(int i=0; i<20; i++) {
//            threads[i].start();
//        }
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
//
