package commands;

import collection.CollectionWorker;
import message.Message;

public class Print_ascending implements Command {

    @Override
    public Message execute(CollectionWorker collectionManager) {
        return collectionManager.print_ascending();
    }
}
