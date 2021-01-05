package pl.reports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.reports.dao.ReportResult;

@Repository
public interface ReportResultRepository extends JpaRepository<ReportResult, Long> {
}
