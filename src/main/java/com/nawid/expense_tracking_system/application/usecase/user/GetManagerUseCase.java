package com.nawid.expense_tracking_system.application.usecase.user;

import com.nawid.expense_tracking_system.application.exception.UserNotFoundException;
import com.nawid.expense_tracking_system.domain.enums.Role;
import com.nawid.expense_tracking_system.domain.model.User;
import com.nawid.expense_tracking_system.domain.repository.UserRepository;
import com.nawid.expense_tracking_system.presentation.dto.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetManagerUseCase {


    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public GetManagerUseCase(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }


    public List<UserResponse> getAllManagers() {
        List<User> users = userRepository.findAll();

        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == Role.MANAGER)
                .map(user -> mapper.map(user, UserResponse.class))
                .toList();
    }

    public UserResponse getManager(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Profile not exists"));

        return mapper.map(user, UserResponse.class);
    }




}
