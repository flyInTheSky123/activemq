package com.activemq.queue;

import cn.hutool.core.util.RandomUtil;
import com.activemq.util.ActiveMQUtil;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

//activeMQ 有两种模式：1，队列模式（即 生产者发送10条消息给MQ,消费者们会瓜分这些消息，一个消息只会被一个消费方得到）
//                   2，主题模式（即 生产者发送10条消息给MQ,消费者们会得到所有消息， 每个消费方都可以得到10条消息）
public class TestConsumer {
    private static final String uri = "tcp://127.0.0.1:61616";
    private static final String name = "queue_style";
    private static final String consumerName = "consumer-" + RandomUtil.randomString(5);

    public static void main(final String[] args) throws JMSException {

        ActiveMQUtil.checkServer();
        System.out.println("%s  消费者启动了 .%n" + consumerName);

        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(uri);
        //工厂创建连接
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建一个队列（createQueue 表明这是一个队列模式）
        Destination destination = session.createQueue(name);

        //创建一个消费者
        MessageConsumer consumer = session.createConsumer(destination);

        //设置一个消息监听者
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;

                try {
                    System.out.println(consumerName + "接受消息：" + textMessage.getText());
                } catch (JMSException e) {

                    e.printStackTrace();
                }

            }
        });


    }
}
