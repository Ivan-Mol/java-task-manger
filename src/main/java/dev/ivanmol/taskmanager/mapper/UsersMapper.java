package dev.ivanmol.taskmanager.mapper;

import dev.ivanmol.taskmanager.dto.user.UserDto;
import dev.ivanmol.taskmanager.dto.user.UserRequestDto;
import dev.ivanmol.taskmanager.model.user.User;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Optional;

@UtilityClass
public class UsersMapper {

    public static User toUser(UserRequestDto request) {
        User user = new User();
        user.setUsername(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    public static User update(User userFromDB, UserRequestDto updateDto) {
        Optional.ofNullable(updateDto.getEmail()).ifPresent(userFromDB::setUsername);
        Optional.ofNullable(updateDto.getPassword()).ifPresent(userFromDB::setPassword);
        return userFromDB;
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getUsername());
    }

    public static Collection<UserDto> toUserDto(Collection<User> users) {
        return users.stream()
                .map(UsersMapper::toUserDto).toList();
    }
}