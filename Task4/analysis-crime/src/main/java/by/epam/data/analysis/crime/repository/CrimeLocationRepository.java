package by.epam.data.analysis.crime.repository;

import by.epam.data.analysis.crime.entity.CrimeLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrimeLocationRepository extends JpaRepository<CrimeLocation, Long> {
}
