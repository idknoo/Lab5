package commands;

import collection.CollectionWorker;
import message.Message;

public class Remove_any_by_agglomeration implements Command {

    int agglomeration;

    public Remove_any_by_agglomeration(int agglomeration) {
        this.agglomeration = agglomeration;
    }

    @Override
    public Message execute(CollectionWorker collectionManager) {
        return collectionManager.remove_any_by_agglomeration(agglomeration);
    }
}
