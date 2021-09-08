package commands;

import collection.CollectionWorker;
import message.Message;

public class Execute_script implements Command {
    private final Command[] commands;

    public Execute_script(Command[] commands) {
        this.commands=commands;
    }


    @Override
    public Message execute(CollectionWorker collectionWorker) {
        return null;
    }

    public Command[] getCommands() {
        return commands;
    }
}
