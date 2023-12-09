package dev.ivanmol.taskmanager.service;

import dev.ivanmol.taskmanager.dto.user.UserDto;
import dev.ivanmol.taskmanager.dto.user.UserRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserRequestDto user);

    Collection<UserDto> getAllUsers(List<Long> ids, Integer from, Integer size);

    UserDto updateUser(Long id, UserRequestDto updateDto);

    void deleteUser(Long id);

    UserDto getUserById(Long id);
}