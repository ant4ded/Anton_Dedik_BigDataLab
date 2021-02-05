package by.epam.data.analysis.crime.repository;

import by.epam.data.analysis.crime.entity.Crime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrimeRepository extends JpaRepository<Crime, Long> {
}
