package app.shifter.DTOs;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDTO {

    private Long userId;

    @NotBlank(message = "Please enter an username")
    @Column(nullable = false, unique = true)
    private String username;
    
    private String role;

    @NotBlank(message = "Please declare an employee")
    @OneToOne
    private EmployeeDTO employee;

    @NotNull(message = "Please enter a password")
    @Column(nullable = false, unique = true)
    private String password;

    public UserDTO() {}

    public UserDTO(Long userId, String username,String role, EmployeeDTO employee) {
        this.userId = userId;
        this.username = username;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
