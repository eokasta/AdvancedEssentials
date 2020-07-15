package com.github.eokasta.advancedessentials.systems.back;

import com.github.eokasta.advancedessentials.AdvancedEssentials;
import com.github.eokasta.advancedessentials.utils.Helper;
import com.github.eokasta.commandlib.CommandLib;
import com.github.eokasta.commandlib.annotations.CommandInformation;
import com.github.eokasta.commandlib.providers.CLCommand;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Map;

@Getter
@CommandInformation(name = {"back", "voltar"},
        permission = Helper.COMMAND_PERMISSION + "back",
        onlyPlayer = true,
        description = "Go back to the last place where you teleported")
public class BackSystem extends CLCommand implements Listener {

    private final AdvancedEssentials plugin;
    private final Map<String, Location> backLocation;

    public BackSystem(AdvancedEssentials plugin) {
        this.plugin = plugin;
        this.backLocation = Maps.newHashMap();

        setNoPermissionMessage(plugin.getMessages().safeOf("no-permission"));
        CommandLib.register(plugin, this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        final Player player = (Player) sender;

        if (!backLocation.containsKey(player.getName())) {
            message(plugin.getMessages().formatted("no-back"));
            return;
        }

        player.teleport(backLocation.get(player.getName()), PlayerTeleportEvent.TeleportCause.COMMAND);
        backLocation.remove(player.getName());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        final Player player = event.getPlayer();
        if (!player.hasPermission(Helper.COMMAND_PERMISSION + "back"))
            return;

        if (plugin.getSettings().getConfig().getStringList(
                "block-back-with").contains(event.getCause().name()))
            return;

        backLocation.put(player.getName(), event.getFrom());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        if (plugin.getSettings().getConfig().getStringList(
                "block-back-with").contains("DEATH"))
            return;

        if (!player.hasPermission(Helper.COMMAND_PERMISSION + "back"))
            return;

        backLocation.put(player.getName(), player.getLocation());
    }
}
