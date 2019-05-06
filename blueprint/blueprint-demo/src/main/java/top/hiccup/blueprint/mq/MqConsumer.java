package top.hiccup.blueprint.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by wenhy on 2018/2/26.
 */
public class MqConsumer {
    public static void main(String[] args) {
        String connectionURI = "tcp://127.0.0.1:61616";
        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory(connectionURI);
            Connection connection = factory.createConnection(
                    ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD);

            // 启动连接
            connection.start();

            // 1、不启用事务 2、客户端自动签收
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 消费者目标队列必须要跟生产者的目标队列保持一致
            String destinationName = "oceanQueue";
            Destination destination = session.createQueue(destinationName);

            MessageConsumer consumer = session.createConsumer(destination);
            while (true) {
                Message msg = consumer.receive();
                String body = ((TextMessage) msg).getText();
                if("SHUTDOWN".equals(body)) {
                    break;
                }
                System.out.println("消费者收到消息："+body);
            }
            // 记得关闭连接
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}

