package dev.ivanmol.taskmanager.controller;

import dev.ivanmol.taskmanager.dto.user.UserDto;
import dev.ivanmol.taskmanager.dto.user.UserRequestDto;
import dev.ivanmol.taskmanager.model.user.User;
import dev.ivanmol.taskmanager.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid UserRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));
        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        UserDto responseDto = new UserDto(user.getId(), user.getUsername());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(responseDto);
    }


}
