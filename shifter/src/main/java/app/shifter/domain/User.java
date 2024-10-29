package app.shifter.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @NotBlank(message = "Please enter an username")
    @Column(nullable = false, unique = true)
    private String username;

    // Entiteetissä salasana näkyy oikeana - DTO:ssa käytetty encoderia
    @NotBlank(message = "Please enter a password")
    @Column(nullable = false, unique = true)
    private String passwordHash;

    @NotNull(message = "This field cannot be null (admin => TRUE / FALSE)")
    @Column(nullable = false)
    private Boolean admin;


    // Haetaan työntekijätiedot käyttäjän luomiseksi
    @NotBlank(message = "Please declare an employee")
    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;


    public User() {}

    public User(Long userid, String username, String passwordHash, Boolean admin, Employee employee, Long employeeId) {

        this.userid = userid;
        this.username = username;
        this.passwordHash = passwordHash;
        this.admin = admin;
        this.employee = employee;
    }

    public Long getUserId() {
        return userid;
    }
    public void setUserId(Long userid) {
        this.userid = userid;
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
    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }



}
