package com.shortlinkv1.Backend.RabbitRoles;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestConsumer {
    @RabbitListener(queues = "test.queue")
    public void receiveMessage(String message) {
        log.info("️️☑️ Received: " + message);
    }

    @RabbitListener(queues = "queue.for.full.rabbit")
    public void receiveMessageFromFullRabbitQueue(String message){
        log.info("☑️ Received second Message using full RabbitMQ: " + message);
    }

}
