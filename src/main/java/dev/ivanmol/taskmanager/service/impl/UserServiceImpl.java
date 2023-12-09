package dev.ivanmol.taskmanager.service.impl;

import dev.ivanmol.taskmanager.dto.user.UserDto;
import dev.ivanmol.taskmanager.dto.user.UserRequestDto;
import dev.ivanmol.taskmanager.mapper.UsersMapper;
import dev.ivanmol.taskmanager.model.user.User;
import dev.ivanmol.taskmanager.repository.UserRepository;
import dev.ivanmol.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserRequestDto userRequestDto) {
        User user = userRepository.saveUnique(UsersMapper.toUser(userRequestDto));
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
                    .toList();
        } else {
            return userRepository.getAllByIdInOrderByIdDesc(userIds, pageable)
                    .stream()
                    .map(UsersMapper::toUserDto)
                    .toList();
        }
    }

    @Override
    public UserDto updateUser(Long id, UserRequestDto updateDto) {
        User userFromDB = userRepository.getByIdAndCheck(id);
        User updatedUser = UsersMapper.update(userFromDB, updateDto);
        log.debug("User with id = {} was updated by new dto: = {}", id, updateDto);
        return UsersMapper.toUserDto(userRepository.save(updatedUser));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("no user found"));
    }
}