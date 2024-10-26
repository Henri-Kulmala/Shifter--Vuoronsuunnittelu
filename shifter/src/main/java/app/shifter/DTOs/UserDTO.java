package app.shifter.DTOs;



public class UserDTO {

    private Long userId;
    private String username;
    private Boolean admin;
    private EmployeeDTO employee;
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
