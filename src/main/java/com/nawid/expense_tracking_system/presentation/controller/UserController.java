package com.nawid.expense_tracking_system.presentation.controller;


import com.nawid.expense_tracking_system.application.usecase.user.CreateUserUseCase;
import com.nawid.expense_tracking_system.application.usecase.user.DeleteUserUseCase;
import com.nawid.expense_tracking_system.application.usecase.user.GetUserUseCase;
import com.nawid.expense_tracking_system.application.usecase.user.UpdateUserUseCase;
import com.nawid.expense_tracking_system.presentation.dto.request.UserRequest;
import com.nawid.expense_tracking_system.presentation.dto.response.ApiResponse;
import com.nawid.expense_tracking_system.presentation.dto.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, GetUserUseCase getUserUseCase, DeleteUserUseCase deleteUserUseCase, UpdateUserUseCase updateUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserRequest request){

        ApiResponse response = createUserUseCase.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }




    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<UserResponse> responses = isAdmin
                ? getUserUseCase.getAllUsers()
                : getUserUseCase.getAllUsersForManager();

        return ResponseEntity.ok(responses);
    }



    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable long id){
        ApiResponse response = deleteUserUseCase.deleteUser(id);
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserProfile(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserResponse response = getUserUseCase.getUser(email);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/me")
    public ResponseEntity<ApiResponse> updateUserProfile(@RequestBody UserRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ApiResponse response = updateUserUseCase.updateUser(email, request);
        return ResponseEntity.ok(response);
    }
}
