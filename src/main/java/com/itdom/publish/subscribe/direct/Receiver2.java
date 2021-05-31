package com.itdom.publish.subscribe.direct;

import com.itdom.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver2 {
    public static final String QUEUE_NAME = "MqName.queue_routing_002".toString();
    public static final String EXCHANGE_NAME = "MqName.exchange_routing".toString();
    public static final String ROUTING_KEY_world = "MqName.routing_world".toString();
    public static final String ROUTING_KEY_hello = "MqName.routing_hello".toString();
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //设置预读取数
        channel.basicQos(1);
        //绑定交换机和路由器，可以绑定多个路由
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY_world);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY_hello);
        Consumer consumer = new DefaultConsumer(channel) {
            /**
             * No-op implementation of {@link Consumer#handleDelivery}.
             *
             * @param consumerTag
             * @param envelope
             * @param properties
             * @param body
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println(envelope.getRoutingKey() + " [2] Receive2 msg:" + msg);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
