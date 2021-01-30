package by.epam.data.analysis.crime.service;

import by.epam.data.analysis.crime.entity.Crime;

import java.util.Collection;
import java.util.Properties;

public interface CrimeService {
    boolean saveAll(Collection<Crime> i);

    void downloadAndSave(Properties properties);
}
