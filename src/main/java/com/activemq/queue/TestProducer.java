package com.activemq.queue;

import com.activemq.util.ActiveMQUtil;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TestProducer {
    private static final String url = "tcp://127.0.0.1:61616";
    private static final String name = "queue_style";

    public static void main(String[] args) throws JMSException {
        //检测端口是否可用
        ActiveMQUtil.checkServer();
        //创建链接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //工厂创建链接
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //链接创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //会话创建一个目标(队列类型)
        Destination destination = session.createQueue(name);
        //创建一个生产者
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 100; i++) {
            //创建消息
            TextMessage textMessage = session.createTextMessage("队列消息" + i);

            //发送消息
            producer.send(textMessage);

            System.out.println("发送消息：" + textMessage.getText());
        }

        connection.close();

    }
}
