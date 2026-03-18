package com.nawid.expense_tracking_system.application.usecase.user;

import com.nawid.expense_tracking_system.application.exception.UserNotFoundException;
import com.nawid.expense_tracking_system.domain.enums.Role;
import com.nawid.expense_tracking_system.domain.model.User;
import com.nawid.expense_tracking_system.domain.repository.UserRepository;
import com.nawid.expense_tracking_system.presentation.dto.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetAdminUseCase {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public GetAdminUseCase(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public UserResponse getAdmin(){
        User admin = userRepository.findByRole(Role.ADMIN)
                .orElseThrow(() -> new UserNotFoundException("Admin not found"));
        return mapper.map(admin, UserResponse.class);
    }

}
