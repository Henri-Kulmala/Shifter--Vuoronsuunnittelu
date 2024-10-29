package app.shifter.DTOs;

import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDTO {

    private Long userId;

    @NotBlank(message = "Please enter an username")
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull(message = "This field cannot be null (admin => TRUE / FALSE)")
    @Column(nullable = false)
    private Boolean admin;

    @NotBlank(message = "Please declare an employee")
    @OneToOne
    private EmployeeDTO employee;

    @NotNull(message = "Please enter a password")
    @Column(nullable = false, unique = true)
    private String password;

    public UserDTO() {}

    public UserDTO(Long userId, String username, Boolean admin, EmployeeDTO employee) {
        this.userId = userId;
        this.username = username;
        this.admin = admin;
        this.employee = employee;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userid) {
        this.userId = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAdmin() {
        return admin;
    }
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    
    public EmployeeDTO getEmployee() {
        return employee;
    }
    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
}
