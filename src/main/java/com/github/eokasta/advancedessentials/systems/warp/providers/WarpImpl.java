package com.github.eokasta.advancedessentials.systems.warp.providers;

import com.github.eokasta.advancedessentials.AdvancedEssentials;
import com.github.eokasta.advancedessentials.exceptions.LocationNotExistsException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;

import java.util.Objects;

@RequiredArgsConstructor
@Data
public class WarpImpl implements Warp {

    private final AdvancedEssentials plugin;

    private String name;
    private Location location;

    public Warp byName(String name) throws LocationNotExistsException {
        this.name = name;
        this.location = plugin.getLocations().of(name);
        return this;
    }

    @Override
    public void save() {
        plugin.getLocations().setSafe(name, Objects.requireNonNull(location));
    }

    @Override
    public void delete() {
        plugin.getLocations().setSafe(name, null);
    }
}
