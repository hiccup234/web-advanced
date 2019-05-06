package top.hiccup.blueprint.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

/**
 * Created by wenhy on 2018/2/26.
 */
public class MqProducer2 {
    private String defaultURI = "tcp://127.0.0.1:61616";
    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    public MqProducer2() throws JMSException {
        factory = new ActiveMQConnectionFactory(defaultURI);
        connection = factory.createConnection(
                    ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD);
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    public void closeConnection() throws JMSException {
        connection.close();
    }
    public Session getSession() {
        return session;
    }

    public static void main(String[] args) {
        try {
            MqProducer2 mqProducer2 = new MqProducer2();

            String destinationName = "oceanQueue2";
            Destination destination = mqProducer2.getSession().createQueue(destinationName);

            // 发布订阅模式
//            Destination destination = mqProducer2.getSession().createTopic(destinationName);

            MessageProducer producer =  mqProducer2.getSession().createProducer(destination);
            // 不做持久化
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            for (int i = 1; i <= 10; i++) {
                MapMessage mapMessage =  mqProducer2.getSession().createMapMessage();
                mapMessage.setString("name","张三");
                mapMessage.setInt("age", 25);
                producer.send(mapMessage);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mqProducer2.closeConnection();
            System.out.println("消息发送完成，连接已关闭！");
        } catch (JMSException e1) {
            e1.printStackTrace();
        }
    }

}
