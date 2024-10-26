package app.shifter.DTOs;

public class EmployeeDTO {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private Boolean qualification;
    private String notes;

    public EmployeeDTO() {}

    public EmployeeDTO(Long employeeId, String firstName, String lastName, Boolean qualification, String notes) {
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
