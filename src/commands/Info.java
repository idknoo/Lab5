package commands;

import collection.CollectionWorker;
import message.Message;

public class Info implements Command {

    @Override
    public Message execute(CollectionWorker collectionWorker) {
        return collectionWorker.info();
    }
}

