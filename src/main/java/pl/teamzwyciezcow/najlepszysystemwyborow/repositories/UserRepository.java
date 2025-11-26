package pl.teamzwyciezcow.najlepszysystemwyborow.repositories;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll(String fullName, String email);
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
}
