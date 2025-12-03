package pl.teamzwyciezcow.najlepszysystemwyborow.repositories;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminRepository {
    List<Admin> findAll(String fullName, String email);
    Optional<Admin> findById(Long id);
    Admin save(Admin user);
    void deleteById(Long id);
}
