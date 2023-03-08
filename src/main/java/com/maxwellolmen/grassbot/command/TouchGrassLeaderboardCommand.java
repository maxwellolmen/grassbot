package com.maxwellolmen.grassbot.command;

import java.sql.SQLException;

import com.maxwellolmen.grassbot.GrassBot;
import com.maxwellolmen.grassbot.handler.Command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.Color;

public class TouchGrassLeaderboardCommand implements Command {

    @Override
    public void onCommand(MessageReceivedEvent event, String command) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Grass Leaderboard!!!");
        eb.setColor(Color.GREEN);
        
        StringBuilder sb = new StringBuilder();

        try {
            GrassBot.sqlManager.autosave();

            String[] topIds = GrassBot.sqlManager.getTopGrassCounts();

            String username;
            int i = 1;
            for (String id : topIds) {
                User user = GrassBot.getJDA().retrieveUserById(id).complete();
                Member member = GrassBot.getJDA().getGuildById("952964020263071765").retrieveMember(user).complete();

                if (member == null) {
                    username = "Unknown Member";
                } else {
                    username = member.getEffectiveName();
                }

                if (!id.equals(topIds[0])) {
                    sb.append('\n');
                }

                sb.append(i + " - " + username + ": " + TouchGrassCommand.touchGrassCounter.get(id));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
