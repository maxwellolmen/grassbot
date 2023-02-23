package com.maxwellolmen.grassbot.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.maxwellolmen.grassbot.GrassBot;
import com.maxwellolmen.grassbot.controller.model.HarisInfo;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

/*
    RESPONSIBLE FOR INTERCEPTING MESSAGES FROM CLIENT TO SERVER.
 */
@Controller
public class HarisController {

    /*@MessageMapping("/touchgrass")
    public HarisInfo receiveGrassTouching(@Payload HarisInfo info) {
        GrassBot.jda.getChannelById(MessageChannel.class, "1078169935706062928").sendMessage(info.getMessage() + " touched grass!").queue();

        return info;
    }*/
}