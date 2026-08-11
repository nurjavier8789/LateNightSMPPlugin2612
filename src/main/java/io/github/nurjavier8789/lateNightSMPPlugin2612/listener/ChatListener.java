package io.github.nurjavier8789.lateNightSMPPlugin2612.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import io.github.nurjavier8789.lateNightSMPPlugin2612.model.chatModels;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class ChatListener implements Listener {
    private final chatModels chatModels;

    public ChatListener(chatModels chatModels) {
        this.chatModels = chatModels;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void whenChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        String channel = chatModels.getChannel(player.getUniqueId());

        if (channel.equals("mod")) {
            event.viewers().removeIf(audience -> {
                if (audience instanceof Player) {
                    Player target = (Player) audience;
                    return !target.hasPermission("latenightsmpplugin.moderator.chat");
                }
                return false;
            });

            event.renderer(ChatRenderer.viewerUnaware((source, displaySource, msg) -> {
                return Component.text("[MOD] ")
                        .color(NamedTextColor.DARK_GREEN)
                        .append(displaySource.color(NamedTextColor.GREEN))
                        .append(Component.text(" » ").color(NamedTextColor.DARK_GRAY))
                        .append(msg.color(NamedTextColor.WHITE));
            }));
        } else if (channel.equals("global")) {}
    }
}
