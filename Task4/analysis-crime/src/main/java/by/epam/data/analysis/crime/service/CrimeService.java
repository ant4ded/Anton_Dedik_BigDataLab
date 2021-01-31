package by.epam.data.analysis.crime.service;

import by.epam.data.analysis.crime.entity.Crime;

import java.util.Collection;

public interface CrimeService extends RemoteEntityService {
    boolean saveAll(Collection<Crime> i, String street, boolean verbose);
}
