package app.shifter.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class Break {

    @NotBlank(message= "Declare a breaktype (Kahvitauko / Ruokatauko)")
    @Column(nullable = false)
    private String breakType;


    // Muutetaan LocalDateTime käytännöllisempään muotoon
    @NotBlank (message = "Starting time of break cannot be empty")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(nullable = false)
    private LocalDateTime breakStart;

    @NotBlank (message = "End time for break cannot be empty")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(nullable = false)
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
