package commands;

import collection.CollectionWorker;
import message.Message;

public class Exit implements Command {

    @Override
    public Message execute(CollectionWorker collectionManager) {
        return new Message("Оффаем клиент. Тушите свет, ёпта.");
    }
}
