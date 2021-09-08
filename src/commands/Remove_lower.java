package commands;

import city.City;
import collection.CollectionWorker;
import message.Message;

public class Remove_lower implements Command {
    private final City city;

    public Remove_lower(City city) {
        this.city = city;
    }

    @Override
    public Message execute(CollectionWorker collectionWorker) {
        return collectionWorker.remove_lower(city);
    }
}
