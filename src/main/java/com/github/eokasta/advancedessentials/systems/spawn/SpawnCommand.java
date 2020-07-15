package com.github.eokasta.advancedessentials.systems.spawn;

import com.github.eokasta.advancedessentials.AdvancedEssentials;
import com.github.eokasta.advancedessentials.exceptions.LocationNotExistsException;
import com.github.eokasta.advancedessentials.utils.Helper;
import com.github.eokasta.commandlib.CommandLib;
import com.github.eokasta.commandlib.annotations.CommandInformation;
import com.github.eokasta.commandlib.exceptions.CommandLibException;
import com.github.eokasta.commandlib.providers.CLCommand;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.text.MessageFormat;

@Getter
@CommandInformation(name = "spawn",
        permission = Helper.COMMAND_PERMISSION + "spawn",
        description = "Teleport to the server spawn point")
public class SpawnCommand extends CLCommand {

    private final AdvancedEssentials plugin;

    public SpawnCommand(AdvancedEssentials plugin) {
        this.plugin = plugin;

        setNoPermissionMessage(plugin.getMessages().safeOf("no-permission"));
        CommandLib.register(plugin, this);
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        try {
            if (args.length > 0 && sender.hasPermission(Helper.COMMAND_PERMISSION + "spawn.other")) {
                final Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null)
                    throw new CommandLibException(
                            MessageFormat.format(plugin.getMessages().formatted("player-not-found"), args[0])
                    );

                teleportToSpawn(target);
                message(MessageFormat.format(
                        plugin.getMessages().formatted("teleported-spawn-other"),
                        target.getName())
                );

                return;
            }

            if (!(sender instanceof Player)) {
                message(getNotAPlayerMessage());
                return;
            }

            final Player player = (Player) sender;
            teleportToSpawn(player);

        } catch (LocationNotExistsException e) {
            message(plugin.getMessages().formatted("spawn-not-defined"));
        } catch (CommandLibException e) {
            message(e.getMessage());
        }

    }

    public void teleportToSpawn(Player player) throws LocationNotExistsException{
        final Location spawnLocation = plugin.getLocations().of("spawn");

        player.teleport(spawnLocation, PlayerTeleportEvent.TeleportCause.COMMAND);
        player.sendMessage(plugin.getMessages().formatted("teleported-spawn"));
    }

    public void safeTeleportToSpawn(Player player) {
        try {
            teleportToSpawn(player);
        } catch (LocationNotExistsException e) {
            player.sendMessage(plugin.getMessages().formatted("spawn-not-defined"));
        }
    }
}
