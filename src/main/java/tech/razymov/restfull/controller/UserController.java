package tech.razymov.restfull.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.razymov.restfull.dto.RegisterDto;
import tech.razymov.restfull.dto.UserDto;
import tech.razymov.restfull.entity.User;
import tech.razymov.restfull.exception.UserNotFoundException;
import tech.razymov.restfull.service.UserService;

import java.util.List;

import static tech.razymov.restfull.util.KeyGenerator.generateIdempotenceKey;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController { //По-хорошему, нужно вынести логику в сервис, а не делать её на уровне домена

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAll(){
        return userService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDto userDto){
        return userService.update(id, userDto);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody RegisterDto registerDto){
        return userService.register(registerDto);
    }

}