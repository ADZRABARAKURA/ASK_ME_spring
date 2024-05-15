package tech.razymov.restfull.controller;

import org.springframework.web.bind.annotation.*;
import tech.razymov.restfull.entity.User;
import tech.razymov.restfull.exception.UserNotFoundException;
import tech.razymov.restfull.repository.UserRepository;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    public List<User> all() {
        return repository.findAll();
    }

    @PostMapping("/users")
    public User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    public User one(@PathVariable int id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException((long) id));
    }

    @PutMapping("/users/{id}")
    public User replaceUser(@RequestBody User newUser, @PathVariable int id) {
        return repository.findById( id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setDescription(newUser.getDescription());
                    return repository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException((long) id));
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }
}