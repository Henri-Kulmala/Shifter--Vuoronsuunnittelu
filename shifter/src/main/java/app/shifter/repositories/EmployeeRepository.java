package app.shifter.repositories;


import org.springframework.data.repository.CrudRepository;

import app.shifter.domain.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Employee findByFullName(String fullName);
    

}