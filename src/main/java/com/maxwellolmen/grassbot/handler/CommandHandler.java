package com.maxwellolmen.grassbot.handler;

import java.util.HashMap;
import java.util.Map;

import com.maxwellolmen.grassbot.command.*;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class CommandHandler {

    private Map<String, Command> commands;

    public CommandHandler() {
        commands = new HashMap<>();

        commands.put("info", new InfoCommand());
        commands.put("ping", new PingCommand());
    }
    
    public void handle(MessageChannel channel, User user, String command) {
        String label = command.split("\\s+")[0].substring(1);

        if (!commands.containsKey(label.toLowerCase())) {
            channel.sendMessage("Sorry, but that command does not exist.").queue();
            return;
        }

        commands.get(label.toLowerCase()).onCommand(channel, user, command);
    }
}
