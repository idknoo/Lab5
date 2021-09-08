package commands;

import collection.CollectionWorker;
import message.Message;

public class Remove_by_id implements Command {
    private final Long id;

    public Remove_by_id(Long id) {
        this.id = id;
    }

    @Override
    public Message execute(CollectionWorker collectionWorker) {
        return collectionWorker.removeByID(id);
    }
}

