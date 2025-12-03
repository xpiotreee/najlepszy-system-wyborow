package pl.teamzwyciezcow.najlepszysystemwyborow.services;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Admin;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.AdminRepository;

public interface AdminService {
    Admin createUser(String fullName, String email, String password);
    Admin login(String email, String password) throws Exception;
    void logout();
    Admin getLoggedIn();
    AdminRepository getRepository();
}
