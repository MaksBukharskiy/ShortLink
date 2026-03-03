package com.shortlinkv1.Backend.config.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue testQueue(){
        return new Queue("test.queue", true);

    }

}
