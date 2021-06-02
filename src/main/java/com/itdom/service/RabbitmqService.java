package com.itdom.service;

import com.itdom.config.RabbitmqConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqService {
@Autowired
private AmqpTemplate amqpTemplate;

    public void convertAndSend(String message) {

        amqpTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.email",message);

    }
}
