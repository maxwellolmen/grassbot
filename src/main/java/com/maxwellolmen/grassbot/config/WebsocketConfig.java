package com.maxwellolmen.grassbot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
/*
ALRIGHT. LISTEN UP KIDS BECAUSE I WILL ONLY SAY THIS ONCE...
OUR MESSAGES FORMAT IS BASICALLY HANDLED HERE.


 */
@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);
        registry.setApplicationDestinationPrefixes("/app");
        /*
        /app is the server subscribing to a message. In the MFCController class, we will
        listen to messages that start with /app. If the client sends a message with /app on the socket, like, for example,
        props.stompClient.send("/app/sendWager", {}, JSON.stringify(newRPSDInfo)); (client sending message to
        /app/sendWager)
            //on controller class
            @MessageMapping("/sendWager")
            public RPSDInfo receiveWager(@Payload RPSDInfo info){

                simpMessagingTemplate.convertAndSendToUser(info.getReceiver(), "/sendWager", info);
                return info;
            }
        would intercept it and get called. It will receive the RPSDInfo object we sent called
        "newRPSDInfo" from the client.
         */
        registry.enableSimpleBroker("/requests","/user");

        /*
        registry.enableSimpleBroker("/requests","/user"); enables us to send messages to
        the client. the messages that they intercept will all start with /requests and /user.

        for example,
        props.stompClient.subscribe("/requests/leaver", props.playerHasLeft); on the frontend means we have
        subscribed to /requests/leaver, which means that if you send a message to that, it'll execute the
        "playerHasLeft" function in the frontend and will receive an object of MFCCInfo.
         */
        registry.setUserDestinationPrefix("/user");

        /*
            simpMessagingTemplate.convertAndSendToUser(info.getReceiver(), "/updatePlayers", info);
            when calling simpMessagingTemplate.convertAndSendToUser, we will only send to a single person
            and our message will begin with /user
         */
    }
}
