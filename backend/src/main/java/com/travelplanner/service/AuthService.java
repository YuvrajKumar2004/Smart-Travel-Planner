package com.travelplanner.service;

import com.travelplanner.dto.auth.AuthResponse;
import com.travelplanner.dto.auth.LoginRequest;
import com.travelplanner.dto.auth.SignupRequest;
import com.travelplanner.exception.BadRequestException;
import com.travelplanner.exception.UnauthorizedException;
import com.travelplanner.model.User;
import com.travelplanner.repository.UserRepository;
import com.travelplanner.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }

        User user = User.builder()
            .fullName(request.getFullName().trim())
            .email(request.getEmail().trim().toLowerCase())
            .build();
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());

        return AuthResponse.builder()
            .userId(savedUser.getId())
            .fullName(savedUser.getFullName())
            .email(savedUser.getEmail())
            .token(jwtUtil.generateToken(userDetails))
            .tokenType("Bearer")
            .build();
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new UnauthorizedException("Invalid email or password");
        }

        User user = userRepository.findByEmailIgnoreCase(request.getEmail())
            .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        return AuthResponse.builder()
            .userId(user.getId())
            .fullName(user.getFullName())
            .email(user.getEmail())
            .token(jwtUtil.generateToken(userDetails))
            .tokenType("Bearer")
            .build();
    }
}
