package commands;

import collection.CollectionWorker;
import message.Message;

public class Print_descending implements Command {

    @Override
    public Message execute(CollectionWorker collectionManager) {
        return collectionManager.print_descending();
    }
}
