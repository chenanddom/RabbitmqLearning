package com.itdom.publish.subscribe.topic;

import com.itdom.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver2 {
    public static final String QUEUE_NAME = "MqName.queue_topic_002";
    public static final String EXCHANGE_NAME = "MqName.exchange_topic";
    public static final String ROUTING_KEY = "MqName.routing_goods";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //获取channel
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //设置预读数
        channel.basicQos(1);
        //绑定交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY + ".*");


        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println(envelope.getRoutingKey() + " [2] Receive2 msg:" + msg);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
