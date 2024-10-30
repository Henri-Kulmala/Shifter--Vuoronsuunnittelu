package app.shifter.DTOs;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import app.shifter.DTOs.EmployeeDTO;

public class ShiftDTO {

    private Long shiftId;

    @NotBlank(message = "Please declare the shift's name")
    @Column(nullable = false)
    private String shiftName;

    @NotBlank(message = "Please declare the shift's workstation")
    @Column(nullable = false)
    private String workstation;
    
    @NotBlank(message = "Please declare the shift's start time")
    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime startTime;

    @NotBlank(message = "Please declare the shift's end time")
    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime endTime;

    private List<BreakDTO> breaks;  
    private EmployeeDTO employee;   
    private ShiftDTO coveringShift;  
    private List<ShiftDTO> coveredBreaks;  

    public ShiftDTO() {}

    public ShiftDTO(Long shiftId, String shiftName, String workstation, LocalTime startTime, LocalTime endTime, List<BreakDTO> breaks, EmployeeDTO employee, ShiftDTO coveringShift, List<ShiftDTO> coveredBreaks) {
        this.shiftId = shiftId;
        this.shiftName = shiftName;
        this.workstation = workstation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breaks = breaks;
        this.employee = employee;
        this.coveringShift = coveringShift;
        this.coveredBreaks = coveredBreaks;
    }

    
    public Long getShiftId() {
        return shiftId;
    }
    
    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getWorkstation() {
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
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

    public List<BreakDTO> getBreaks() {
        return breaks;
    }

    public void setBreaks(List<BreakDTO> breaks) {
        this.breaks = breaks;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public ShiftDTO getCoveringShift() {
        return coveringShift;
    }

    public void setCoveringShift(ShiftDTO coveringShift) {
        this.coveringShift = coveringShift;
    }

    public List<ShiftDTO> getCoveredBreaks() {
        return coveredBreaks;
    }

    public void setCoveredBreaks(List<ShiftDTO> coveredBreaks) {
        this.coveredBreaks = coveredBreaks;
    }
}
