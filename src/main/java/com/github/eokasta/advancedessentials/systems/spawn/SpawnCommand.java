package com.github.eokasta.advancedessentials.systems.spawn;

import com.github.eokasta.advancedessentials.AdvancedEssentials;
import com.github.eokasta.advancedessentials.exceptions.LocationNotExistsException;
import com.github.eokasta.advancedessentials.utils.Helper;
import com.github.eokasta.commandlib.CommandLib;
import com.github.eokasta.commandlib.annotations.CommandInformation;
import com.github.eokasta.commandlib.providers.CLCommand;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

@Getter
@CommandInformation(name = "spawn",
        permission = Helper.COMMAND_PERMISSION + "spawn",
        onlyPlayer = true)
public class SpawnCommand extends CLCommand {

    private final AdvancedEssentials plugin;

    public SpawnCommand(AdvancedEssentials plugin) {
        this.plugin = plugin;

        setNoPermissionMessage(plugin.getMessages().safeOf("no-permission"));
        CommandLib.register(plugin, this);
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        final Player player = (Player) sender;
        try {
            player.teleport(
                    plugin.getLocations().of("spawn"),
                    PlayerTeleportEvent.TeleportCause.COMMAND
            );
            player.sendMessage(plugin.getMessages().formatted("teleported-spawn"));
        } catch (LocationNotExistsException e) {
            player.sendMessage(plugin.getMessages().formatted("spawn-not-defined"));
        }
    }
}
