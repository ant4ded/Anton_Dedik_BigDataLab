package by.epam.data.analysis.crime.service.impl;

import by.epam.data.analysis.crime.entity.Crime;
import by.epam.data.analysis.crime.repository.CrimeRepository;
import by.epam.data.analysis.crime.service.CrimeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrimeServiceImpl implements CrimeService {
    private static final String ARGUMENT_DATE = "date";
    private static final String ARGUMENT_PATH = "path";

    private static final String URL_API =
            "https://data.police.uk/api/crimes-street/all-crime?lat={lat}&lng={lng}&date={date}";
    private static final String URL_PARAMETER_LATITUDE = "lat";
    private static final String URL_PARAMETER_LONGITUDE = "lng";
    private static final String URL_PARAMETER_DATE = "date";
    private static final String LOG_PARAMETER = "street";

    private static final String CSV_DELIMITER = ",";
    private static final int CSV_DEFAULT_FIELD_QUANTITY = 3;
    private static final int CSV_DEFAULT_STREET_INDEX = 0;
    private static final int CSV_DEFAULT_LONGITUDE_INDEX = 1;
    private static final int CSV_DEFAULT_LATITUDE_INDEX = 2;

    private static final int SLEEP_TIME = 1000;

    private final CrimeRepository repository;
    private final RestTemplate restTemplate;

    @Autowired
    public CrimeServiceImpl(CrimeRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean saveAll(Collection<Crime> i) {
        boolean isSuccess;
        log.info("Trying save Crime collection with size: " + i.size());
        isSuccess = !repository.saveAll(i).isEmpty();
        log.info("Crime collection with size: " + i.size() + (isSuccess ? " saved." : " unsaved."));
        return isSuccess;
    }

    @SneakyThrows(value = InterruptedException.class)
    private List<Crime> getListCrime(Map<String, String> map, String date) {
        map.put(URL_PARAMETER_DATE, date);
        List<Crime> list = new LinkedList<>();
        try {
            Optional<Crime[]> optionalCrimes = Optional.ofNullable(restTemplate
                    .getForEntity(URL_API, Crime[].class, map)
                    .getBody());
            list = Arrays
                    .stream(optionalCrimes.orElse(new Crime[0]))
                    .collect(Collectors.toList());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            HttpStatus status = e.getStatusCode();
            log.error("Server returned HTTP response code: " + status);

            if (status == HttpStatus.BAD_REQUEST) {
                log.error("Nothing by this arguments: date=\"" + date + "\", " + map.toString());
            } else if (status == HttpStatus.FORBIDDEN || status == HttpStatus.INTERNAL_SERVER_ERROR) {
                log.error("API call limit reached. Waiting and try again.");
                Thread.sleep(SLEEP_TIME);
                list = getListCrime(map, date);
            } else {
                log.error("Error reading from url with HTTP response code: " + status, e);
            }
        }
        return list;
    }

    @SneakyThrows
    private List<Map<String, String>> parseCsv(String filepath) {
        List<Map<String, String>> mapList = new LinkedList<>();
        if (filepath != null) {
            String row;
            BufferedReader csvReader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath))));
            while ((row = csvReader.readLine()) != null) {
                String[] splitRow = row.split(CSV_DELIMITER);
                if (splitRow.length == CSV_DEFAULT_FIELD_QUANTITY) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put(LOG_PARAMETER, splitRow[CSV_DEFAULT_STREET_INDEX]);
                    map.put(URL_PARAMETER_LATITUDE, splitRow[CSV_DEFAULT_LATITUDE_INDEX]);
                    map.put(URL_PARAMETER_LONGITUDE, splitRow[CSV_DEFAULT_LONGITUDE_INDEX]);
                    mapList.add(map);
                }
            }
            csvReader.close();
        }
        return mapList;
    }

    public void downloadAndSave(Properties properties) {
        String date = properties.getProperty(ARGUMENT_DATE);
        String path = properties.getProperty(ARGUMENT_PATH);
        List<Map<String, String>> mapsUrlParameters = parseCsv(path);
        mapsUrlParameters.forEach(urlParameters -> saveAll(getListCrime(urlParameters, date)));
    }
}
