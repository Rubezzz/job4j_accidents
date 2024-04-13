package ru.job4j.accidents.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @NonNull
    private final UserRepository repository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public Optional<User> save(User user) {
        try {
            repository.save(user);
            return Optional.of(user);
        } catch (DataIntegrityViolationException e) {
            logger.info(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
