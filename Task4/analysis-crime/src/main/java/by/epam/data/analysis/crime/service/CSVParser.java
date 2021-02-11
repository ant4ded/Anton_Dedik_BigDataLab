package by.epam.data.analysis.crime.service;

import java.util.List;
import java.util.Map;

public interface CSVParser {
    List<Map<String, String>> csvToUrlParameters(String filepath) throws CSVParseException;
}
