package by.epam.data.analysis.crime.service.impl;

import by.epam.data.analysis.crime.entity.Crime;
import by.epam.data.analysis.crime.repository.CrimeRepository;
import by.epam.data.analysis.crime.service.CrimeService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CrimeServiceImpl implements CrimeService {
    private final CrimeRepository repository;

    @Autowired
    public CrimeServiceImpl(CrimeRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean saveAll(Iterable<Crime> i) {
        boolean isSuccess = false;
        try {
            isSuccess = !repository.saveAll(i).isEmpty();
        } catch (JpaSystemException e){
            log.error("Can not save this entities in database. They already exist in database.");
        }
        return isSuccess;
    }
}
