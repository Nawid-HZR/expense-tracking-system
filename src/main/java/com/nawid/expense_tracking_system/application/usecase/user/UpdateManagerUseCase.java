package com.nawid.expense_tracking_system.application.usecase.user;


import com.nawid.expense_tracking_system.application.exception.UserNotFoundException;
import com.nawid.expense_tracking_system.domain.enums.Role;
import com.nawid.expense_tracking_system.domain.model.User;
import com.nawid.expense_tracking_system.domain.repository.UserRepository;
import com.nawid.expense_tracking_system.presentation.dto.request.UserRequest;
import com.nawid.expense_tracking_system.presentation.dto.response.ApiResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdateManagerUseCase {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UpdateManagerUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public ApiResponse updateManager(String email, UserRequest request){

        User manager = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        manager.setEmail(request.getEmail());
        manager.setName(request.getName());
        manager.setRole(Role.MANAGER);
        manager.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(manager);

        return new ApiResponse(true, "Manager updated successfully");
    }
}
