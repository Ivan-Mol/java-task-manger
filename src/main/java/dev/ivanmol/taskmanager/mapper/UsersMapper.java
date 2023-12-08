package dev.ivanmol.taskmanager.mapper;

import dev.ivanmol.taskmanager.dto.user.NewUserRequestDto;
import dev.ivanmol.taskmanager.dto.user.UpdateUserRequestDto;
import dev.ivanmol.taskmanager.dto.user.UserDto;
import dev.ivanmol.taskmanager.dto.user.UserShortDto;
import dev.ivanmol.taskmanager.model.user.User;

import java.util.Collection;
import java.util.Optional;

public class UsersMapper {

    public static User toUser(NewUserRequestDto request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    public static User update(User userFromDB, UpdateUserRequestDto updateDto) {
        User fromDb = userFromDB;
        UpdateUserRequestDto update = updateDto;
        Optional.ofNullable(update.getEmail()).ifPresent(fromDb::setEmail);
        Optional.ofNullable(update.getPassword()).ifPresent(fromDb::setPassword);
        return fromDb;
    }

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    public static UserShortDto toUserShortDto(User user) {
        UserShortDto userDto = new UserShortDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public static Collection<UserDto> toUserDto(Collection<User> users) {
        return users.stream()
                .map(UsersMapper::toUserDto).toList();
    }
}