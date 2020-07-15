package com.github.eokasta.advancedessentials.exceptions;

public class LocationNotExistsException extends AEPluginException {

    public LocationNotExistsException(String path) {
        super(String.format("Location path %s was not found.", path));
    }

}
