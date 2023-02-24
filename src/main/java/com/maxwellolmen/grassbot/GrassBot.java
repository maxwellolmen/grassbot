package com.maxwellolmen.grassbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.maxwellolmen.grassbot.handler.MessageHandler;
import com.maxwellolmen.grassbot.sql.SQLManager;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;

@SpringBootApplication
public class GrassBot {

    public static JDA jda;
    public static Guild ldg;

    public static SQLManager sqlManager;

    public static void main(String[] args) {
        // SpringApplication.run(GrassBot.class, new String[] {"--server.port=8082"});

        if (args.length < 1) {
            System.err.println("You must pass the Discord token as an argument.");
            return;
        }

        sqlManager = new SQLManager();
        sqlManager.init();

        jda = JDABuilder.createDefault(args[0]).enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS).build();

        while (jda.getGuilds().size() == 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Connected to a Guild!");

        ldg = jda.getGuildById("952964020263071765");
        jda.addEventListener(new MessageHandler());

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
    
                    sqlManager.autosave();
                }
            }
        }.start();
    }
}