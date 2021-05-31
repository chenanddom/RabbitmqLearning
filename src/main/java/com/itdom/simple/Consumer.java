package com.itdom.simple;

import com.itdom.utils.ConnectionUtil;
import com.rabbitmq.client.*;
import com.sun.org.apache.bcel.internal.generic.FADD;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

public class Consumer {

    private static String QUEUE_NAME = "test_simple_queue".toString();


    public static void main(String[] args) throws IOException, TimeoutException {
    getMessage();
    }

    public static void getMessage() throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //声明通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false,false,false,null);
        //定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("receive message: "+msg);
            }
        };

        //监听对垒
        /**
         * true:表示自动确认，只要消息队列中获取，无论消费者获取到消息后是否成功消费，都会认为消息已经成功消息
         * false:表示手动确认，消费者获取消息后，服务器会将搞消息标记为不可用状态，等待消费者的反馈。
         * 如果消费者一直没有反馈，那么该消息将一直处于不可用的状态，并且服务器会认为该消费者已经挂掉，不会再给其发生消息，知道该消费者反馈。
         */
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }

}
