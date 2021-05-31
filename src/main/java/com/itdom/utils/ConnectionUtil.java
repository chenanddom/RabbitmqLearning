package com.itdom.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

/**
 * 链接RabiitMQ的工具类
 */
public class ConnectionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {
        return getConnection(new Properties());
    }


    private static Connection getConnection(Properties properties) throws IOException, TimeoutException {
    return getConnection(properties.host,
            properties.port,
            properties.vHost,
            properties.userName,
            properties.passWord);
    }

    public static Connection getConnection(String host, int port, String vHost, String userName, String password) throws IOException, TimeoutException {
        ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setVirtualHost(vHost);
        factory.setUsername(userName);
        factory.setPassword(password);
        com.rabbitmq.client.Connection connection = factory.newConnection();
        return connection;
    }


    public static class Properties implements Serializable {
        String host = "192.168.253.128";
        int port = 5672;
        String vHost =  "/itdom";
        String userName = "chendom";
        String passWord = "root@123";

        public Properties() {
        }

        public Properties(String host, int port, String vHost, String userName, String passWord) {
            this.host = host;
            this.port = port;
            this.vHost = vHost;
            this.userName = userName;
            this.passWord = passWord;
        }

        public String getHost() {
            return host;
        }

        public Properties setHost(String host) {
            this.host = host;
            return self();
        }

        public int getPort() {
            return port;
        }

        public Properties setPort(int port) {
            this.port = port;
            return self();
        }

        public String getvHost() {
            return vHost;
        }

        public Properties setvHost(String vHost) {
            this.vHost = vHost;
            return self();
        }

        public String getUserName() {
            return userName;
        }

        public Properties setUserName(String userName) {
            this.userName = userName;
            return self();
        }

        public String getPassWord() {
            return passWord;
        }

        public Properties setPassWord(String passWord) {
            this.passWord = passWord;
            return self();
        }

        private Properties self(){
            return this;
        }
    }
}
