package app.shifter.repositories;


import org.springframework.data.repository.CrudRepository;

import app.shifter.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserName(String userName);
    

}