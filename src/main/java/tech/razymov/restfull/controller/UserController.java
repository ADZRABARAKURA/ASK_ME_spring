package tech.razymov.restfull.controller;

import org.springframework.web.bind.annotation.*;
import tech.razymov.restfull.entity.User;
import tech.razymov.restfull.exception.UserNotFoundException;
import tech.razymov.restfull.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<User> all() {
        return repository.findAll();
    }

    @PostMapping
    public User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/{id}")
    public User one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException((long) id));
    }

    @PutMapping("/{id}")
    public User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return repository.findById( id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setDescription(newUser.getDescription());
                    user.setBlock1(newUser.getBlock1());
                    user.setBlock2(newUser.getBlock2());
                    user.setBlock3(newUser.getBlock3());
                    user.setBlock4(newUser.getBlock4());
                    user.setImg(newUser.getImg());
                    return repository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException((long) id));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}