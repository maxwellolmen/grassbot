package com.maxwellolmen.grassbot.command;

import com.maxwellolmen.grassbot.handler.Command;
import com.maxwellolmen.grassbot.GrassBot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.Color;

public class InfoCommand implements Command {

    @Override
    public void onCommand(MessageReceivedEvent event, String command) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(GrassBot.ldg.getName());
        eb.setColor(Color.RED);
        eb.setImage(GrassBot.ldg.getIconUrl());
        eb.setDescription("Total Members: " + GrassBot.ldg.getMemberCount());

        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Get basic information about the server.";
    }
}
