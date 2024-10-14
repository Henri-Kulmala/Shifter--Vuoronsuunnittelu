package app.shifter.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "breaks")
public class Breaks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long breakId;

    @Column(name = "break_type", nullable = false)
    private String breakType;

    @Column(name = "break_start", nullable = false)
    private LocalDateTime breakStart;

    @Column(name = "break_end", nullable = false)
    private LocalDateTime breakEnd;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shifts shift;

    public Breaks() {}

    public Breaks(Long breakId, String breakType, LocalDateTime breakStart, LocalDateTime breakEnd) {

        this.breakId = breakId;
        this.breakType = breakType;
        this.breakStart = breakStart;
        this.breakEnd = breakEnd;
    }

    public Long getBreakId() {
        return breakId;
    }
    
    public void setBreakId(Long breakId) {
        this.breakId = breakId;
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
    
    public Shifts getShift() {
        return shift;
    }
    
    public void setShift(Shifts shift) {
        this.shift = shift;
    }


    
}