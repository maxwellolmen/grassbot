package com.maxwellolmen.grassbot.command;
import com.maxwellolmen.grassbot.handler.Command;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.Color;

public class HelpCommand implements Command {
    
    private Map<String, Command> commands;

    public HelpCommand(Map<String, Command> c) {
        // should keep a internal hashmap of commands
        commands = c;
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String command) {
        StringBuilder sb = new StringBuilder();
        
        for (Map.Entry<String, Command> set : commands.entrySet()) {
            sb.append("!").append(set.getKey()).append("\n");
        }
        
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("GrassBot Commands");
        eb.setColor(Color.BLUE);
        eb.setDescription(sb.toString());

        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }
}
