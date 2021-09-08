package commands;

import collection.CollectionWorker;
import message.Message;

public class Show implements Command{
    @Override
    public Message execute(CollectionWorker collectionWorker) {
        return collectionWorker.show();
    }
}

