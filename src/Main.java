
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import commands.Command;
import commands.Execute_script;
import commands.Exit;
import message.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

//        XmlMapper xmlMapper = new XmlMapper();
//        xmlMapper.registerModule(new JavaTimeModule());
//        xmlMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        FileWorker fileWorker = new FileWorker(
//                new File("output.xml"),
//                xmlMapper);

//        File file = new File("collection.xml");
//        System.out.println(file.getAbsolutePath());
//        ArrayList<City> q = fileWorker.parse(file);
//        System.out.println(q);

//        City city = new City("name", new Coordinates(10, 10), ZonedDateTime.now(), 1, 2, 3, 4, Government.COMMUNISM, new Human(13, LocalDate.now()), Climate.RAIN_FOREST);
//        ArrayList<City> queue = new ArrayList<>();
//        queue.add(city);
//        try {
//            fileWorker.serialize(queue);
//        } catch (Exception e) {
//            System.out.println(e);
//        }

        String filename = System.getenv("filename");
        if (filename == null) {
            System.out.println("Не задано имя файла");
            System.exit(1);
        }

        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Файл не существует");
            System.exit(1);
        }

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        FileWorker fileWorker = new FileWorker(file, xmlMapper);
        CollectionWorkerImpl scw = new CollectionWorkerImpl(fileWorker);

        try {
            Scanner scan = new Scanner(System.in);
            CommandListener commandListener = new CommandListener(System.in, false);
            do {
                System.out.println("Введите комманду.");
                System.out.print("> ");
                Command cmd = commandListener.readCommand();
                if (cmd instanceof Exit) {
                    scan.close();
                    System.out.println("Завершение работы.");
                    System.exit(0);
                } else {
                    if (cmd instanceof Execute_script) {
                        for (Command c : ((Execute_script) cmd).getCommands()) {
                            Message msg = scw.execute(c);
                            System.out.println(msg.getContent());
                        }
                    } else {
                        Message msg = scw.execute(cmd);
                        System.out.println(msg.getContent());
                    }
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
