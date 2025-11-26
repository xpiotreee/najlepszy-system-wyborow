package pl.teamzwyciezcow.najlepszysystemwyborow.services;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;

public interface UserService {
    User createUser(String fullName, String email, String password, String pesel);
    User register(String fullName, String email, String password, String pesel) throws Exception;
    User login(String email, String password) throws Exception;
    void logout();
}
