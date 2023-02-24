package com.maxwellolmen.grassbot.handler;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {

    public void onCommand(MessageReceivedEvent event, String command);
}
