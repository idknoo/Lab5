package commands;

import collection.CollectionWorker;
import message.Message;

public interface Command {
    Message execute(CollectionWorker collectionManager);
}
