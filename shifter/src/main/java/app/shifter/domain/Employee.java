package app.shifter.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    
    @NotBlank(message = "Declare the employee's first name")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Declare the employee's last name")
    @Column(nullable = false)
    private String lastName;

    @NotNull(message = "This field cannot be null (qualification => TRUE / FALSE)")
    @Column(nullable = false)
    private Boolean qualification;

    private String notes;

    public Employee() {
    }

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
