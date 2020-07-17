package com.github.eokasta.advancedessentials;

import com.github.eokasta.advancedessentials.systems.back.BackSystem;
import com.github.eokasta.advancedessentials.systems.spawn.SetSpawnCommand;
import com.github.eokasta.advancedessentials.systems.spawn.SpawnCommand;
import com.github.eokasta.advancedessentials.systems.spawn.SpawnListener;
import com.github.eokasta.advancedessentials.systems.teleport.TeleportCommand;
import com.github.eokasta.advancedessentials.systems.warp.DelWarpCommand;
import com.github.eokasta.advancedessentials.systems.warp.SetWarpCommand;
import com.github.eokasta.advancedessentials.systems.warp.WarpCommand;
import lombok.Data;

@Data
public class Manager {

    private final AdvancedEssentials plugin;

    private SetSpawnCommand setSpawnCommand;
    private SpawnCommand spawnCommand;
    private SpawnListener spawnListener;
    private BackSystem backSystem;
    private TeleportCommand teleportCommand;
    private SetWarpCommand setWarpCommand;
    private WarpCommand warpCommand;
    private DelWarpCommand delWarpCommand;

    public Manager(AdvancedEssentials plugin) {
        this.plugin = plugin;

        this.setSpawnCommand = new SetSpawnCommand(plugin);
        this.spawnCommand = new SpawnCommand(plugin);
        this.spawnListener = new SpawnListener(plugin);
        this.backSystem = new BackSystem(plugin);
        this.teleportCommand = new TeleportCommand(plugin);
        this.setWarpCommand = new SetWarpCommand(plugin);
        this.warpCommand = new WarpCommand(plugin);
        this.delWarpCommand = new DelWarpCommand(plugin);

    }
}
