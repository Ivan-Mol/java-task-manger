package dev.ivanmol.taskmanager.service.impl;

import dev.ivanmol.taskmanager.dto.user.NewUserRequestDto;
import dev.ivanmol.taskmanager.dto.user.UpdateUserRequestDto;
import dev.ivanmol.taskmanager.dto.user.UserDto;
import dev.ivanmol.taskmanager.mapper.UsersMapper;
import dev.ivanmol.taskmanager.model.user.User;
import dev.ivanmol.taskmanager.repository.UserRepository;
import dev.ivanmol.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(NewUserRequestDto newUserRequestDto) {
        User user = userRepository.saveUnique(UsersMapper.toUser(newUserRequestDto));
        log.debug("User with id = {}, was created", user.getId());
        return UsersMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers(List<Long> userIds, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        if (userIds == null || userIds.isEmpty()) {
            return userRepository.findAll(pageable)
                    .stream()
                    .map(UsersMapper::toUserDto)
                    .collect(Collectors.toList());
        } else {
            return userRepository.getAllByIdInOrderByIdDesc(userIds, pageable)
                    .stream()
                    .map(UsersMapper::toUserDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserRequestDto updateDto) {
        User userFromDB = userRepository.getByIdAndCheck(id);
        User updatedUser = UsersMapper.update(userFromDB, updateDto);
        log.debug("User with id = {} was updated by new dto: = {}", id, updateDto);
        return UsersMapper.toUserDto(userRepository.saveUnique(updatedUser));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.debug("User with id = {}, was deleted", id);
    }

    @Override
    public UserDto getUserById(Long id) {
        return UsersMapper.toUserDto(userRepository.getByIdAndCheck(id));
    }
}