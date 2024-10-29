package app.shifter.repositories;


import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.shifter.domain.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Long> {

    Shift findByShiftName(String shiftName);
    boolean existsByWorkstationAndStartTimeAndEndTime(String workstation, LocalDateTime startTime, LocalDateTime endTime);
    
    @Query("SELECT COUNT(s) > 0 FROM Shift s JOIN s.employee e WHERE s.workstation = :workstation AND e.qualification = :qualification")
    boolean isQualifiedForWorkstation(@Param("workstation") String workstation, @Param("qualification") boolean qualification);

}