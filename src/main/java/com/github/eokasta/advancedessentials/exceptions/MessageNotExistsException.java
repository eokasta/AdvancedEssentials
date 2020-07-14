package com.github.eokasta.advancedessentials.exceptions;

public class MessageNotExistsException extends Exception {

    public MessageNotExistsException(String path) {
        super(String.format("Message path %s was not found.", path));
    }

}
