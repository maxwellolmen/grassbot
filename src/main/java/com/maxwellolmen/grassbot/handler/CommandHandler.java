package com.maxwellolmen.grassbot.handler;

import java.util.HashMap;
import java.util.Map;

import com.maxwellolmen.grassbot.command.*;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandHandler {

    private Map<String, Command> commands;

    public CommandHandler() {
        commands = new HashMap<>();
        commands.put("info", new InfoCommand());
        commands.put("ping", new PingCommand());
        commands.put("touchgrass", new TouchGrassCommand());
        commands.put("touchgrassleaderboard", new TouchGrassLeaderboardCommand());

        commands.put("help", new HelpCommand(commands));
    }

    public void handle(MessageReceivedEvent event, String command) {
        String label = command.split("\\s+")[0].substring(1);
        /// t! touchgrass @haris
        // t! , touchgrass, @haris
        // event.getChannel().sendMessage("label:" + label).queue();
        if (!commands.containsKey(label.toLowerCase())) {
            event.getChannel().sendMessage("Sorry, but that command does not exist.").queue();
            return;
        }
        commands.get(label.toLowerCase()).onCommand(event, command);
    }
}
