package com.maxwellolmen.grassbot.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.maxwellolmen.grassbot.GrassBot;
import com.maxwellolmen.grassbot.handler.Command;
import com.maxwellolmen.grassbot.sql.SQLSaver;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TouchGrassLeaderboardCommand implements Command, SQLSaver {
    @Override
    public void onCommand(MessageReceivedEvent event, String command) {
        String message = "";
        try {
            Map<String, Integer> map = GrassBot.sqlManager.getGrassCounts();
            Map<String, Integer> sorted =
            map.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(
                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));;
            
            int place = 0;
            for (String key: sorted.keySet()){
                place++;
                User user = GrassBot.getJDA().retrieveUserById(key).complete();
                message += place + ": " + user.getName() + "\n";  
            }
            event.getChannel()
            .sendMessage("Grass Leaderboard!!!!\n" + message)
            .queue();
            Iterator<String> namesIterator = sorted.keySet().iterator();
            if (namesIterator.hasNext()){
                User firstPlace = GrassBot.getJDA().retrieveUserById(namesIterator.next()).complete();
                event.getChannel()
                .sendMessage(firstPlace.getName() + " really needs to touch some grass!!!!")
                .queue();
            }
           
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    @Override
    public void autosave() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'autosave'");
    }
    
}
