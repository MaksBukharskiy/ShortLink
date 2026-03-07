package com.shortlinkv1.Backend.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue testQueue(){
        return new Queue("test.queue", true);

    }

    @Bean
    public Queue queueForInfo(){
        return new Queue("queue.for.exchange", false);
    }

    @Bean
    public DirectExchange userQueueExchange(){
        return new DirectExchange("user.exchange");
    }

    @Bean
    public Binding bindingTest(Queue queueForInfo, DirectExchange userQueueExchange){
        return BindingBuilder
                .bind(queueForInfo)
                .to(userQueueExchange)
                .with("binding.app");
    }

}
