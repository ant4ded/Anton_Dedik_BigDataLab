package by.epam.data.analysis.crime.service.impl;

import by.epam.data.analysis.crime.entity.Crime;
import by.epam.data.analysis.crime.repository.CrimeRepository;
import by.epam.data.analysis.crime.service.CrimeService;
import by.epam.data.analysis.crime.service.JsonConverter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class CrimeServiceImpl implements CrimeService {
    private static final String ARGUMENT_DATE = "date";
    private static final String ARGUMENT_PATH = "path";

    private static final String URL_API = "https://data.police.uk/api/crimes-street/all-crime?";
    private static final String URL_PARAMETER_LATITUDE = "lat=";
    private static final String URL_PARAMETER_LONGITUDE = "&lng=";
    private static final String URL_PARAMETER_DATE = "&date=";

    private static final String CSV_DELIMITER = ",";
    private static final int CSV_DEFAULT_FIELD_QUANTITY = 3;
    private static final int CSV_DEFAULT_STREET_INDEX = 0;
    private static final int CSV_DEFAULT_LONGITUDE_INDEX = 1;
    private static final int CSV_DEFAULT_LATITUDE_INDEX = 2;

    private static final int SLEEP_TIME = 1000;

    private final CrimeRepository repository;
    private final JsonConverter jsonConverter;

    @Autowired
    public CrimeServiceImpl(CrimeRepository repository, JsonConverter jsonConverter) {
        this.repository = repository;
        this.jsonConverter = jsonConverter;
    }

    @Override
    public boolean saveAll(Collection<Crime> i) {
        boolean isSuccess;
        log.info("Trying save Crime collection with size: " + i.size());
        isSuccess = !repository.saveAll(i).isEmpty();
        log.info("Crime collection with size: " + i.size() + (isSuccess ? " saved." : " unsaved."));
        return isSuccess;
    }

    private String readAll(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int characterCode;
        while ((characterCode = reader.read()) != -1) {
            stringBuilder.append((char) characterCode);
        }
        return stringBuilder.toString();
    }

    private List<JSONObject> getListJSONObject(UrlLocation urlLocation, String date) {
        URLConnection urlConnection = null;
        List<JSONObject> list = new LinkedList<>();
        try {
            urlConnection = new URL(URL_API +
                    URL_PARAMETER_LATITUDE + urlLocation.getLatitude() +
                    URL_PARAMETER_LONGITUDE + urlLocation.getLongitude() +
                    URL_PARAMETER_DATE + date)
                    .openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            JSONArray jsonArray = new JSONArray(readAll(rd));
            list = StreamSupport.stream(jsonArray.spliterator(), true)
                    .map(val -> (JSONObject) val)
                    .collect(Collectors.toList());
//            jsonArray.forEach(jsonObject -> list.add((JSONObject) jsonObject));
        } catch (IOException e) {
            try {
                if (urlConnection != null) {
                    int responseCode = ((HttpURLConnection) urlConnection).getResponseCode();
                    log.error("Server returned HTTP response code: " + responseCode);

                    if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                        log.error("Nothing by this arguments: date=\"" + date + "\", " + urlLocation.toString());
                    } else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                        log.error("API call limit reached. Waiting and try again.");
                        Thread.sleep(SLEEP_TIME);
                    } else {
                        log.error("Error reading from url.", e);
                    }
                }
            } catch (IOException ioException) {
                log.error("Error with get error code.", ioException);
            } catch (InterruptedException interruptedException) {
                log.error("Thread interrupted.", interruptedException);
                Thread.currentThread().interrupt();
            }
        }
        if (!list.isEmpty()) {
            log.info("Received json list for street: \"" + urlLocation.getStreet() + "\" by date: \"" + date + "\"");
        }
        return list;
    }

    private List<UrlLocation> findCoordinates(String filepath) {
        List<UrlLocation> urlLocations = new LinkedList<>();
        if (filepath != null) {
            try {
                String row;
                BufferedReader csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath))));
                while ((row = csvReader.readLine()) != null) {
                    UrlLocation urlLocation = new UrlLocation();
                    String[] splitRow = row.split(CSV_DELIMITER);
                    if (splitRow.length == CSV_DEFAULT_FIELD_QUANTITY) {
                        urlLocation.setStreet(splitRow[CSV_DEFAULT_STREET_INDEX]);
                        urlLocation.setLongitude(splitRow[CSV_DEFAULT_LONGITUDE_INDEX]);
                        urlLocation.setLatitude(splitRow[CSV_DEFAULT_LATITUDE_INDEX]);
                        urlLocations.add(urlLocation);
                    }
                }
                csvReader.close();
            } catch (FileNotFoundException e) {
                log.error("Can not find file by path: \"" + filepath + "\" .", e);
            } catch (IOException e) {
                log.error("Error closing file reader.", e);
            }
        }
        return urlLocations;
    }

    public void downloadAndSave(Properties properties) {
        String date = properties.getProperty(ARGUMENT_DATE);
        String path = properties.getProperty(ARGUMENT_PATH);
        List<UrlLocation> urlLocations = findCoordinates(path);
        for (UrlLocation urlLocation : urlLocations) {
            List<Crime> list = getListJSONObject(urlLocation, date)
                    .stream()
                    .parallel()
                    .map(jsonConverter::jsonToCrime)
                    .collect(Collectors.toList());
            saveAll(list);
        }
    }

    @Data
    private class UrlLocation {
        private String street;
        private String latitude;
        private String longitude;
    }
}
