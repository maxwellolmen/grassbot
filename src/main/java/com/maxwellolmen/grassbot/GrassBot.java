package com.maxwellolmen.grassbot;

import com.maxwellolmen.grassbot.handler.MessageHandler;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class GrassBot {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("You must pass the Discord token as an argument.");
            return;
        }

        JDA jda = JDABuilder.createDefault(args[0]).build();

        jda.addEventListener(new MessageHandler());
    }
}