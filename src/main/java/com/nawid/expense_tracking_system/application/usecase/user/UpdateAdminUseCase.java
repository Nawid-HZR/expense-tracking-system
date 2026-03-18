package com.nawid.expense_tracking_system.application.usecase.user;

import com.nawid.expense_tracking_system.domain.enums.Role;
import com.nawid.expense_tracking_system.domain.model.User;
import com.nawid.expense_tracking_system.domain.repository.UserRepository;
import com.nawid.expense_tracking_system.presentation.dto.request.UserRequest;
import com.nawid.expense_tracking_system.presentation.dto.response.ApiResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UpdateAdminUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UpdateAdminUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApiResponse updateAdmin(UserRequest user){

        User admin = userRepository.findByRole(Role.ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        admin.setEmail(user.getEmail());
        admin.setName(user.getName());
        admin.setRole(Role.ADMIN);
        admin.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(admin);

        return new ApiResponse(true, "Admin updated successfully");
    }
}
