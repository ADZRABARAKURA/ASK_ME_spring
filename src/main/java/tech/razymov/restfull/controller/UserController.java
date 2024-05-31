package tech.razymov.restfull.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationObservationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tech.razymov.restfull.dto.DonationDto;
import tech.razymov.restfull.dto.RegisterDto;
import tech.razymov.restfull.dto.UserDto;
import tech.razymov.restfull.entity.User;
import tech.razymov.restfull.exception.UserNotFoundException;
import tech.razymov.restfull.service.UserService;
import tech.razymov.restfull.service.auth.AskMeUserPrincipal;

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

    @GetMapping("/current")
    public UserDto getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal()!="anonymousUser") {
            String currentUserName = authentication.getName();
            return new UserDto(userService.findByEmail(currentUserName));
        }
        return null;
    }

    @GetMapping("/donations/{id}")
    public List<DonationDto> getUserDonations(@PathVariable Long id){
        return userService.userDonates(id);
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