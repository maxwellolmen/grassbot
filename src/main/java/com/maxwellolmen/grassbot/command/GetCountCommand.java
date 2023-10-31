package com.maxwellolmen.grassbot.command;

import com.maxwellolmen.grassbot.handler.Command;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GetCountCommand implements Command {
    
    @Override
    public void onCommand(MessageReceivedEvent event, String command) {
        if (event.getMessage().getMentions().getUsers().size() == 0) {
            event.getChannel().sendMessage("You must mention a user!").queue();
            return;
        }
        User target = event.getMessage().getMentions().getUsers().get(0);

        int count = TouchGrassCommand.getCount(target.getId());

        String grassMsg;
        if (target.getId() == "1078162609641107486") { // GrassBot's ID
            grassMsg = "I don't have a GrassCount bro ";
        } else {
            grassMsg = target.getAsMention() + "'s GrassCount is " + count;
        }

        event.getChannel()
                .sendMessage(grassMsg)
                .queue();
    }

    @Override
    public String getUsage() {
        return "!getcount <user>";
    }

    @Override
    public String getDescription() {
        return "Check the current GrassCount of the mentioned user.";
    }

}