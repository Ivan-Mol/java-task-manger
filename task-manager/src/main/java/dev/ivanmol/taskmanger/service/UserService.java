package dev.ivanmol.taskmanger.service;

import dev.ivanmol.taskmanger.dto.user.UserDto;
import dev.ivanmol.taskmanger.dto.user.NewUserRequestDto;
import dev.ivanmol.taskmanger.dto.user.UpdateUserRequestDto;

import java.util.Collection;
import java.util.List;

public interface UserService {

    UserDto createUser(NewUserRequestDto user);

    Collection<UserDto> getAll(List<Long> ids, Integer from, Integer size);

    UserDto updateUser(Long id, UpdateUserRequestDto updateDto);

    void deleteUser(Long id);

    UserDto getUserById(Long id);
}