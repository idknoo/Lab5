package collection;

import city.City;
import message.Message;

public interface CollectionWorker {

    Message help();

    Message removeByID(Long id);

    Message remove_any_by_agglomeration(int agglomeration);

    Message clear();

    Message info();

    Message add(City city);

    Message show();

    Message update(City city, Long id);

    Message add_if_min(City city);

    Message add_if_max(City city);

    Message print_ascending();

    Message print_descending();

    Message remove_lower(City city);

    Message save();
}
