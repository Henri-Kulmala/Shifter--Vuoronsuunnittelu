package app.shifter.DTOs;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class ShiftDTO {

    private Long shiftId;

    private EmployeeDTO employee; 

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
    

    public ShiftDTO() {}

    public ShiftDTO(Long shiftId, String shiftName, String workstation, LocalTime startTime, LocalTime endTime, List<BreakDTO> breaks, EmployeeDTO employee, ShiftDTO coveringShift, List<ShiftDTO> coveredBreaks) {
        this.shiftId = shiftId;
        this.shiftName = shiftName;
        this.workstation = workstation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breaks = breaks;
        this.employee = employee;
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

}
