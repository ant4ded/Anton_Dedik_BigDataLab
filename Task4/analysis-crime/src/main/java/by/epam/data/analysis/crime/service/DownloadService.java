package by.epam.data.analysis.crime.service;

import java.util.Properties;

public interface DownloadService {
    void download(Properties properties) throws DownloadEntityException;
}
