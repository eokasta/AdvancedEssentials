package com.github.eokasta.advancedessentials;

import com.github.eokasta.advancedessentials.systems.spawn.SetSpawnCommand;
import com.github.eokasta.advancedessentials.systems.spawn.SpawnCommand;
import lombok.Data;

@Data
public class Manager {

    private final AdvancedEssentials plugin;

    public Manager(AdvancedEssentials plugin) {
        this.plugin = plugin;

        new SetSpawnCommand(plugin);
        new SpawnCommand(plugin);
    }
}
