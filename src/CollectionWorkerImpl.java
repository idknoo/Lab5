import city.City;
import collection.CollectionWorker;
import commands.Command;
import message.CollectionMessage;
import message.Message;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionWorkerImpl implements CollectionWorker {

    private final LocalDateTime time;
    private final LinkedHashSet<City> cities;
    private final FileWorker fileWorker;

    public CollectionWorkerImpl(FileWorker fileWorker) {
        this.fileWorker = fileWorker;
        this.time = LocalDateTime.now();
        cities = new LinkedHashSet<>();
        readCities();
    }

    @Override
    public Message help() {
        return new Message("Вызвана команда help, идёт вывод доступных комманд.\n" +
                "help : вывести справку по доступным командам.\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.).\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении.\n" +
                "add {element} : добавить новый элемент в коллекцию.\n" +
                "update {id} : обновить значение элемента коллекции, id которого равен заданному.\n" +
                "remove_by_id {id} : удалить элемент из коллекции по его id.\n" +
                "clear : очистить коллекцию.\n" +
                "execute_script : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "add_if_max {element} : добавить новый элемент, если он превышает максимальный элемент коллекции.\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный.\n" +
                "remove_any_by_agglomeration agglomeration : удалить из коллекции один элемент, значение поля agglomeration которого эквивалентно заданному.\n" +
                "print_ascending : вывести элементы коллекции в порядке возрастания.\n" +
                "print_descending : вывести элементы коллекции в порядке убывания");
    }

    @Override
    public Message removeByID(Long id) {
        City city = cities.stream().filter(h -> h.getId().equals(id)).findAny().orElse(null);
        if (city == null) {
            return new Message("Элемент не найден");
        }
        cities.remove(city);
        return new Message("Элемент удален");
    }

    @Override
    public Message remove_any_by_agglomeration(int agglomeration) {
        ArrayList<City> toRemove = cities.stream()
                .filter(h -> h.getAgglomeration() == agglomeration)
                .collect(Collectors.toCollection(ArrayList::new));

        Random rand = new Random();
        int index = rand.nextInt(toRemove.size());
        City removedCity = toRemove.remove(index);

        return new Message("Город был удален: " + removedCity);
    }

    @Override
    public Message clear() {
        if (cities.size() > 0) {
            cities.clear();
            return new Message("Все элементы удалены");
        } else {
            return new Message("У вас нет элементов для удаления");
        }
    }

    private City getMaxCity() {
        return cities.stream().max(City::compareTo).orElse(null);
    }

    private City getMinCity() {
        return cities.stream().min(City::compareTo).orElse(null);
    }

    @Override
    public Message info() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Вызвана команда info. Информация о коллекции:");
        strBuilder.append("\nТип коллекции: " + cities.getClass().getName());
        strBuilder.append("\nКоллекция содержит элементы класса: City");
        strBuilder.append(String.format("\nКоличество элементов коллекции: %d\n", this.cities.size()));
        if (this.cities.size() > 0) {
            strBuilder.append(String.format("\nДата инициализации коллекции: %s\n", this.time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
            strBuilder.append(String.format("\nМаксимальный элемент коллекции: \n%s\n", this.getMaxCity().toString()));
        }
        return new Message(strBuilder.toString());
    }

    @Override
    public Message add(City city) {
        cities.add(city);
        return new Message("Город добавлен");
    }

    @Override
    public Message show() {
        return new CollectionMessage(cities.toString(), cities.toArray(new City[0]));
    }

    @Override
    public Message update(City newCity, Long id) {
        City toRemove = cities.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (toRemove == null)
            return new Message("Города с id " + id + " не существует.");

        newCity.setId(id);

        cities.remove(toRemove);
        cities.add(newCity);
        return new Message("Город с id == " + id + " обновлён.");
    }

    @Override
    public Message add_if_min(City city) {
        long elementsLowerThanThis = cities.stream()
                .filter(p -> p.compareTo(city) <= 0)
                .count();

        return elementsLowerThanThis == 0
                ? add(city)
                : new Message("Элемент превышает минимальный элемент в коллекции и не был добавлен.");
    }

    @Override
    public Message add_if_max(City city) {
        long elementsLargerThanThis = cities.stream()
                .filter(p -> p.compareTo(city) >= 0)
                .count();

        return elementsLargerThanThis == 0
                ? add(city)
                : new Message("Элемент не превышает наибольший элемент коллекции и не был добавлен.");
    }

    @Override
    public Message print_ascending() {
        return getCitiesMessage(cities.stream().sorted());
    }

    @Override
    public Message print_descending() {
        return getCitiesMessage(cities.stream().sorted(Comparator.reverseOrder()));
    }

    @Override
    public Message remove_lower(City city) {
        List<City> toRemove = cities.stream()
                .filter(p -> p.compareTo(city) < 0)
                .collect(Collectors.toList());

        int[] cnt = new int[]{0};
        toRemove.forEach(p -> {
            cities.remove(p);
            ++cnt[0];
        });

        String answer = cnt[0] + " городишек уничтожено.";
        if (toRemove.size() != cnt[0])
            answer += "\n Ошибка БД.";

        return new Message(answer);
    }

    @Override
    public Message save() {
        try {
            if (cities.size() == 0) {
                fileWorker.clear();
                return new Message("коллекция пуста. файл удален");
            }
            fileWorker.serialize(new ArrayList<>(cities));
            return new Message("коллекция сохранена");
        } catch (IOException e) {
            e.printStackTrace();
            return new Message("не удалось сохранить коллекцию");
        }
    }

    private Message getCitiesMessage(Stream<City> cityStream) {
        String[] strings = cityStream
                .map(City::toString)
                .toArray(String[]::new);
        return new Message(String.join("\n", strings));
    }

    private void readCities() {
        ArrayList<City> cities = fileWorker.parse();
        for (City city : cities) {
            add(city);
        }
    }

    public LinkedHashSet<City> getCollection() {
        return cities;
    }

    public Message execute(Command command) {
        return command.execute(this);
    }
}
