package com.maxwellolmen.grassbot.command;

import com.maxwellolmen.grassbot.GrassBot;
import com.maxwellolmen.grassbot.handler.Command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.Color;

public class TouchGrassLeaderboardCommand implements Command {

    @Override
    public void onCommand(MessageReceivedEvent event, String command) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Grass Leaderboard!!!");
        eb.setColor(Color.GREEN);

        StringBuilder sb = new StringBuilder();

        GrassBot.sqlManager.autosave();

        String[] topIds = TouchGrassCommand.getTopGrassCounts();

        int i = 1;
        for (String id : topIds) {
            if (!id.equals(topIds[0])) {
                sb.append('\n');
            }

            sb.append(i + " - <@" + id + ">: " + TouchGrassCommand.getCount(id));
            i++;
        }

        eb.setDescription(sb.toString());

        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Examine those who need the most grass-touching.";
    }
}