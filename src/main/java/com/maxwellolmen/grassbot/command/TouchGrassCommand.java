package com.maxwellolmen.grassbot.command;

import java.sql.SQLException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maxwellolmen.grassbot.GrassBot;
import com.maxwellolmen.grassbot.handler.Command;
import com.maxwellolmen.grassbot.sql.SQLSaver;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TouchGrassCommand implements Command, SQLSaver {
    private Map<String, Integer> touchGrassCounter;
    private Map<String, Long> cooldownMap;
    private List<String> touchGrassMsgs;
    Clock baseClock = Clock.systemDefaultZone();

    public TouchGrassCommand() {
        try {
            touchGrassCounter = GrassBot.sqlManager.getGrassCounts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cooldownMap = new HashMap<>();
        touchGrassMsgs = initTouchGrassMsgs();

        GrassBot.sqlManager.addSaver(this);
    }

    private List<String> initTouchGrassMsgs() {
        List<String> list = new ArrayList<String>();
        list.add("Go fucking touch some grass, ");
        list.add("It's a beautiful day outside, perfect for touching grass... ");
        list.add("Seriously, go outside and touch grass. ");
        list.add("L + Ratio + touch grass ");
        list.add("You... especially you really need to touch grass! ");
        list.add("Nerd alert! This person needs to touch some grass: ");
        list.add("It seems like you're lacking a bit of grass touching, ");
        list.add("No shower AND haven't touched grass yet? Geez, ");
        
        // 10% chance of adding nice message :) (AND EVEN SMALLER CHANCE OF BEING PICKED!)
        if((int)(Math.random()*10) == 1) {
            list.add("Hmm... you look like you're doing well, no need to touch grass ;) Have a great day! ");
        }
        return list;
    }

    // Generates number 0..1, scaled to list size and typeset to int
    // (you can change it to use Random if u want)
    private String pickRandomMsg(List<String> list) {
        int index = (int)(Math.random() * list.size());
        return list.get(index);
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
            //event.getChannel().sendMessage("Go fucking touch some grass, " + mentioned.getAsMention()).queue();
            event.getChannel().sendMessage(pickRandomMsg(touchGrassMsgs) + mentioned.getAsMention()).queue();
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