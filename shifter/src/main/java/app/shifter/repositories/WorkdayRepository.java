package app.shifter.repositories;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import app.shifter.domain.Workday;

public interface WorkdayRepository extends JpaRepository<Workday, Long> {
    Optional<Workday> findByDate(LocalDate date);
    Optional<Workday> deleteByDate(LocalDate date);
}
