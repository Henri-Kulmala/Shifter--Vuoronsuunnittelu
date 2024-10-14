package app.shifter.repositories;


import org.springframework.data.repository.CrudRepository;

import app.shifter.domain.Shifts;

public interface ShiftsRepository extends CrudRepository<Shifts, Long> {

    Shifts findByShiftName(String shiftName);
    

}