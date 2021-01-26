package by.epam.data.analysis.crime.service;

import by.epam.data.analysis.crime.entity.Crime;

public interface CrimeService {
    boolean saveAll(Iterable<Crime> i);
}
