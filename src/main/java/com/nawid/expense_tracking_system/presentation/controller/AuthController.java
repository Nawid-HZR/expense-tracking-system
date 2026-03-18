package com.nawid.expense_tracking_system.presentation.controller;



import com.nawid.expense_tracking_system.application.usecase.user.*;
import com.nawid.expense_tracking_system.infrastructure.security.jwt.JwtUtil;
import com.nawid.expense_tracking_system.presentation.dto.request.LoginRequest;
import com.nawid.expense_tracking_system.presentation.dto.request.UserRequest;
import com.nawid.expense_tracking_system.presentation.dto.response.ApiResponse;
import com.nawid.expense_tracking_system.presentation.dto.response.LoginResponse;
import com.nawid.expense_tracking_system.presentation.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final GetAdminUseCase getAdminUseCase;
    private final UpdateAdminUseCase updateAdminUseCase;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, GetAdminUseCase getAdminUseCase, UpdateAdminUseCase updateAdminUseCase) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.getAdminUseCase = getAdminUseCase;
        this.updateAdminUseCase = updateAdminUseCase;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtUtil.generateToken(request.getEmail());
        LoginResponse response = new LoginResponse(token);

        return ResponseEntity.ok(response);
    }


    @PreAuthorize(("hasRole('ADMIN')"))
    @GetMapping("/admin")
    public ResponseEntity<UserResponse> getAdmin(){
        UserResponse response = getAdminUseCase.getAdmin();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize(("hasRole('ADMIN')"))
    @PutMapping("/admin")
    public ResponseEntity<ApiResponse> updateAdmin(@RequestBody UserRequest admin){
        ApiResponse response = updateAdminUseCase.updateAdmin(admin);
        return ResponseEntity.ok(response);
    }






}
