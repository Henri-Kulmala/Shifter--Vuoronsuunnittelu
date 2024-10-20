package app.shifter.repositories;


import org.springframework.data.jpa.repository.JpaRepository;


import app.shifter.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByFirstNameAndLastName(String firstName, String lastName);
    

}