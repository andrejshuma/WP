package mk.ukim.finki.wp2025.service.impl;

import mk.ukim.finki.wp2025.model.User;
import mk.ukim.finki.wp2025.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wp2025.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wp2025.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wp2025.repository.UserRepository;
import mk.ukim.finki.wp2025.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    // Dependency injection of UserRepository
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        // Business logic rule: username and password cannot be null or empty
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        // Find the user by username and password, or throw an exception if not found
        return this.userRepository.findByUsernameAndPassword(username, password).orElseThrow(InvalidUserCredentialsException::new);
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname) {
        // Business logic rule: none of the fields can be null or empty
        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                repeatPassword == null || repeatPassword.isEmpty() ||
                name == null || name.isEmpty() ||
                surname == null || surname.isEmpty()
        ) {
            throw new InvalidArgumentsException();
        }

        // Business logic rule: password and repeatPassword must match
        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        return this.userRepository.save(new User(username, password, name, surname));
    }
}
