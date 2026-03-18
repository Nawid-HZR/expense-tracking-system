package com.nawid.expense_tracking_system.application.usecase.user;

import com.nawid.expense_tracking_system.application.exception.UserNotFoundException;
import com.nawid.expense_tracking_system.domain.model.User;
import com.nawid.expense_tracking_system.domain.repository.UserRepository;
import com.nawid.expense_tracking_system.presentation.dto.response.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public class DeleteManagerUseCase {

    private final UserRepository userRepository;

    public DeleteManagerUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public ApiResponse deleteUser(long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Manager not found"));
        userRepository.deleteById(id);
        return new ApiResponse(true, "Manager deleted successfully");
    }
}
