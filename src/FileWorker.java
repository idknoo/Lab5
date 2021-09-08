import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import city.City;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FileWorker {

    private final File file;
    private final XmlMapper xmlMapper;

    public FileWorker(File file,
                      XmlMapper xmlMapper) {
        this.file = file;
        this.xmlMapper = xmlMapper;
    }

    private boolean checkAccess(File file) { return (file.canWrite() && file.canRead() && file.exists()); }

    public ArrayList<City> parse() {
        try(Scanner scanner = new Scanner(file)) {
            String xml = getXml(scanner);
            City[] values = xmlMapper.readValue(xml, City[].class);
            return new ArrayList<>(Arrays.asList(values));
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public String getXml(Scanner scanner) {
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }
        return String.join("", lines);
    }

    public void serialize(ArrayList<City> cities) throws IOException {
        City[] citiesArr = cities.toArray(new City[0]);
        System.out.println("save collection to " + file.getAbsolutePath());

        try (FileWriter fos = new FileWriter(file)) {
            xmlMapper.writeValue(fos, citiesArr);
        }
    }

    public void clear() {
        file.delete();
    }
}
