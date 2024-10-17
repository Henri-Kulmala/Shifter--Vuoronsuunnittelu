package app.shifter.repositories;


import org.springframework.data.jpa.repository.JpaRepository;


import app.shifter.domain.Breaks;


public interface BreaksRepository extends JpaRepository<Breaks, Long> {

    Breaks findByBreakId(Long breakId);


}