package app.shifter.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    // Valuation

    private String firstName;
    private String lastName;
    private Boolean qualification;
    private String notes;


    public Employee() {}

    public Employee(String firstName, String lastName, Boolean qualification, String notes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.qualification = qualification;
        this.notes = notes;
    }

    public Long getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
   
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public Boolean getQualification() {
        return qualification;
    }

    public void setQualification(Boolean qualification) {
        this.qualification = qualification;
    }


}
