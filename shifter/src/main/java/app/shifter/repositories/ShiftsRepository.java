package app.shifter.repositories;


import org.springframework.data.jpa.repository.JpaRepository;


import app.shifter.domain.Shifts;

public interface ShiftsRepository extends JpaRepository<Shifts, Long> {

    Shifts findByShiftName(String shiftName);
    

}