package city;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.Date;

/**
 * класс человека
 */
public class Human {
    private long age; //Значение поля должно быть больше 0
    //    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;

    public Human() {
    }

    public Human(long age, LocalDate birthday) {
        this.age = age;
        this.birthday = birthday;
    }

    public String toString() {
        return (age + " " + birthday);
    }

    public long getAge() {
        return age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}

