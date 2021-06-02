package com.itdom.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    //声明交换机和队列
    public static final String QUEUE_INFO_EMAIL = "queue_inform_email";
    public static final String QUEUE_INFO_SMS = "queue_inform_sms";
    public static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";
    public static final String ROUTINGKEY_EMAIL = "inform.#.email.#";
    public static final String ROUTINGKEY_SMS = "inform.#.sms.#";

    /**
     * 声明TOPICS主题模式的交换机
     *
     * @return
     */
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    /**
     * 声明email队列
     *
     * @return
     */
    @Bean(QUEUE_INFO_EMAIL)
    public Queue queue_email() {
        return new Queue(QUEUE_INFO_EMAIL);
    }

    /**
     * 声明sms队列
     *
     * @return
     */
    @Bean(QUEUE_INFO_SMS)
    public Queue queue_sms() {
        return new Queue(QUEUE_INFO_SMS);
    }

    /**
     * 交换机与QUEUE_INFORM_EMAIL队列绑定
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bind_queue_inform_email(@Qualifier(QUEUE_INFO_EMAIL) Queue queue,
                                           @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_EMAIL).noargs();
    }

    /**
     * 交换机与QUEUE_INFORM_SMS队列绑定
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bind_queue_inform_sms(@Qualifier(QUEUE_INFO_SMS) Queue queue,
                                         @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_SMS).noargs();
    }
}
