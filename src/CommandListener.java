import city.*;
import commands.*;

import java.io.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class CommandListener {
    private final BufferedReader input;
    private final Boolean isScript;

    public CommandListener(InputStream inputStream, boolean isScript) {
        this.isScript = isScript;
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        this.input = input;
    }

    public Command readCommand() throws IOException {
        String line = null;
        try {
            line = input.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (line == null) throw new RuntimeException("Ctrl+D");
        String[] cmd = line.split(" ");

        switch (cmd[0]) {
            case "help":
                if (cmd.length == 1) return new Help();
                else {
                    break;
                }
            case "info":
                if (cmd.length == 1) return new Info();
                else {
                    break;
                }
            case "show":
                if (cmd.length == 1) return new Show();
                else {
                    break;
                }
            case "print_ascending":
                if (cmd.length == 1) return new Print_ascending();
                else {
                    break;
                }
            case "print_descending":
                if (cmd.length == 1) return new Print_descending();
                else {
                    break;
                }
            case "clear":
                if (cmd.length == 1) return new Clear();
                else {
                    break;
                }
            case "add":
                return new Add(readCity());
            case "add_if_max":
                return new Add_if_max(readCity());
            case "remove_by_id":
                if (cmd.length == 2)
                    return new Remove_by_id(Long.parseLong(cmd[1]));
                else {
                    break;
                }
            case "remove_any_by_agglomeration":
                if (cmd.length == 2)
                    return new Remove_any_by_agglomeration(Integer.parseInt(cmd[1]));
                else {
                    break;
                }
            case "remove_lower":
                if (cmd.length == 1)
                    return new Remove_lower(readCity());
                else {
                    break;
                }
            case "update":
                if (cmd.length == 2)
                    return new Update(readCity(), Long.parseLong(cmd[1]));
                else {
                    break;
                }
            case "save":
                if (cmd.length == 1) return new Save();
                else {
                    break;
                }
            case "exit":
                return new Exit();

            case "execute_script":
                try {
                    if (cmd.length == 2) {
                        File file = new File(cmd[1]);
                        if (!file.exists())
                            throw new FileNotFoundException();
                        CommandListener commandListener = new CommandListener(new FileInputStream(file), true);
                        return new Execute_script(commandListener.readAllCommands());
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Скрипт некорректен");
                }
            default:
                System.out.println("Вы ввели неверную команду. Вызвана команда help с справкой.");
                return new Help();
        }
        System.out.println("Вы ввели неверную команду. Вызвана команда help с справкой.");
        return new Help();

    }

    public Command[] readAllCommands() throws IOException {
        ArrayList<Command> commands = new ArrayList<>();
        while (input.ready()) {
            commands.add(readCommand());
        }
        return commands.toArray(new Command[0]);
    }

    private City readCity() {
        City city = new City();
        city.generateID();
        Scanner scanner = new Scanner(System.in);
        city.setName(FieldReader.readString(scanner, "Введите название Города."));
        float x = FieldReader.readX(scanner);
        long y = FieldReader.readY(scanner);
        city.setCoordinates(new Coordinates(x, y));
        city.setArea(FieldReader.readArea(scanner));
        city.setClimate(Climate.valueOf(FieldReader.readClimate(scanner)));
        city.setGovernor(new Human(FieldReader.readGovernorAge(scanner), FieldReader.readGovernorBirthday(scanner)));
        city.setMetersAboveSeaLevel(FieldReader.readMetersAboveSeaLevel(scanner));
        city.setPopulation(FieldReader.readPopulation(scanner));
        city.setAgglomeration(FieldReader.readPopulationDensity(scanner));
        city.setCreationDateTime(ZonedDateTime.now());
        city.setGovernment(Government.valueOf(FieldReader.readGovernment(scanner)));
        return city;
    }
}
