package com.order.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.dto.UserDTO;
import com.order.request.AuthenticationRequest;
import com.order.request.RegisterRequest;
import com.order.response.AuthenticationResponse;
import com.order.serviceImpl.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@Tag(name = "Autenticação", description = "Endpoints para autenticação e registro de usuários")
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;
    

    @PostMapping("/register")
    @Operation(
        summary = "Registrar novo usuário",
        description = "Cria um novo usuário no sistema e retorna um token JWT válido"
    )
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(request);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + response.getAccessToken())
                .header("Refresh-Token", response.getRefreshToken())
                .body(response.getUser());
    }


    @PostMapping("/authenticate")
    @Operation(
        summary = "Autenticar usuário",
        description = "Autentica o usuário com email e senha e retorna um token JWT válido"
    )
    public ResponseEntity<UserDTO> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + response.getAccessToken())
                .header("Refresh-Token", response.getRefreshToken())
                .body(response.getUser());
    }
    
}