package app.shifter.DTOs;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;

public class BreakDTO {

    @NotBlank(message= "Declare a breaktype (Kahvitauko / Ruokatauko)")
    private String breakType;

    @NotBlank(message = "Starting time of break cannot be empty")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime breakStart;

    @NotBlank(message = "End time for break cannot be empty")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime breakEnd;

    private String breakCoverEmployee;

    public BreakDTO() {}

    public BreakDTO(String breakType, LocalTime breakStart, LocalTime breakEnd) {
        this.breakType = breakType;
        this.breakStart = breakStart;
        this.breakEnd = breakEnd;
    }

    public String getBreakType() {
        return breakType;
    }

    public void setBreakType(String breakType) {
        this.breakType = breakType;
    }

    public LocalTime getBreakStart() {
        return breakStart;
    }

    public void setBreakStart(LocalTime breakStart) {
        this.breakStart = breakStart;
    }

    public LocalTime getBreakEnd() {
        return breakEnd;
    }

    public void setBreakEnd(LocalTime breakEnd) {
        this.breakEnd = breakEnd;
    }

    public String getBreakCoverEmployee() {
        return breakCoverEmployee;
    }

    public void setBreakCoverEmployee(String breakCoverEmployee) {
        this.breakCoverEmployee = breakCoverEmployee;
    }
}
