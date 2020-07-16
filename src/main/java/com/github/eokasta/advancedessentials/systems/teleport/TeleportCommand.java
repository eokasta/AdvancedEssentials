package com.github.eokasta.advancedessentials.systems.teleport;

import com.github.eokasta.advancedessentials.AdvancedEssentials;
import com.github.eokasta.advancedessentials.exceptions.SyntaxCommandException;
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
@CommandInformation(name = {"teleport", "tp"},
        permission = Helper.COMMAND_PERMISSION + "teleport",
        description = "Teleport to a player or coordinate",
        usage = "/teleport [player/coordinate] <target>")
public class TeleportCommand extends CLCommand {

    private final AdvancedEssentials plugin;

    public TeleportCommand(AdvancedEssentials plugin) {
        this.plugin = plugin;

        setNoPermissionMessage(plugin.getMessages().safeOf("no-permission"));
        CommandLib.register(plugin, this);
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        /*
        length=1: teleport to target
        length=2: player teleport to target
        length=3: teleport to coordinate
        */
        try {
            if (args.length == 0)
                throw new SyntaxCommandException(MessageFormat.format(plugin.getMessages().formatted(
                        "syntax-error"),
                        getUsage(),
                        getDescription())
                );

            if (args.length == 1) {
                if (!(sender instanceof Player))
                    throw new SyntaxCommandException(getNotAPlayerMessage());

                final Player player = (Player) sender;
                final Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null)
                    throw new CommandLibException(MessageFormat.format(plugin.getMessages().formatted(
                            "player-not-found"),
                            args[0])
                    );

                player.teleport(target.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
                player.sendMessage(MessageFormat.format(plugin.getMessages().formatted("teleported-to"), target.getName()));
                return;
            }

            if (args.length == 2) {
                final Player player = Bukkit.getPlayerExact(args[0]);
                final Player target = Bukkit.getPlayerExact(args[1]);
                if (player == null)
                    throw new CommandLibException(MessageFormat.format(plugin.getMessages().formatted(
                            "player-not-found"),
                            args[0])
                    );

                if (target == null)
                    throw new CommandLibException(MessageFormat.format(plugin.getMessages().formatted(
                            "player-not-found"),
                            args[1])
                    );

                player.teleport(target.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
                sender.sendMessage(MessageFormat.format(plugin.getMessages().formatted("teleport-other"), target.getName(), player.getName()));
                return;
            }

            final Double x = Helper.getDouble(args[0]), y = Helper.getDouble(args[1]), z = Helper.getDouble(args[2]);
            if (x == null || y == null || z == null)
                throw new SyntaxCommandException(MessageFormat.format(plugin.getMessages().formatted(
                        "syntax-error"),
                        getUsage(),
                        getDescription())
                );

            if (args.length == 3) {
                if (!(sender instanceof Player))
                    throw new SyntaxCommandException(getNotAPlayerMessage());

                final Player player = (Player) sender;
                player.teleport(new Location(player.getWorld(), x, y, z), PlayerTeleportEvent.TeleportCause.COMMAND);
                final StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(player.getWorld().getName()).append(", ");
                stringBuilder.append(x).append(", ");
                stringBuilder.append(y).append(", ");
                stringBuilder.append(z);

                message(MessageFormat.format(plugin.getMessages().formatted("teleported-to"), stringBuilder));

            } else {
                final Player target = Bukkit.getPlayerExact(args[3]);
                if (target == null)
                    throw new CommandLibException(MessageFormat.format(plugin.getMessages().formatted(
                            "player-not-found"),
                            args[3])
                    );
                target.teleport(new Location(target.getWorld(), x, y, z), PlayerTeleportEvent.TeleportCause.COMMAND);
                final StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(target.getWorld().getName()).append(", ");
                stringBuilder.append(x).append(", ");
                stringBuilder.append(y).append(", ");
                stringBuilder.append(z);

                message(MessageFormat.format(plugin.getMessages().formatted("teleport-other"), target.getName(), stringBuilder));
            }

        } catch (SyntaxCommandException | CommandLibException e) {
            message(e.getMessage());
        }
    }

}
