package org.example.universitytodolist.service;


import org.example.universitytodolist.DTOs.RegistrationRequestDTO;
import org.example.universitytodolist.DTOs.UserDTO;
import org.example.universitytodolist.mapper.UserMapper;
import org.example.universitytodolist.model.User;
import org.example.universitytodolist.repository.UserRepository;
import org.example.universitytodolist.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public UserDTO registerUser(RegistrationRequestDTO registrationRequestDTO) {

        User user = new User();
        user.setUsername(registrationRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequestDTO.getPassword()));
        user.setEmail(registrationRequestDTO.getEmail());
        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    public String login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(username);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
