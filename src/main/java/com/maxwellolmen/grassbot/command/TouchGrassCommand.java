package com.maxwellolmen.grassbot.command;

import java.sql.SQLException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.maxwellolmen.grassbot.GrassBot;
import com.maxwellolmen.grassbot.handler.Command;
import com.maxwellolmen.grassbot.sql.SQLSaver;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TouchGrassCommand implements Command, SQLSaver {
    public static Map<Integer, List<String>> touchGrassCounter;
    private Map<String, Long> cooldownMap;
    private List<String> touchGrassMsgs;
    Clock baseClock = Clock.systemDefaultZone();

    public TouchGrassCommand() {
        try {
            touchGrassCounter = GrassBot.sqlManager.getGrassCounts();
            cooldownMap = GrassBot.sqlManager.getGrassCooldowns();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

        // 10% chance of adding nice message :) (AND EVEN SMALLER CHANCE OF BEING
        // PICKED!)
        if ((int) (Math.random() * 10) == 1) {
            list.add("Hmm... you look like you're doing well, no need to touch grass ;) Have a great day! ");
        }

        return list;
    }

    // Generates number 0..1, scaled to list size and typeset to int
    // (you can change it to use Random if u want)
    private String pickRandomMsg(List<String> list) {
        int index = (int) (Math.random() * list.size());

        return list.get(index);
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String command) {
        if (event.getMessage().getMentions().getUsers().size() == 0) {
            event.getChannel().sendMessage("You must mention a user!").queue();

            return;
        } else if (event.getMessage().getMentions().getUsers().contains(event.getAuthor())) {
            event.getChannel()
                    .sendMessage(
                            "You cannot just tell yourself to touch some grass. Have some respect for yourself, it'll help out your confidence in the long run.")
                    .queue();

            return;
        } else if (cooldownMap.containsKey(event.getAuthor().getId())
                && System.currentTimeMillis() - cooldownMap.get(event.getAuthor().getId()) < 86400000) {
            event.getChannel()
                    .sendMessage(event.getAuthor().getName()
                            + " already told someone to touch grass today. Try again in "
                            + (TimeUnit.MILLISECONDS.toHours((86400000 + cooldownMap.get(event.getAuthor().getId()))
                                    - System.currentTimeMillis()))
                            + " hours.")
                    .queue();

            int index = (int) (Math.random() * 10);

            if (index == 1) {
                event.getChannel()
                        .sendMessage(
                                "Perhaps a good alternative would be to follow your own advice.\nHowever, it seems that people are good at giving advice but not following it, so it's all up to you.")
                        .queue();
            }

            return;
        }

        User target = event.getMessage().getMentions().getUsers().get(0);

        int count = getCount(target.getId());
        touchGrassCounter.get(count).remove(target.getId());
        touchGrassCounter.get(count + 1).add(target.getId());

        String grassMsg;
        if (target.getId() == "1078162609641107486") { // GrassBot's ID
            grassMsg = "You're telling me, GrassBot, to touch grass? Alright sure, if it makes you feel better about yourself... ";
        } else {
            grassMsg = pickRandomMsg(touchGrassMsgs) + target.getAsMention();
        }

        event.getChannel()
                .sendMessage(grassMsg + "\n"
                        + "Grass Counter Increased by one. " + target.getName() + "'s count is now "
                        + (count + 1) + ".")
                .queue();
        cooldownMap.put(event.getAuthor().getId(), System.currentTimeMillis());
    }

    public Map<Integer, List<String>> getGrassCounts() {
        return touchGrassCounter;
    }

    @Override
    public void autosave() throws SQLException {
        GrassBot.sqlManager.saveGrassCounts(touchGrassCounter);
        GrassBot.sqlManager.saveGrassCooldowns(cooldownMap);
    }

    @Override
    public String getUsage() {
        return "!touchgrass <user>";
    }

    @Override
    public String getDescription() {
        return "Remind someone that they really need some grass-touching.";
    }

    public int getCount(String id) {
        for (Map.Entry<Integer, List<String>> entry : touchGrassCounter.entrySet()) {
            if (entry.getValue().contains(id)) {
                return entry.getKey();
            }
        }

        return 0;
    }

    public static String[] getTopGrassCounts() {
        String[] ids = new String[] { null, null, null, null, null, null, null, null, null, null };
        int i = 0;

        for (Map.Entry<Integer, List<String>> entry : touchGrassCounter.entrySet()) {
            for (String id : entry.getValue()) {
                ids[i] = id;
                i++;

                if (i == 10) {
                    break;
                }
            }
        }

        return ids;
    }
}