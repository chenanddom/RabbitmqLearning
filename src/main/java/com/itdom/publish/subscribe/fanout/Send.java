package com.itdom.publish.subscribe.fanout;

import com.itdom.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 一个消费者将消息首先发生到交换器，交换器绑定到多个队列，
 * 然后被监听该队列的消费者锁接收并消费
 *
 * 如果使用fanout模式发送消息，如果发送一条消息，那么多个监听绑定该交换器的队列的消费者将获取到同样的消息
 * 如果没有队列绑定交换机，则消息将会丢失，因为交换机没有能力当存储器，消息只能存储在队列当中。
 */
public class Send {
    private static final String EXCHANGE_NAME="MqName.exchange_fanout".toString();
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = ConnectionUtil.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        //发送消息
        String msg = "hello exchange";
        System.out.println("[mq] send: "+msg);
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        channel.close();

        connection.close();
    }
}
