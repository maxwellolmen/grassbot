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
            if (rand > 0.99) {
                newCount = TouchGrassCommand.scaleCount(randomId, 0.5);
            } else if (rand > 0.8) {
                newCount = TouchGrassCommand.scaleCount(randomId, 0.8);
            } else {
                newCount = TouchGrassCommand.scaleCount(randomId, 0.9);
            }

            channel.sendMessage("The time is here! Congratulations <@" + randomId
                    + ">, your grass count has been reduced from " + oldCount + " to " + newCount + "!").queue();
        }
    }

    public ScheduleHandler() {
        initLottery();
    }

    public void initLottery() {
        TimerTask lotteryTask = new LotteryTask();

        Timer timer = new Timer();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date timeToRun = calendar.getTime();

        timer.schedule(lotteryTask, timeToRun);
    }
}