package com.nawid.expense_tracking_system.presentation.controller;

import com.nawid.expense_tracking_system.application.usecase.user.CreateManagerUseCase;
import com.nawid.expense_tracking_system.application.usecase.user.DeleteManagerUseCase;
import com.nawid.expense_tracking_system.application.usecase.user.GetManagerUseCase;
import com.nawid.expense_tracking_system.application.usecase.user.UpdateManagerUseCase;
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
@RequestMapping("/admin/manager")
public class ManagerController {

    private final CreateManagerUseCase createManagerUseCase;
    private final GetManagerUseCase getManagerUseCase;
    private final DeleteManagerUseCase deleteManagerUseCase;
    private final UpdateManagerUseCase updateManagerUseCase;

    public ManagerController(CreateManagerUseCase createManagerUseCase, GetManagerUseCase getManagerUseCase, DeleteManagerUseCase deleteManagerUseCase, UpdateManagerUseCase updateManagerUseCase) {
        this.createManagerUseCase = createManagerUseCase;
        this.getManagerUseCase = getManagerUseCase;
        this.deleteManagerUseCase = deleteManagerUseCase;
        this.updateManagerUseCase = updateManagerUseCase;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> createManager(@RequestBody UserRequest request){

        ApiResponse response = createManagerUseCase.createManager(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAllManagers(){
        List<UserResponse> responses = getManagerUseCase.getAllManagers();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteManager(@PathVariable long id){
        ApiResponse response = deleteManagerUseCase.deleteUser(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getManagerProfile(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserResponse response = getManagerUseCase.getManager(email);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/me")
    public ResponseEntity<ApiResponse> updateManagerProfile(@RequestBody UserRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ApiResponse response = updateManagerUseCase.updateManager(email, request);
        return ResponseEntity.ok(response);
    }

}
