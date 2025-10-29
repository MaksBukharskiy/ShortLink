package com.shortlinkv1.Backend.controller.testMessage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/message/test")
public class OKRestMessage {

    public OKRestMessage() {
        System.out.println("✅ [DEBUG] OKRestMessage bean created!");
    }

    @GetMapping("/test-permit-all")
    public String test() {
        return "OK";
    }

}
