package by.epam.data.analysis.crime.service.impl;

import by.epam.data.analysis.crime.entity.Crime;
import by.epam.data.analysis.crime.repository.CrimeRepository;
import by.epam.data.analysis.crime.service.CSVParser;
import by.epam.data.analysis.crime.service.CrimeService;
import by.epam.data.analysis.crime.service.UrlParameterName;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrimeServiceImpl implements CrimeService {
    private static final String ARGUMENT_DATE = "date";
    private static final String ARGUMENT_PATH = "path";
    private static final String ARGUMENT_VERBOSE = "verbose";
    private static final String URL_API =
            "https://data.police.uk/api/crimes-street/all-crime?lat={lat}&lng={lng}&date={date}";
    private static final String LOG_PARAMETER = "street";
    private static final int SLEEP_TIME = 1000;

    private final CrimeRepository repository;
    private final RestTemplate restTemplate;
    private final CSVParser csvParser;

    @Autowired
    public CrimeServiceImpl(CrimeRepository repository, RestTemplate restTemplate, CSVParser csvParser) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.csvParser = csvParser;
    }

    public boolean saveAll(Collection<Crime> i, String street, boolean verbose) {
        boolean isSuccess;
        try {
            long start = System.currentTimeMillis();
            isSuccess = !repository.saveAll(i).isEmpty();
            long end = System.currentTimeMillis();
            if (verbose) {
                log.info("Crime collection with size: " + i.size() +
                        " for street: " + street +
                        (isSuccess ? " saved in " + (end - start) + " milliseconds." : " unsaved."));
            }
        } catch (DataIntegrityViolationException e) {
            log.info("Duplicate entry for street " + street + ". Try again.");
            isSuccess = saveAll(i, street, verbose);
        }
        return isSuccess;
    }

    @SneakyThrows(value = InterruptedException.class)
    private List<Crime> getListCrime(Map<String, String> map, String date) {
        map.put(UrlParameterName.URL_PARAMETER_DATE, date);
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

    public void downloadAndSave(Properties properties) {
        String date = properties.getProperty(ARGUMENT_DATE);
        String path = properties.getProperty(ARGUMENT_PATH);
        boolean verbose = Boolean.parseBoolean(properties.getProperty(ARGUMENT_VERBOSE));
        List<Map<String, String>> mapsUrlParameters = csvParser.csvToUrlParameters(path);
        mapsUrlParameters
                .stream()
                .parallel()
                .forEach(urlParameters -> saveAll(getListCrime(urlParameters, date),
                        urlParameters.get(LOG_PARAMETER),
                        verbose));
    }
}
