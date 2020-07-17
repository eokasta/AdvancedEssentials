package com.github.eokasta.advancedessentials.systems.warp;

import com.github.eokasta.advancedessentials.AdvancedEssentials;
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
import org.bukkit.entity.Player;

import java.text.MessageFormat;

@Getter
@CommandInformation(name = "setwarp",
        permission = Helper.COMMAND_PERMISSION + "setwarp",
        onlyPlayer = true,
        description = "Set a new warp where you are",
        usage = "/setwarp [warp]")
public class SetWarpCommand extends CLCommand {

    private final AdvancedEssentials plugin;

    public SetWarpCommand(AdvancedEssentials plugin) {
        this.plugin = plugin;

        setNoPermissionMessage(plugin.getMessages().safeOf("no-permission"));
        CommandLib.register(plugin, this);
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) throws CommandLibException {
        final Player player = (Player) sender;
        if (args.length == 0)
            throw new SyntaxCommandException(MessageFormat.format(plugin.getMessages().formatted(
                    "syntax-error"),
                    getUsage(),
                    getDescription())
            );

        final Warp warp = new WarpImpl(plugin);
        warp.setName(args[0].toLowerCase());
        warp.setLocation(player.getLocation());
        warp.save();
        message(MessageFormat.format(plugin.getMessages().formatted("warp-setted"), warp.getName()));
    }
}
