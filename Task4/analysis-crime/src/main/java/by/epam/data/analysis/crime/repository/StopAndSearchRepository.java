package by.epam.data.analysis.crime.repository;

import by.epam.data.analysis.crime.entity.StopAndSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopAndSearchRepository extends JpaRepository<StopAndSearch, Long> {
}
