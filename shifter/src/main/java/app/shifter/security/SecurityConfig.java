package app.shifter.security;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/login", "/resources/**").permitAll()
                        .requestMatchers("/api/**").hasAnyAuthority("ADMIN", "USER") 
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/index", true)
                )
                .httpBasic(Customizer.withDefaults()); // Enable HTTP Basic authentication

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
