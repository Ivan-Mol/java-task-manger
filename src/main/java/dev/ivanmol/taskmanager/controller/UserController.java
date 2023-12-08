package dev.ivanmol.taskmanager.controller;

import dev.ivanmol.taskmanager.dto.user.NewUserRequestDto;
import dev.ivanmol.taskmanager.dto.user.UpdateUserRequestDto;
import dev.ivanmol.taskmanager.dto.user.UserDto;
import dev.ivanmol.taskmanager.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Slf4j
@Hidden
@Tag(name = "Secret User Controller", description = "Used for tests in Postman")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid NewUserRequestDto userDto) throws ValidationException {
        log.info("POST/createUser with dto " + userDto);
        return userService.createUser(userDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable Long id) {
        log.info("GET/getUserById with id " + id);
        return userService.getUserById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDto> getAllUsers(@RequestParam(required = false) List<Long> ids,
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET/getAllUsers with ids " + ids + " from: " + from + " size: " + size);
        return userService.getAllUsers(ids, from, size);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        log.info("DELETE/deleteUser with id " + id);
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDto updateUserById(@PathVariable Long id,
                                  @RequestBody @Validated UpdateUserRequestDto updateDto) {
        log.info("PATCH/updateUser with id " + id);
        return userService.updateUser(id, updateDto);
    }
}