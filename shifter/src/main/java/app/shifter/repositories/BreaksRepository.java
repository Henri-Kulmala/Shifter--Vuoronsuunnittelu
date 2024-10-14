package app.shifter.repositories;


import org.springframework.data.repository.CrudRepository;

import app.shifter.domain.Breaks;

public interface BreaksRepository extends CrudRepository<Breaks, Long> {

    Breaks findByBreakId(Long breakId);

}