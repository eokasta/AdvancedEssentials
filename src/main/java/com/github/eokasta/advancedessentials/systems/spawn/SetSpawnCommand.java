package com.github.eokasta.advancedessentials.systems.spawn;

import com.github.eokasta.advancedessentials.AdvancedEssentials;
import com.github.eokasta.advancedessentials.utils.Helper;
import com.github.eokasta.commandlib.CommandLib;
import com.github.eokasta.commandlib.annotations.CommandInformation;
import com.github.eokasta.commandlib.providers.CLCommand;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
@CommandInformation(name = "setspawn",
        onlyPlayer = true,
        permission = Helper.COMMAND_PERMISSION + "setspawn",
        description = "Set the server spawn point")
public class SetSpawnCommand extends CLCommand {

    private final AdvancedEssentials plugin;

    public SetSpawnCommand(AdvancedEssentials plugin) {
        this.plugin = plugin;

        setNoPermissionMessage(plugin.getMessages().safeOf("no-permission"));
        CommandLib.register(plugin, this);
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        final Player player = (Player) sender;
        plugin.getLocations().setSafe("spawn", player.getLocation());
        player.sendMessage(plugin.getMessages().formatted("set-spawn"));
    }

}
