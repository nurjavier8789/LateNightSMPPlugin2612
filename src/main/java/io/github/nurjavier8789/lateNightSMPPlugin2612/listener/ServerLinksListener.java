package io.github.nurjavier8789.lateNightSMPPlugin2612.listener;

import java.net.URI;

import org.bukkit.ServerLinks;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import net.kyori.adventure.text.Component;

import org.bukkit.event.player.PlayerLinksSendEvent;

public class ServerLinksListener implements Listener {
    @EventHandler
    public void sendServerLink(PlayerLinksSendEvent event) {
        ServerLinks links = event.getLinks();

        links.addLink(Component.text("Test"), URI.create("https://google.com/"));

        links.addLink(ServerLinks.Type.SUPPORT, URI.create("https://discord.gg/muwrAQfe8r"));
        links.addLink(ServerLinks.Type.STATUS, URI.create("https://nurjavier8789.my.id/LNPPublicSheet.html"));
    }
}
