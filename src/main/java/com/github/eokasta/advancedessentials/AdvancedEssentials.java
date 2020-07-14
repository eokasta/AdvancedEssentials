package com.github.eokasta.advancedessentials;

import com.github.eokasta.advancedessentials.utils.Locations;
import com.github.eokasta.advancedessentials.utils.Messages;
import com.github.eokasta.advancedessentials.utils.Settings;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class AdvancedEssentials extends JavaPlugin {

    private Settings settings;
    private Messages messages;
    private Locations locations;
    private Manager manager;

    @Override
    public void onEnable() {
        this.settings = new Settings(this);
        this.messages = new Messages(this);
        this.locations = new Locations(this);
        this.manager = new Manager(this);

    }

    @Override
    public void onDisable() { }
}
