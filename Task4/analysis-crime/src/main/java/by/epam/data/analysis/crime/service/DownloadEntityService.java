package by.epam.data.analysis.crime.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public abstract class DownloadEntityService<Entity, Repository extends JpaRepository<Entity, Long>>
        implements DownloadService {
    protected static final String ARGUMENT_START_DATE = "start";
    protected static final String ARGUMENT_END_DATE = "end";
    protected static final String ARGUMENT_VERBOSE = "verbose";
    private static final String DATE_PATTERN = "yyyy-MM";
    private static final String ARGUMENT_PATH = "path";
    private static final String LOG_PARAMETER = "street";
    private static final int SLEEP_TIME = 2000;
    private static final int RECURSION_LIMIT = 50;
    protected Repository repository;
    protected RestTemplate restTemplate;
    protected CSVParser csvParser;
    protected Class<Entity> entityClass;
    protected Class<Entity[]> entityArrayClass;
    protected String urlApi;

    protected DownloadEntityService(Repository repository, RestTemplate restTemplate, CSVParser csvParser) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.csvParser = csvParser;
    }

    protected boolean saveAll(Collection<Entity> i,
                              String collectionAffiliation,
                              boolean verbose,
                              int countSaveRecursion) {
        boolean isSuccess = false;
        if (countSaveRecursion < RECURSION_LIMIT) {
            try {
                long start = System.currentTimeMillis();
                isSuccess = !repository.saveAll(i).isEmpty();
                long end = System.currentTimeMillis();
                if (verbose) {
                    log.info(entityClass.getSimpleName() + " collection with size: " + i.size() +
                            " for " + collectionAffiliation +
                            (isSuccess ? " saved in " + (end - start) + " milliseconds." : " unsaved."));
                }
            } catch (DataIntegrityViolationException e) {
                log.info("Duplicate entry for street " + collectionAffiliation + ". Try again.");
                isSuccess = saveAll(i, collectionAffiliation, verbose, ++countSaveRecursion);
            }
        } else {
            log.error("Save recursion limit reached.");
        }
        return isSuccess;
    }

    @SuppressWarnings("unchecked")
    protected List<Entity> getListEntity(Map<String, String> map,
                                         String date, Class<Entity> entityClass,
                                         int countDownloadRecursion) {
        List<Entity> list = new LinkedList<>();
        if (countDownloadRecursion < RECURSION_LIMIT) {
            map.put(UrlParameterName.URL_PARAMETER_DATE, date);
            try {
                Optional<Entity[]> optionalEntities = Optional.ofNullable(restTemplate
                        .getForEntity(urlApi, entityArrayClass, map)
                        .getBody());
                list = Arrays
                        .stream(optionalEntities.orElse((Entity[]) Array.newInstance(entityClass, 0)))
                        .collect(Collectors.toList());
            } catch (HttpClientErrorException | HttpServerErrorException e) {
                HttpStatus status = e.getStatusCode();
                log.error("Server returned HTTP response code: " + status);

                if (status == HttpStatus.BAD_REQUEST) {
                    log.error("Nothing by this arguments: date=\"" + date + "\", " + map.toString());
                } else if (status == HttpStatus.TOO_MANY_REQUESTS || status == HttpStatus.INTERNAL_SERVER_ERROR) {
                    log.error("Waiting and try again.");
                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        throw new DownloadEntityException("Downloading failed. Thread interrupt.");
                    }
                    list = getListEntity(map, date, entityClass, ++countDownloadRecursion);
                }
            }
        } else {
            log.error("Download recursion limit reached.");
        }
        return list;
    }

    protected Calendar getCalendar(Properties properties, String property) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        String date = properties.getProperty(property);
        calendar.setTime(new SimpleDateFormat(DATE_PATTERN).parse(date));
        if (calendar.compareTo(Calendar.getInstance()) >= 0) {
            throw new IncorrectArgumentException("Passed date should be less than current date.");
        }
        return calendar;
    }

    protected List<Calendar> getCalendars(Properties properties) throws ParseException {
        Calendar start = getCalendar(properties, ARGUMENT_START_DATE);
        Calendar end = getCalendar(properties, ARGUMENT_END_DATE);

        List<Calendar> calendars = new LinkedList<>();
        int additionalMonth = 0;

        while (true) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start.getTime());
            calendar.add(Calendar.MONTH, additionalMonth++);

            if (calendar.compareTo(end) > 0) {
                break;
            }
            calendars.add(calendar);
        }
        return calendars;
    }

    @Override
    public void download(Properties properties) {
        try {
            List<Calendar> calendars = getCalendars(properties);
            String path = properties.getProperty(ARGUMENT_PATH);
            boolean verbose = Boolean.parseBoolean(properties.getProperty(ARGUMENT_VERBOSE));
            List<Map<String, String>> mapsUrlParameters = csvParser.csvToUrlParameters(path);
            calendars
                    .stream()
                    .parallel()
                    .forEach(calendar ->
                            mapsUrlParameters
                                    .forEach(urlParameters -> saveAll(getListEntity(urlParameters,
                                            calendar.get(Calendar.YEAR) +
                                                    "-"
                                                    + (calendar.get(Calendar.MONTH) + 1),
                                            entityClass,
                                            0),
                                            urlParameters.get(LOG_PARAMETER),
                                            verbose,
                                            0))
                    );
        } catch (CSVParseException e) {
            throw new DownloadEntityException("Search url parameters failed.", e);
        } catch (ParseException e) {
            throw new DownloadEntityException("Parsing input arguments failed.", e);
        }
    }
}
