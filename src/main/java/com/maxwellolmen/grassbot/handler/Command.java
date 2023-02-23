package com.maxwellolmen.grassbot.handler;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public interface Command {
    
    public void onCommand(MessageChannel channel, User user, String command);
}
