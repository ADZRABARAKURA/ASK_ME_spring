package tech.razymov.restfull.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.razymov.restfull.dto.RegisterDto;
import tech.razymov.restfull.dto.UserDto;
import tech.razymov.restfull.entity.Role;
import tech.razymov.restfull.entity.User;
import tech.razymov.restfull.exception.UserNotFoundException;
import tech.razymov.restfull.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static tech.razymov.restfull.util.KeyGenerator.generateIdempotenceKey;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto update(Long id, UserDto userDto){
        var op = userRepository.findById(id);
        if(op.isEmpty())
            throw new UserNotFoundException(id);

        var user = op.get();
        user.setDescription(userDto.getDescription());
        user.setName(userDto.getName());
        user.setDescription(userDto.getDescription());
        user.setBlock1(userDto.getBlock1());
        user.setBlock2(userDto.getBlock2());
        user.setBlock3(userDto.getBlock3());
        user.setBlock4(userDto.getBlock4());
        user.setImg(userDto.getImg());
        return new UserDto(userRepository.save(user));
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public UserDto getById(Long id){
        var op = userRepository.findById(id);
        if(op.isEmpty())
            throw new UserNotFoundException(id);
        return new UserDto(op.get());
    }

    public List<UserDto> getAll(){
        return userRepository.findAll()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    public User findByEmail(String email){
        var optional =userRepository.findByEmail(email);
        if(optional.isEmpty())
            throw new UsernameNotFoundException(email);
        return optional.get();
    }

    public UserDto register(RegisterDto registerDto){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        var newUser = User.builder()
                .id(null)
                .name(registerDto.getName())
                .description(registerDto.getDescription())
                .block1(registerDto.getBlock1())
                .block2(registerDto.getBlock2())
                .block3(registerDto.getBlock3())
                .block4(registerDto.getBlock4())
                .role(Role.USER)
                .img(registerDto.getImgUrl())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .email(registerDto.getEmail())
                .uniqUrl(generateIdempotenceKey(32))
                .build();
        return new UserDto(userRepository.save(newUser));
    }
}
