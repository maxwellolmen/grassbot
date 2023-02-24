package com.maxwellolmen.grassbot.handler;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageHandler extends ListenerAdapter {

    private CommandHandler commandHandler;

    public MessageHandler() {
        commandHandler = new CommandHandler();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().getId().equals("1078162609641107486"))
            return;

        if (!event.getChannel().getId().equals("1078169935706062928")) {
            return;
        }

        String message = event.getMessage().getContentStripped();

        if (message.startsWith("!")) {
            commandHandler.handle(event, message);
        }
    }
}