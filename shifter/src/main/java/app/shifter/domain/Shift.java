package app.shifter.domain;

import java.time.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shiftid;

    @NotBlank(message = "Please declare the shift's workstation")
    @Column(nullable = false)
    private String workstation;

    @NotBlank(message = "Please declare the shift's name")
    @Column(nullable = false)
    private String shiftName;
    
    @NotBlank(message = "Please declare the shift's start time")
    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime startTime;

    @NotBlank(message = "Please declare the shift's end time")
    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime endTime;

    // Sisältää listan taukoja, joita voi hyödyntää tässä luokassa
    @ElementCollection
    private List<Break> breaks;

    // Tälle vuorolle määrätty työntekijä

    @NotBlank(message = "A shift must have it's employee stated")
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // Tämän vuoron ja työntekijän tekemät taukopaikkaukset 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "covering_shift_id")
    private Shift coveringShift;
    
    // Pohdi vielä toimintalogiikkaa lisää
    @OneToMany(mappedBy = "coveringShift", cascade = CascadeType.ALL)
    private List<Shift> coveredBreaks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workday_id")
    private Workday workday;

    
        
    public Shift() {}

    public Shift(Long shiftid, String workstation, String shiftName, LocalTime startTime, LocalTime endTime, List<Break> breaks, Employee employee, Workday workday) {
        this.shiftid = shiftid;
        this.workstation = workstation;
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breaks = breaks;
        this.employee = employee;
        this.workday = workday;
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
    
    public LocalTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    public List<Break> getBreaks() {
        return breaks;
    }
    
    public void setBreaks(List<Break> list) {
        this.breaks = list;
    }
    
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Shift getCoveringShift() {
        return coveringShift;
    }

    public void setCoveringShift(Shift coveringShift) {
        this.coveringShift = coveringShift;
    }

    public List<Shift> getCoveredBreaks() {
        return coveredBreaks;
    }

    public void setCoveredBreaks(List<Shift> coveredBreaks) {
        this.coveredBreaks = coveredBreaks;
    }

    public Workday getWorkday() {
        return workday;
    }

    public void setWorkday(Workday workday) {
        this.workday = workday;
    }

}



