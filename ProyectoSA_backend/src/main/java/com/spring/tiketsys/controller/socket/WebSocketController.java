package com.spring.tiketsys.controller.socket;

import com.spring.tiketsys.service.History_of_CommunicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    //private final History_of_CommunicationService historyOfCommunicationService;

    @MessageMapping("/hello")
    @SendTo("/user/greetings")
    public String greeting(@Payload String message) {
        return "Hello, " + message + "!";
    }

}



