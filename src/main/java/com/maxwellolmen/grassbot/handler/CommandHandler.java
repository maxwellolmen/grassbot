package com.maxwellolmen.grassbot.handler;

import java.util.HashMap;
import java.util.Map;

import com.maxwellolmen.grassbot.command.PingCommand;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class CommandHandler {

    private Map<String, Command> commands;

    public CommandHandler() {
        commands = new HashMap<>();

        commands.put("ping", new PingCommand());
    }
    
    public void handle(MessageChannel channel, User user, String command) {
        String label = command.split("\\s+")[0].substring(1);

        commands.get(label.toLowerCase()).onCommand(channel, user, command);
    }
}
