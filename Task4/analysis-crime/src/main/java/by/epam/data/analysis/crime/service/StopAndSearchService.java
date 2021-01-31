package by.epam.data.analysis.crime.service;

import by.epam.data.analysis.crime.entity.StopAndSearch;

import java.util.Collection;

public interface StopAndSearchService extends RemoteEntityService {
    boolean saveAll(Collection<StopAndSearch> i, String street, boolean verbose);
}
