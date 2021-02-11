package by.epam.data.analysis.crime.service.impl;

import by.epam.data.analysis.crime.entity.Force;
import by.epam.data.analysis.crime.entity.StopAndSearch;
import by.epam.data.analysis.crime.repository.StopAndSearchRepository;
import by.epam.data.analysis.crime.service.CSVParser;
import by.epam.data.analysis.crime.service.DownloadEntityException;
import by.epam.data.analysis.crime.service.DownloadEntityService;
import by.epam.data.analysis.crime.service.UrlParameterName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StopAndSearchService extends DownloadEntityService<StopAndSearch, StopAndSearchRepository> {
    private static final String URL_API_FORCES = "https://data.police.uk/api/forces";

    private List<Force> forces;

    @Autowired
    public StopAndSearchService(StopAndSearchRepository repository, RestTemplate restTemplate, CSVParser csvParser) {
        super(repository, restTemplate, csvParser);
    }

    @PostConstruct
    private void init() {
        urlApi = "https://data.police.uk/api/stops-force?force={force}&date={date}";
        entityClass = StopAndSearch.class;
        entityArrayClass = StopAndSearch[].class;

        Optional<Force[]> optionalEntities = Optional.ofNullable(restTemplate
                .getForEntity(URL_API_FORCES, Force[].class)
                .getBody());
        forces = Arrays
                .stream(optionalEntities.orElse(new Force[0]))
                .collect(Collectors.toList());
    }

    @Override
    public void download(Properties properties) {
        try {
            List<Calendar> calendars = getCalendars(properties);
            boolean verbose = Boolean.parseBoolean(properties.getProperty(ARGUMENT_VERBOSE));
            List<Map<String, String>> mapsUrlParameters = forces.stream().map(force -> {
                HashMap<String, String> map = new HashMap<>();
                map.put(UrlParameterName.URL_PARAMETER_FORCE, force.getId());
                return map;
            }).collect(Collectors.toList());
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
                                            urlParameters.get(UrlParameterName.URL_PARAMETER_FORCE),
                                            verbose,
                                            0))
                    );
        } catch (ParseException e) {
            throw new DownloadEntityException("Parsing input arguments failed.", e);
        }
    }
}
