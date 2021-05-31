package com.itdom.publish.subscribe.topic;

import com.itdom.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题模式--消息发送者
 * 经过多次的实验可以发现如下的规律：
 * 消费者1只能接收到.add 和 .update的消息
 * 消费者2可以接收到.add .publish 和 .update的消息
 */
public class Send {

    private static final String EXCHANGE_NAME="MqName.exchange_topic";
    private static final String ROUTING_KEY="MqName.routing_goods";

    public static void main(String[] args) throws IOException, TimeoutException {
        //建立连接
        Connection connection = ConnectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
//        String routingKey = ROUTING_KEY + ".add";
//        String routingKey = ROUTING_KEY + ".publish";
        String routingKey = ROUTING_KEY + ".update";
        String msg = "route message ->"+routingKey;

        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
        System.out.println("对 "+routingKey+" 发送消息: "+msg);
        //关闭连接
        channel.close();
        connection.close();

    }
}
