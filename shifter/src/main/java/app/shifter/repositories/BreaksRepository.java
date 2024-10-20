package app.shifter.repositories;


import org.springframework.data.jpa.repository.JpaRepository;


import app.shifter.domain.Breaks;
import java.util.List;


public interface BreaksRepository extends JpaRepository<Breaks, Long> {

    Breaks findByBreakId(Long breakId);
    List<Breaks> findByBreakType(String breakType);


}