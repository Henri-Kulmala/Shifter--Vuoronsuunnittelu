package app.shifter.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    // Stores multiple breaks for this shift
    @ElementCollection
    private List<Break> breaks;

    // The employee assigned to this shift
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // This shift is covering breaks of another shift
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "covering_shift_id")
    private Shifts coveringShift;
    
    // The shifts whose breaks are covered by this shift
    @OneToMany(mappedBy = "coveringShift", cascade = CascadeType.ALL)
    private List<Shifts> coveredBreaks;
        
    public Shifts() {}

    public Shifts(Long shiftid, String workstation, String shiftName, LocalDateTime startTime, LocalDateTime endTime, List<Break> breaks, Employee employee) {
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
    
    public List<Break> getBreaks() {
        return breaks;
    }
    
    public void setBreaks(List<Break> breaks) {
        this.breaks = breaks;
    }
    
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Shifts getCoveringShift() {
        return coveringShift;
    }

    public void setCoveringShift(Shifts coveringShift) {
        this.coveringShift = coveringShift;
    }

    public List<Shifts> getCoveredBreaks() {
        return coveredBreaks;
    }

    public void setCoveredBreaks(List<Shifts> coveredBreaks) {
        this.coveredBreaks = coveredBreaks;
    }
}



