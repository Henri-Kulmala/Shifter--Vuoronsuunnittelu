package app.shifter.DTOs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.DayOfWeek;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;


public class WorkdayDTO {


    private Long workdayId;

    @JsonFormat(pattern = "dd-MM-yyy")
    @Column(nullable = false)
    private LocalDate date;

    private DayOfWeek dayOfWeek;
    
    @OneToMany(mappedBy = "workday", cascade = CascadeType.ALL)
    private List<ShiftDTO> shifts = new ArrayList<>();

    


    public WorkdayDTO() {}

    public WorkdayDTO(LocalDate date, Long workdayId) {
        this.date = date;
        this.workdayId = workdayId;
        this.dayOfWeek = date.getDayOfWeek();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = date.getDayOfWeek(); 
    }

    
    
    public List<ShiftDTO> getShifts() {
        return shifts;
    }
    
    public void setShifts(List<ShiftDTO> shifts) {
        this.shifts = shifts;
    }

    public Long getWorkdayId() {
        return workdayId;
    }

    public void setWorkdayId(Long workdayId) {
        this.workdayId = workdayId;
    }
    

}