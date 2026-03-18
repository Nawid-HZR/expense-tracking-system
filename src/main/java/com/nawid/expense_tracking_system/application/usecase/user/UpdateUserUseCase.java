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
public class UpdateUserUseCase {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public ApiResponse updateUser(String email, UserRequest request){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return new ApiResponse(true, "User updated successfully");
    }
}
