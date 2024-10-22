package app.shifter.domain;

import java.time.LocalDateTime;
import jakarta.persistence.Embeddable;

@Embeddable
public class Break {
    private String breakType;
    private LocalDateTime breakStart;
    private LocalDateTime breakEnd;

    public Break() {}

    public Break(String breakType, LocalDateTime breakStart, LocalDateTime breakEnd) {
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
