package handler.commands;

import storage.JsonStorage;

abstract public class Command{

    protected JsonStorage storage;

    public Command(JsonStorage storage) {
        this.storage = storage;

    }

    abstract public String execute(int userId, String arguments);
}
