package dev.ivanmol.taskmanager.service;

import dev.ivanmol.taskmanager.dto.user.NewUserRequestDto;
import dev.ivanmol.taskmanager.dto.user.UpdateUserRequestDto;
import dev.ivanmol.taskmanager.dto.user.UserDto;

import java.util.Collection;
import java.util.List;

public interface UserService {

    UserDto createUser(NewUserRequestDto user);

    Collection<UserDto> getAllUsers(List<Long> ids, Integer from, Integer size);

    UserDto updateUser(Long id, UpdateUserRequestDto updateDto);

    void deleteUser(Long id);

    UserDto getUserById(Long id);
}