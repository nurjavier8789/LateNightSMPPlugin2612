package io.github.nurjavier8789.lateNightSMPPlugin2612;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomConfig {
    private final JavaPlugin plugin;
    private FileConfiguration customConfig;
    private File customConfigFile;
    private final String fileName;

    public CustomConfig(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        setup();
    }

    public void setup() {
        customConfigFile = new File(plugin.getDataFolder(), fileName);

        if (!customConfigFile.exists()) {
            plugin.saveResource(fileName, false); 
        }

        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
    }

    public FileConfiguration getConfig() {
        return customConfig;
    }

    public void reloadConfig() {
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
    }
}
