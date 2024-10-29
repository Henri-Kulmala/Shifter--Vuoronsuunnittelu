package app.shifter.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import app.shifter.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    

}