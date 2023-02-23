package com.maxwellolmen.grassbot.handler;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageHandler extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().getId().equals("1078162609641107486")) return;

        if (event.getChannel().getId().equals("1078169935706062928")) {
            // event.getChannel().sendMessage("I detected a message in the bot channel!").queue();
        }
    }
}