package com.maxwellolmen.grassbot.command;
import com.maxwellolmen.grassbot.handler.Command;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand implements Command {
    
    private Map<String, Command> commands;

    public HelpCommand(Map<String, Command> c) {
        // should keep a internal hashmap of commands
        commands = c;
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String command) {
        String message = "GrassBot Commands:\n";
        
        for (Map.Entry<String, Command> set : commands.entrySet()) {
            message += "!" + set.getKey() + "\n";
        }
        
        event.getChannel().sendMessage(message).queue();
    }
}
