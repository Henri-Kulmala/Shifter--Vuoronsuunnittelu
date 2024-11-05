package app.shifter.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "Please enter an username")
    @Column(nullable = false, unique = true)
    private String username;

    // Entiteetissä salasana näkyy oikeana - DTO:ssa käytetty encoderia
    @NotBlank(message = "Please enter a password")
    @Column(nullable = false, unique = true)
    private String passwordHash;

    private String role;

    // Haetaan työntekijätiedot käyttäjän luomiseksi
    @NotBlank(message = "Please declare an employee")
    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public User() {
    }

    public User(Long userId, String role, String username, String passwordHash, Employee employee, Long employeeId) {

        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.employee = employee;

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
