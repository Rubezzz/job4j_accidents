package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Optional<User> save(User user) {
        try {
            repository.save(user);
            return Optional.of(user);
        } catch (DataIntegrityViolationException e) {
            return Optional.empty();
        }
    }
}
