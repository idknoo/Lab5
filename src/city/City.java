package city;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.PriorityQueue;

/**
 * Класс города для описания хранящихся в коллекции данных
 */
public class City implements Comparable<City>, Serializable {
    /**
     * ID города Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
     */
    private long id;
    private static long numOfCities = 0;
    /**
     * Название города. Поле не может быть null, Строка не может быть пустой
     */
    private String name;
    /**
     * Координаты города. Не может быть null.
     */
    private Coordinates coordinates;
    /**
     * Время создания организации.
     * Поле не может быть null, Значение этого поля должно генерироваться автоматически
     */
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private ZonedDateTime creationDateTime;
    /**
     * Площадь города. Значение поля должно быть больше 0
     */
    private double area;
    /**
     * Значение населения. Значение поля должно быть больше 0, Поле не может быть null
     * ну типо кек, город без людей же есть 100%, что за дискриминация а?!)
     */
    private long population;
    /**
     * Географическое указание высоты над уровнем моря.
     */
    private double metersAboveSeaLevel;
    /**
     * Значение поля должно быть больше 0.
     */
    private int agglomeration;
    /**
     * ну это надеюсь описывать не надо... Поле может быть null
     */
    private Climate climate;//Поле может быть null
    /**
     * Поле с государственным строем.
     * Классная идея реализации через перечисление, т.к. даёт идею для написания консольной стратегической игры с видами гос-в.
     * очень удобно для описания классов в играх.
     * Поле может быть null
     */
    private Government government;

    private Human governor;//не может быть нулл.

    public City(String name, Coordinates coordinates, ZonedDateTime creationDateTime, double area, long population, int agglomeration, double metersAboveSeaLevel, Government government, Human governor, Climate climate) {

        this.name = name;
        this.coordinates = coordinates;
        this.creationDateTime = creationDateTime;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.agglomeration = agglomeration;
        this.government = government;
        this.governor = governor;
        this.climate = climate;
    }

    public City() {
    } //конструктор для использования в команде Add

    //Далее идут геттеры и сеттеры

    /**
     * Метод для установки названия города
     *
     * @param name - имя
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDateTime(ZonedDateTime date) {
        this.creationDateTime = date;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public void setAgglomeration(int agglomeration) {
        this.agglomeration = agglomeration;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public void setMetersAboveSeaLevel(double metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    public void setGovernor(Human governor) {
        this.governor = governor;
    }

    // геттеры
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public String getName() {
        return this.name;
    }

    public Long getId() {
        return this.id;
    }

    public double getArea() {
        return this.area;
    }

    public long getPopulation() {
        return this.population;
    }

    public ZonedDateTime getCreationDateTime() {
        return this.creationDateTime;
    }

    public int getAgglomeration() {
        return this.agglomeration;
    }

    public Government getGovernment() {
        return this.government;
    }

    public double getMetersAboveSeaLevel() {
        return this.metersAboveSeaLevel;
    }

    public Climate getClimate() {
        return this.climate;
    }

    public Human getGovernor() {
        return this.governor;
    }

    public void generateID() {
        id = ++numOfCities;
    }

    @Override
    public String toString() {
        return (this.name + "; " + this.id + "; " + this.governor + "; " + this.climate + "; " + this.agglomeration + "; " + this.population + "; " + this.getMetersAboveSeaLevel() + "; " + this.government.toString() + "; " + this.area);
    }

    public static boolean idIsUnique(Long id, PriorityQueue<City> queue) {
        for (City city : queue) {
            if (city.getId() == id) {
                return false;
            }
        }
        return true;
    }

    // Сравнение объектов из коллекции по id
    @Override
    public int compareTo(City o) {
        return (int) (this.agglomeration - ((City) o).agglomeration);
    }
    //Результат положителен если объект больше сравнимаего, отрицателен, если меньше и равен 0, если объекты равны.
}
