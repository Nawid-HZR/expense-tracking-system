package com.nawid.expense_tracking_system.presentation.controller;



import com.nawid.expense_tracking_system.application.usecase.user.CreateManagerUseCase;
import com.nawid.expense_tracking_system.application.usecase.user.CreateUserUseCase;
import com.nawid.expense_tracking_system.infrastructure.security.jwt.JwtUtil;
import com.nawid.expense_tracking_system.presentation.dto.request.LoginRequest;
import com.nawid.expense_tracking_system.presentation.dto.request.SignUpRequest;
import com.nawid.expense_tracking_system.presentation.dto.response.LoginResponse;
import com.nawid.expense_tracking_system.presentation.dto.response.SignUpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    private CreateUserUseCase createUserUseCase;
    private CreateManagerUseCase createManagerUseCase;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CreateUserUseCase createUserUseCase, CreateManagerUseCase createManagerUseCase) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.createUserUseCase = createUserUseCase;
        this.createManagerUseCase = createManagerUseCase;
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

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/admin/create-user")
    public ResponseEntity<SignUpResponse> createUser(@RequestBody SignUpRequest request){

        SignUpResponse response = createUserUseCase.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create-manager")
    public ResponseEntity<SignUpResponse> createManager(@RequestBody SignUpRequest request){

        SignUpResponse response = createManagerUseCase.createManager(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
