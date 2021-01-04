package pl.reports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.reports.dao.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
}
