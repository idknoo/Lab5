package commands;

import collection.CollectionWorker;
import message.Message;

public class Save implements Command {

    @Override
    public Message execute(CollectionWorker collectionManager) {
        return collectionManager.save();
    }
}
