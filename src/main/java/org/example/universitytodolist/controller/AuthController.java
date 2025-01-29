package org.example.universitytodolist.controller;

import org.example.universitytodolist.DTOs.RegistrationRequestDTO;
import org.example.universitytodolist.DTOs.UserDTO;
import org.example.universitytodolist.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password) {
        String token = authService.login(username, password);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegistrationRequestDTO registrationRequestDTO) {
        UserDTO userDTO = authService.registerUser(registrationRequestDTO);
        return ResponseEntity.ok(userDTO);
    }
}
