package com.maxwellolmen.grassbot.command;

import com.maxwellolmen.grassbot.handler.Command;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand implements Command {

    @Override
    public void onCommand(MessageReceivedEvent event, String command) {
        event.getChannel().sendMessage("Pong!").queue();
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Verify the bot is running.";
    }
}
