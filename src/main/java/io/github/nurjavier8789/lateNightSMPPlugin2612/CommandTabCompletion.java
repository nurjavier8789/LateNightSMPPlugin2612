package io.github.nurjavier8789.lateNightSMPPlugin2612;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.List;
import java.util.ArrayList;

public class CommandTabCompletion implements Listener {
    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        String buffer = event.getBuffer();

        if (buffer.toLowerCase().startsWith("/help ")) {
            List<String> newRekom = new ArrayList<>();

            newRekom.add("skin");

            event.setCompletions(newRekom);
        }
    }
}
