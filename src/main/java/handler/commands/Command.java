package handler.commands;

import handler.HandlerState;
import storage.JsonStorage;

abstract public class Command{

    protected JsonStorage storage;

    public Command(JsonStorage storage) {
        this.storage = storage;

    }

    abstract public String execute(int userId, String arguments);
    abstract public HandlerState getState();
}
