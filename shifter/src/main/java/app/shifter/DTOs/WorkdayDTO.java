package app.shifter.DTOs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


public class WorkdayDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;
    
    @OneToMany(mappedBy = "workday", cascade = CascadeType.ALL)
    private List<ShiftDTO> shifts = new ArrayList<>();

    public WorkdayDTO() {}

    public WorkdayDTO(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public List<ShiftDTO> getShifts() {
        return shifts;
    }
    
    public void setShifts(List<ShiftDTO> shifts) {
        this.shifts = shifts;
    }

}