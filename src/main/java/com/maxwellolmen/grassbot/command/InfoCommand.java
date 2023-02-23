package com.maxwellolmen.grassbot.command;

import com.maxwellolmen.grassbot.handler.Command;
import com.maxwellolmen.grassbot.GrassBot;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class InfoCommand implements Command {

    @Override
    public void onCommand(MessageChannel channel, User user, String command) {
        channel.sendMessageEmbeds(new MessageEmbed(null, GrassBot.jda.getGuildById("952964020263071765").getName(), null, null, null, 0, null, null, null, null, null, null, null)).queue();
    }
}
