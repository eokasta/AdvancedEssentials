package com.github.eokasta.advancedessentials.systems.warp;

import com.github.eokasta.advancedessentials.AdvancedEssentials;
import com.github.eokasta.advancedessentials.exceptions.LocationNotExistsException;
import com.github.eokasta.advancedessentials.systems.warp.providers.Warp;
import com.github.eokasta.advancedessentials.systems.warp.providers.WarpImpl;
import com.github.eokasta.advancedessentials.utils.Helper;
import com.github.eokasta.commandlib.CommandLib;
import com.github.eokasta.commandlib.annotations.CommandInformation;
import com.github.eokasta.commandlib.exceptions.CommandLibException;
import com.github.eokasta.commandlib.exceptions.SyntaxCommandException;
import com.github.eokasta.commandlib.providers.CLCommand;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.text.MessageFormat;

@Getter
@CommandInformation(name = "warp",
        usage = "/warp [warp]",
        description = "Teleport to a warp",
        permission = Helper.COMMAND_PERMISSION + "warp")
public class WarpCommand extends CLCommand {

    private final AdvancedEssentials plugin;

    public WarpCommand(AdvancedEssentials plugin) {
        this.plugin = plugin;

        setNoPermissionMessage(plugin.getMessages().safeOf("no-permission"));
        CommandLib.register(plugin, this);
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) throws CommandLibException {

        if (args.length == 0)
            throw new SyntaxCommandException(MessageFormat.format(plugin.getMessages().formatted(
                    "syntax-error"),
                    getUsage(),
                    getDescription())
            );

        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                message(getNotAPlayerMessage());
                return;
            }
            final Player player = (Player) sender;

            try {
                final Warp warp = new WarpImpl(plugin).byName(args[0].toLowerCase());
                if (player.hasPermission(Helper.COMMAND_PERMISSION + "warp." + warp.getName()))
                    teleportWarpSafe(player, warp);
                else
                    player.sendMessage(MessageFormat.format(plugin.getMessages().formatted("no-permission-to-teleport-warp"), warp.getName()));
            } catch (LocationNotExistsException e) {
                player.sendMessage(MessageFormat.format(plugin.getMessages().formatted("warp-not-found"), args[0].toLowerCase()));
            }

            return;
        }

        if (!sender.hasPermission(Helper.COMMAND_PERMISSION + "warp.other"))
            throw new SyntaxCommandException(getNoPermissionMessage());

        final Player target = Bukkit.getPlayerExact(args[1]);
        if (target == null)
            throw new SyntaxCommandException(
                    MessageFormat.format(plugin.getMessages().formatted("player-not-found"), args[1])
            );

        try {
            final Warp warp = new WarpImpl(plugin).byName(args[0].toLowerCase());
            teleportWarpSafe(target, warp);
            message(MessageFormat.format(
                    plugin.getMessages().formatted("teleport-warp-other"),
                    target.getName(), warp.getName())
            );
        } catch (LocationNotExistsException e) {
            message(MessageFormat.format(plugin.getMessages().formatted("warp-not-found"), args[0].toLowerCase()));
        }
    }

    public void teleportWarpSafe(Player player, Warp warp) {
        player.teleport(warp.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
        player.sendMessage(MessageFormat.format(plugin.getMessages().formatted("teleport-to-warp"), warp.getName()));
    }

}
