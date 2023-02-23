package com.maxwellolmen.grassbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.maxwellolmen.grassbot.handler.MessageHandler;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

@SpringBootApplication
public class GrassBot {

    public static JDA jda;

    public static void main(String[] args) {
        // SpringApplication.run(GrassBot.class, new String[] {"--server.port=8082"});

        if (args.length < 1) {
            System.err.println("You must pass the Discord token as an argument.");
            return;
        }

        jda = JDABuilder.createDefault(args[0]).enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS).build();

        jda.addEventListener(new MessageHandler());
    }
}