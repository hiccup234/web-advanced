package top.hiccup.blueprint.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by wenhy on 2018/2/26.
 */
public class MqProducer {

    public static void main(String[] args) {
        String connectionURI = "tcp://127.0.0.1:61616";
        try {
//            ConnectionFactory factory = new ActiveMQConnectionFactory(connectionURI);
//            Connection connection = factory.createConnection(
//                    ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD);

            ConnectionFactory factory = new ActiveMQConnectionFactory(
                    ActiveMQConnectionFactory.DEFAULT_USER,
                    ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                    connectionURI);
            Connection connection = factory.createConnection();
            // 启动连接
            connection.start();

            // 1、不启用事务 2、客户端自动签收
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            String destinationName = "oceanQueue";
            Destination destination = session.createQueue(destinationName);

            MessageProducer producer = session.createProducer(destination);
            // 不做持久化
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            for (int i = 1; i <= 10; i++) {
                TextMessage msg = session.createTextMessage("消息:" + i);
                msg.setIntProperty("id", i);
                producer.send(msg);
            }

//            producer.send(session.createTextMessage("SHUTDOWN"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 记得关闭连接
            connection.close();
            System.out.println("消息发送完成，连接已关闭！");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
