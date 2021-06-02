package com.itdom.controller;

import com.itdom.service.RabbitmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitmqController {
    @Autowired
    private RabbitmqService rabbitmqService;

    @PostMapping("/convertAndSend")
    public void convertAndSend(@RequestParam("msg") String message) {
        rabbitmqService.convertAndSend(message);
    }


}
