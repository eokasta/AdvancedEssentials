package com.github.eokasta.advancedessentials.utils;

import com.github.eokasta.advancedessentials.AdvancedEssentials;
import com.github.eokasta.advancedessentials.exceptions.MessageNotExistsException;
import lombok.Data;

@Data
public class Messages {

    private final YamlConfig file;
    private final AdvancedEssentials plugin;

    public Messages(AdvancedEssentials plugin) {
        this.plugin = plugin;
        this.file = new YamlConfig(
                String.format(
                        "messages/%s.yml", plugin.getSettings().getMessageLang()
                ), plugin, true);
    }

    public String of(String path) throws MessageNotExistsException {
        if (file.getConfig().getString(path) == null)
            throw new MessageNotExistsException(path);

        return file.getConfig().getString(path);
    }

    public String safeOf(String path) {
        try {
            return of(path);
        } catch (MessageNotExistsException e) {
            return e.getMessage();
        }
    }

    public String formatted(String path) {
        return Helper.format(safeOf(path));
    }

}
