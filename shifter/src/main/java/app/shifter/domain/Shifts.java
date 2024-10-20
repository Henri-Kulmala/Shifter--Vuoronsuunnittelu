package app.shifter.domain;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Shifts {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shiftid;

    private String workstation;
    private String shiftName;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime endTime;


    @OneToMany(mappedBy = "shift", cascade = CascadeType.ALL)
    private List<Breaks> breaks;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    

    public Shifts() {}

    public Shifts(Long shiftid, String workstation, String shiftName, LocalDateTime startTime, LocalDateTime endTime, List<Breaks> breaks, Employee employee) {

        this.shiftid = shiftid;
        this.workstation = workstation;
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breaks = breaks;
        this.employee = employee;
    }

    public Long getShiftId() {
        return shiftid;
    }

    public void setShiftId(Long shiftid) {
        this.shiftid = shiftid;
    }
    
    public String getWorkstation() {
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }

    public String getShiftName() {
        return shiftName;
    }
    
    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public List<Breaks> getBreaks() {
        return breaks;
    }
    
    public void setBreaks(List<Breaks> breaks) {
        this.breaks = breaks;
    }
    
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }




}
