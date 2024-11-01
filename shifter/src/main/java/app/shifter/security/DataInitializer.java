package app.shifter.security;

import app.shifter.domain.Role;
import app.shifter.domain.User;
import app.shifter.repositories.RoleRepository;
import app.shifter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {  

    @Autowired
    private RoleRepository roleRepository;
    
    private final UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    
    public DataInitializer(UserRepository userRepository) {
 
        this.userRepository = userRepository;
       
    }

    @Bean
    CommandLineRunner initData() {
    return args -> {
        // Initialize roles in the database
        Role userRole = roleRepository.findByName("USER")
            .orElseGet(() -> roleRepository.save(new Role("USER")));
        Role adminRole = roleRepository.findByName("ADMIN")
            .orElseGet(() -> roleRepository.save(new Role("ADMIN")));

        // Initialize default admin user if not present
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPasswordHash(passwordEncoder.encode("admin"));
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
        }

        // Assign USER role to any existing users without roles
        userRepository.findAll().forEach(user -> {
            if (user.getRoles().isEmpty()) {
                user.setRoles(Set.of(adminRole)); // Set default role to USER if no role exists
                userRepository.save(user);
            }
        });
    };
}
}
