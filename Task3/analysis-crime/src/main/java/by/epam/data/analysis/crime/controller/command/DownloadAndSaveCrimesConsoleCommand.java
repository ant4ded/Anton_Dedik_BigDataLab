package by.epam.data.analysis.crime.controller.command;

import by.epam.data.analysis.crime.entity.Crime;
import by.epam.data.analysis.crime.service.CrimeService;
import by.epam.data.analysis.crime.service.JsonConverter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DownloadAndSaveCrimesConsoleCommand implements ConsoleCommand {
    private static final String DEFAULT_FILE_PATH = "classpath:LondonStations.csv";
    private static final String ARGUMENT_STREET = "street";
    private static final String ARGUMENT_DATE = "date";
    private static final String ARGUMENT_PATH = "path";
    private static final String URL_PART1 = "https://data.police.uk/api/crimes-street/all-crime?lat=";
    private static final String URL_PART2 = "&lng=";
    private static final String URL_PART3 = "&date=";

    private final CrimeService crimeService;
    private final JsonConverter jsonConverter;

    private String latitude;
    private String longitude;
    private String date;

    @Autowired
    public DownloadAndSaveCrimesConsoleCommand(CrimeService crimeService, JsonConverter jsonConverter) {
        this.crimeService = crimeService;
        this.jsonConverter = jsonConverter;
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private List<JSONObject> getListJSONObject() {
        List<JSONObject> list = new LinkedList<>();
        try {
            InputStream is = new URL(URL_PART1 + latitude + URL_PART2 + longitude + URL_PART3 + date).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String[] strings = readAll(rd).replace("[", "")
                    .replace("]", "").split("\\},\\{");

            list.add(new JSONObject(strings[0] + "}"));
            for (int i = 1; i < strings.length - 1; i++) {
                list.add(new JSONObject("{" + strings[i] + "}"));
            }
            list.add(new JSONObject("{" + strings[strings.length - 1]));

        } catch (FileNotFoundException e) {
            log.error("Nothing by this arguments");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @SneakyThrows
    private void findCoordinates(String street, File file) {
        BufferedReader csvReader;
        String row;
        try {
            csvReader = new BufferedReader(new FileReader(file));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                if (data[0].equalsIgnoreCase(street)) {
                    longitude = data[1];
                    latitude = data[2];
                }
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            log.error("Can not find file by filepath argument. Search street in default file.");
            findCoordinates(street, ResourceUtils.getFile(DEFAULT_FILE_PATH));
        }
        if (longitude == null || latitude == null) {
            if (file.getAbsolutePath().equals(ResourceUtils.getFile(DEFAULT_FILE_PATH).getAbsolutePath())) {
                throw new CommandRuntimeException("Can not find street in default file.");
            }
            log.error("Can not find street in file by filepath argument. Search street in default file");
            findCoordinates(street, ResourceUtils.getFile(DEFAULT_FILE_PATH));
        }
    }

    @Override
    public void execute(Properties properties) {
        date = properties.getProperty(ARGUMENT_DATE);
        String street = properties.getProperty(ARGUMENT_STREET);
        String path = properties.getProperty(ARGUMENT_PATH);
        findCoordinates(street, new File(path == null ? "" : path));
        List<Crime> list = getListJSONObject().stream().map(jsonConverter::jsonToCrime).collect(Collectors.toList());
        crimeService.saveAll(list);
    }
}
