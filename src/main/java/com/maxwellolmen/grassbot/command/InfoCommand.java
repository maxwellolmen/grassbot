package com.maxwellolmen.grassbot.command;

import com.maxwellolmen.grassbot.handler.Command;
import com.maxwellolmen.grassbot.GrassBot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.awt.Color;

public class InfoCommand implements Command {

    @Override
    public void onCommand(MessageChannel channel, User user, String command) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(GrassBot.ldg.getName());
        eb.setColor(Color.RED);
        eb.setImage(GrassBot.ldg.getIconUrl());
        eb.setDescription("Total Members: " + GrassBot.ldg.getMemberCount());

        channel.sendMessageEmbeds(eb.build()).queue();
    }
}
