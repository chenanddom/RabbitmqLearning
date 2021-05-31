package com.itdom.simple;

import com.itdom.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    private static String QUEUE_NAME = "test_simple_queue".toString();


    public static void main(String[] args) throws IOException, TimeoutException {
        sendMessage();
    }

    public static void sendMessage() throws IOException, TimeoutException {
        //获取链接
        Connection connection = ConnectionUtil.getConnection();
        //声明通道
        Channel channel = connection.createChannel();
        //声明队列
        /**
         * queue:队列名称
         *
         * durable： 是不持久化， true ，表示持久化，会存盘，服务器重启仍然存在，false，非持久化
         *
         * exclusive ： 是否排他的，true，排他。如果一个队列声明为排他队列，该队列公对首次声明它的连接可见，并在连接断开时自动删除，
         *
         * 排序是基于连接的Connection可见的，同一个连接的不同信道是可以同时访问同一个连接创建的排他队列，
         *
         * 首次--是指如果一个连接已经声明了一个排他队列，其它连接是不允许建立同名的排他队列，这个与普通队列不同，即使该队列是持久化的，一旦连接关闭或者客户端退出，该排他队列都会被自动删除，这个队列适用于一个客户端同是发送和读取消息的应用场景
         *
         * autoDelete ：是否自动删除,true，自动删除，自动删除的前提：至少有一个消息者连接到这个队列，之后所有与这个队列连接的消息都断开时，才会自动删除，，
         *
         * 备注：生产者客户端创建这个队列，或者没有消息者客户端连接这个队列时，不会自动删除这个队列
         *
         * arguments：其它一些参数。如：x-message-ttl,之类
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义消息内容
        String message = " hello rabbitmq ";
        //发布消息
        // channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN , message.getBytes());
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("[x] Sent'" + message + "'");
        //关闭通道
        channel.close();
        //关闭连接
        connection.close();
    }
}
