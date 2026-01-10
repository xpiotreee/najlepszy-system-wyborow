package pl.teamzwyciezcow.najlepszysystemwyborow.services.impl;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.UserRepository;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.UserService;
import pl.teamzwyciezcow.najlepszysystemwyborow.utils.SecurityUtils;

import java.util.NoSuchElementException;

public class UserServiceImpl implements UserService {
    private User loggedIn;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String fullName, String email, String password, String pesel) {
        String hashedPassword = SecurityUtils.hashPassword(password);
        User user = new User(fullName, pesel, email, hashedPassword);
        this.userRepository.save(user);

        return user;
    }

    @Override
    public User updateUser(Long userId, String fullName, String email, String password, String pesel) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFullName(fullName);
        user.setEmail(email);
        if (password != null && !password.isEmpty()) {
            user.setPassword(SecurityUtils.hashPassword(password));
        }
        user.setPesel(pesel);
        userRepository.save(user);
        
        if (this.loggedIn != null && this.loggedIn.getId() == userId) {
            this.loggedIn = user;
        }
        
        return user;
    }

    @Override
    public User register(String fullName, String email, String password, String pesel) throws Exception {
        boolean emailTaken = !this.userRepository.findAll(null, email).isEmpty();
        if (emailTaken) {
            throw new Exception(email + " już jest zajęty.");
        }

        this.loggedIn = this.createUser(fullName, email, password, pesel);
        return this.loggedIn;
    }

    @Override
    public User login(String email, String password) throws Exception {
        User user;
        try {
            user = this.userRepository.findAll(null, email).getFirst();
        } catch (NoSuchElementException e) {
            throw new Exception("Nie znaleziono użytkownika z emailem: " + email);
        }

        if (!SecurityUtils.verifyPassword(password, user.getPassword())) {
            throw new Exception("Niepoprawne dane logowania.");
        }

        this.loggedIn = user;
        return user;
    }

    @Override
    public void logout() {
        this.loggedIn = null;
    }

    @Override
    public User getLoggedIn() {
        return this.loggedIn;
    }

    @Override
    public UserRepository getRepository() {
        return this.userRepository;
    }
}
