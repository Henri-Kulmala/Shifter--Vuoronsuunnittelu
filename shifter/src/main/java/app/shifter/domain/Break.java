package app.shifter.domain;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class Break {

    @NotBlank(message = "Declare a breaktype (Kahvitauko / Ruokatauko)")
    @Column(nullable = false)
    private String breakType;

    @NotBlank(message = "Starting time of break cannot be empty")
    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime breakStart;

    @NotBlank(message = "End time for break cannot be empty")
    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime breakEnd;

    @ManyToOne
    private Employee coverEmployee;

    public Break() {
    }

    public Break(String breakType, LocalTime breakStart, LocalTime breakEnd) {
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

    public Employee getCoverEmployee() {
        return coverEmployee;
    }

    public void setCoverEmployee(Employee coverEmployee) {
        this.coverEmployee = coverEmployee;
    }
}
