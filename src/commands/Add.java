package commands;

import city.City;
import collection.CollectionWorker;
import message.Message;

public class Add implements Command {
    private final City city;

    public Add(City city) {
        this.city = city;
    }

        @Override
        public Message execute(CollectionWorker collectionWorker) {
            return collectionWorker.add(city);
        }
    }
