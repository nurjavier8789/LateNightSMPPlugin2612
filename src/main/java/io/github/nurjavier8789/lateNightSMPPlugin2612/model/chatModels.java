package io.github.nurjavier8789.lateNightSMPPlugin2612.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class chatModels {
    private final Map<UUID, String> activeChannel = new HashMap<>();

    public String getChannel(UUID uuid) {
        return activeChannel.getOrDefault(uuid, "global");
    }

    public void setChannel(UUID uuid, String channel) {
        activeChannel.put(uuid, channel.toLowerCase());
    }
}
