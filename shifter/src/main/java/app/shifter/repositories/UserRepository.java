package app.shifter.repositories;


import org.springframework.data.jpa.repository.JpaRepository;


import app.shifter.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);
    

}