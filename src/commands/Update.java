package commands;

import city.City;
import collection.CollectionWorker;
import message.Message;

public class Update implements Command {
    private final City city;
    private final Long id;

    public Update(City city, Long id){
        this.city = city;
        this.id = id;
    }

    @Override
    public Message execute(CollectionWorker collectionWorker) {
        return collectionWorker.update(city, id);
    }
}


