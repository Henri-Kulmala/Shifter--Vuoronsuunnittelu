package app.shifter.DTOs;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BreakDTO {

    private String breakType;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime breakStart;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime breakEnd;

    public BreakDTO() {}

    public BreakDTO(String breakType, LocalDateTime breakStart, LocalDateTime breakEnd) {
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

    public LocalDateTime getBreakStart() {
        return breakStart;
    }

    public void setBreakStart(LocalDateTime breakStart) {
        this.breakStart = breakStart;
    }

    public LocalDateTime getBreakEnd() {
        return breakEnd;
    }

    public void setBreakEnd(LocalDateTime breakEnd) {
        this.breakEnd = breakEnd;
    }
}
