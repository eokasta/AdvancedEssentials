package com.github.eokasta.advancedessentials.utils;

import com.github.eokasta.advancedessentials.AdvancedEssentials;
import com.github.eokasta.advancedessentials.exceptions.LocationNotExistsException;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;

public class Locations {

    public final AdvancedEssentials plugin;
    public final JsonDocument file;

    public Locations(AdvancedEssentials plugin) {
        this.plugin = plugin;
        this.file = JsonDocument.safeOf(
            new File(plugin.getDataFolder() + "/data"),
            "locations.json"
            );

}

    public Location of(String path) throws LocationNotExistsException {
        if (file.get(path).isJsonNull())
            throw new LocationNotExistsException(path);

        return Helper.deserializeLocation(file.get(path).getAsString());
    }

    public void set(String path, Location location) throws IOException {
        file.set(path, Helper.serializeLocation(location));
        file.save();
    }

    public void setSafe(String path, Location location) {
        try {
            set(path, location);
        } catch (IOException ignored) { }
    }

}
