package com.github.eokasta.advancedessentials.systems.spawn;

import com.github.eokasta.advancedessentials.AdvancedEssentials;
import com.github.eokasta.advancedessentials.exceptions.LocationNotExistsException;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

@Getter
public class SpawnListener implements Listener {

    private final AdvancedEssentials plugin;

    public SpawnListener(AdvancedEssentials plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if (plugin.getSettings().getConfig().getBoolean("to-spawn-on-join", false)) {
            try {
                player.teleport(plugin.getLocations().of("spawn"), PlayerTeleportEvent.TeleportCause.PLUGIN);
            } catch (LocationNotExistsException e) {
                player.sendMessage(plugin.getMessages().formatted("spawn-not-defined"));
            }
        }
    }

}
