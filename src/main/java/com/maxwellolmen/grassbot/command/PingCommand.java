package com.maxwellolmen.grassbot.command;

import com.maxwellolmen.grassbot.handler.Command;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class PingCommand implements Command {

    @Override
    public void onCommand(MessageChannel channel, User user, String command) {
        channel.sendMessage("Pong!").queue();
    }
}
