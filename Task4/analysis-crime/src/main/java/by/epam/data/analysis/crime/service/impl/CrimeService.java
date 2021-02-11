package by.epam.data.analysis.crime.service.impl;

import by.epam.data.analysis.crime.entity.Crime;
import by.epam.data.analysis.crime.repository.CrimeRepository;
import by.epam.data.analysis.crime.service.CSVParser;
import by.epam.data.analysis.crime.service.DownloadEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class CrimeService extends DownloadEntityService<Crime, CrimeRepository> {
    @Autowired
    public CrimeService(CrimeRepository repository, RestTemplate restTemplate, CSVParser csvParser) {
        super(repository, restTemplate, csvParser);
    }

    @PostConstruct
    private void init() {
        urlApi = "https://data.police.uk/api/crimes-street/all-crime?lat={lat}&lng={lng}&date={date}";
        entityClass = Crime.class;
        entityArrayClass = Crime[].class;
    }
}
