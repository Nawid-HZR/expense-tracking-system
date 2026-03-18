package com.nawid.expense_tracking_system.infrastructure.bootstrap;



import com.nawid.expense_tracking_system.domain.enums.Role;
import com.nawid.expense_tracking_system.domain.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AdminInitializer implements CommandLineRunner {

    private final com.nawid.expense_tracking_system.domain.repository.UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(com.nawid.expense_tracking_system.domain.repository.UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        boolean existsAdmin = userRepository.existsByRole(Role.ADMIN);

        if (existsAdmin) {
            return;
        }

        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);

        System.out.println("Default admin created");
    }

}