package com.shortlinkv1.Backend.controller.rabbitmq.RController;

import com.shortlinkv1.Backend.RabbitRoles.TestProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rabbitmq")
public class Controller {

    private final TestProducer testProducer;

    @PostMapping("/test/request")
    public void sendProduser(@RequestParam String msg){
        testProducer.sendMessage(msg);
        log.info("✅✅✅Producer✅✅✅");
    }

}
