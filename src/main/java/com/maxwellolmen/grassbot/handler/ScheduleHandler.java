package com.maxwellolmen.grassbot.handler;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.maxwellolmen.grassbot.GrassBot;
import com.maxwellolmen.grassbot.command.TouchGrassCommand;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class ScheduleHandler {

    class LotteryTask extends TimerTask {

        @Override
        public void run() {
            TextChannel channel = GrassBot.jda.getTextChannelById(1078169935706062928L);
            String randomId = TouchGrassCommand.getRandomId();

            double rand = new Random().nextDouble();

            int oldCount = TouchGrassCommand.getCount(randomId);
            int newCount;
            double percentage;
            if (rand > 0.99) {
                percentage = 0.5;
            } else if (rand > 0.8) {
                percentage = 0.2;
            } else {
                percentage = 0.1;
            }

            newCount = TouchGrassCommand.scaleCount(randomId, 1 - percentage);

            channel.sendMessage("The time is here! Congratulations <@" + randomId
                    + ">, your grass count has been reduced from " + oldCount + " to " + newCount
                    + " for a decrease of "
                    + percentage
                    + "%! See you next Monday @ 9pm Central, where a random user will be selected for a random decrease in their grasscount :)")
                    .queue();

            initLottery();
        }
    }

    public ScheduleHandler() {
        initLottery();
    }

    public void initLottery() {
        TimerTask lotteryTask = new LotteryTask();

        Timer timer = new Timer();

        Calendar calendar = Calendar.getInstance();

        int daysUntilNextMonday = Calendar.MONDAY - calendar.get(Calendar.DAY_OF_WEEK);

        // If today is already Monday and it's past 9 PM, add 7 days to get to the next
        // Monday
        if (daysUntilNextMonday <= 0 && calendar.get(Calendar.HOUR_OF_DAY) >= 21) {
            daysUntilNextMonday += 7;
        }

        // Add the calculated days to get to the next Monday
        calendar.add(Calendar.DAY_OF_WEEK, daysUntilNextMonday);

        // Set the time to 9 PM
        calendar.set(Calendar.HOUR_OF_DAY, 21); // 9 PM
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date timeToRun = calendar.getTime();

        timer.schedule(lotteryTask, timeToRun);
    }
}