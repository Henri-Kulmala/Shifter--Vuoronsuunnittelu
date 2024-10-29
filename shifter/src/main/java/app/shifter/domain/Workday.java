package app.shifter.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Workday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;
    
    @OneToMany(mappedBy = "workday", cascade = CascadeType.ALL)
    private List<Shift> shifts = new ArrayList<>();

    public Workday() {}

    public Workday(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public List<Shift> getShifts() {
        return shifts;
    }
    
    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

}
