package app.shifter.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Workday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Transient 
    private DayOfWeek dayOfWeek;

    @OneToMany(mappedBy = "workday", cascade = CascadeType.ALL)
    private List<Shift> shifts = new ArrayList<>();

    public Workday() {}

    public Workday(LocalDate date) {
        this.date = date;
        this.dayOfWeek = date.getDayOfWeek(); 
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = date.getDayOfWeek(); 
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }


}
