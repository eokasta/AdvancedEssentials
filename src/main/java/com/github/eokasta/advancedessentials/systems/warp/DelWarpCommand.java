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
import org.bukkit.command.CommandSender;

import java.text.MessageFormat;

@Getter
@CommandInformation(name = {"delwarp", "deletewarp"},
        permission = Helper.COMMAND_PERMISSION + "delwarp",
        description = "Delete a warp",
        usage = "/delwarp [warp]")
public class DelWarpCommand extends CLCommand {

    private final AdvancedEssentials plugin;

    public DelWarpCommand(AdvancedEssentials plugin) {
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

        try {
            final Warp warp = new WarpImpl(plugin).byName(args[0]);
            warp.delete();
            message(MessageFormat.format(plugin.getMessages().safeOf("warp-deleted"), warp.getName()));
        } catch (LocationNotExistsException e) {
            message(MessageFormat.format(plugin.getMessages().safeOf("warp-not-found"), args[0].toLowerCase()));
        }
    }
}
