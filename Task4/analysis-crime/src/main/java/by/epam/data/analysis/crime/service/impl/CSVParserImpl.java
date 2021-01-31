package by.epam.data.analysis.crime.service.impl;

import by.epam.data.analysis.crime.service.CSVParser;
import by.epam.data.analysis.crime.service.UrlParameterName;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class CSVParserImpl implements CSVParser {
    private static final String CSV_DELIMITER = ",";
    private static final int CSV_DEFAULT_FIELD_QUANTITY = 3;
    private static final int CSV_DEFAULT_STREET_INDEX = 0;
    private static final int CSV_DEFAULT_LONGITUDE_INDEX = 1;
    private static final int CSV_DEFAULT_LATITUDE_INDEX = 2;
    private static final String CSV_PARAMETER_STREET = "street";

    @SneakyThrows
    @Override
    public List<Map<String, String>> csvToUrlParameters(String filepath) {
        List<Map<String, String>> mapList = new LinkedList<>();
        if (filepath != null) {
            String row;
            BufferedReader csvReader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath))));
            while ((row = csvReader.readLine()) != null) {
                String[] splitRow = row.split(CSV_DELIMITER);
                if (splitRow.length == CSV_DEFAULT_FIELD_QUANTITY) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put(CSV_PARAMETER_STREET, splitRow[CSV_DEFAULT_STREET_INDEX]);
                    map.put(UrlParameterName.URL_PARAMETER_LATITUDE, splitRow[CSV_DEFAULT_LATITUDE_INDEX]);
                    map.put(UrlParameterName.URL_PARAMETER_LONGITUDE, splitRow[CSV_DEFAULT_LONGITUDE_INDEX]);
                    mapList.add(map);
                }
            }
            csvReader.close();
        }
        return mapList;
    }
}
