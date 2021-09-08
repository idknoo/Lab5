package commands;

import city.City;
import collection.CollectionWorker;
import message.Message;

public class Add_if_max implements Command {
    private final City city;

    public Add_if_max(City city) {
        this.city = city;
    }

    @Override
    public Message execute(CollectionWorker collectionWorker) { return collectionWorker.add_if_max(city); }
}
