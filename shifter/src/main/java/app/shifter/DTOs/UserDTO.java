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
    
    private Set<String> roles;

    @NotBlank(message = "Please declare an employee")
    @OneToOne
    private EmployeeDTO employee;

    @NotNull(message = "Please enter a password")
    @Column(nullable = false, unique = true)
    private String password;

    public UserDTO() {}

    public UserDTO(Long userId, String username,Set<String> roles, EmployeeDTO employee) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
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
