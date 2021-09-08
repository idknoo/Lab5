package commands;

import collection.CollectionWorker;
import message.Message;

public class Help implements Command {

    @Override
    public Message execute(CollectionWorker collectionWorker) {
        return collectionWorker.help();
    }
}

