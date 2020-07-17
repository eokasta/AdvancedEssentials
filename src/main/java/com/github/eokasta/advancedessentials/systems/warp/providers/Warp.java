package com.github.eokasta.advancedessentials.systems.warp.providers;

import com.github.eokasta.advancedessentials.exceptions.LocationNotExistsException;
import org.bukkit.Location;

public interface Warp {

    String getName();
    Location getLocation();
    void setName(String name);
    void setLocation(Location location);
    Warp byName(String name) throws LocationNotExistsException;
    void save();
    void delete();

}
