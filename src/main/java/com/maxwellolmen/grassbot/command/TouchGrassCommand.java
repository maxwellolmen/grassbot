package com.maxwellolmen.grassbot.command;

import java.sql.SQLException;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

import com.maxwellolmen.grassbot.GrassBot;
import com.maxwellolmen.grassbot.handler.Command;
import com.maxwellolmen.grassbot.sql.SQLSaver;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TouchGrassCommand implements Command, SQLSaver {
    private Map<String, Integer> touchGrassCounter;
    private Map<String, Long> cooldownMap;
    Clock baseClock = Clock.systemDefaultZone();

    public TouchGrassCommand() {
        try {
            touchGrassCounter = GrassBot.sqlManager.getGrassCounts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cooldownMap = new HashMap<>();

        GrassBot.sqlManager.addSaver(this);
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String command) {
        if (event.getMessage().getMentions().getUsers().size() == 0) {
            event.getChannel().sendMessage("You must mention a user!").queue();
            return;
        }

        for (User mentioned : event.getMessage().getMentions().getUsers()) {
            if (cooldownMap.containsKey(mentioned.getId())
                    && System.currentTimeMillis() - cooldownMap.get(mentioned.getId()) < 86400000) {
                event.getChannel()
                        .sendMessage(mentioned.getName()
                                + " has already been told to touch grass again. Try again after 24 hours.")
                        .queue();
                return;
            }

            touchGrassCounter.put(mentioned.getId(), touchGrassCounter.getOrDefault(mentioned.getId(), 0) + 1);
            // event.getMessage().delete().queue();
            event.getChannel().sendMessage("Go fucking touch some grass, " + mentioned.getAsMention()).queue();
            event.getChannel()
                    .sendMessage("Grass Counter Increased by one. " + mentioned.getName() + "'s count is now "
                            + touchGrassCounter.get(mentioned.getId())
                            + ".")
                    .queue();
            cooldownMap.put(mentioned.getId(), System.currentTimeMillis());
        }
    }

    public Map<String, Integer> getGrassCounts() {
        return touchGrassCounter;
    }

    @Override
    public void autosave() throws SQLException {
        GrassBot.sqlManager.saveGrassCounts(touchGrassCounter);
    }
}