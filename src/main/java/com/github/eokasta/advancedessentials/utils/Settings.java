package com.github.eokasta.advancedessentials.utils;

import com.github.eokasta.advancedessentials.AdvancedEssentials;
import org.bukkit.configuration.file.FileConfiguration;

public class Settings {

    private final YamlConfig file;
    private final AdvancedEssentials plugin;

    public Settings(AdvancedEssentials plugin) {
        this.plugin = plugin;
        this.file = new YamlConfig("config.yml", plugin, true);
    }

    public String getMessageLang() { return file.getConfig().getString("message-language", "en"); }

    public YamlConfig getFile() { return file; }

    public FileConfiguration getConfig() { return file.getConfig(); }

    public AdvancedEssentials getPlugin() { return plugin; }
}
