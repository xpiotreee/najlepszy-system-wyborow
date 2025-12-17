package pl.teamzwyciezcow.najlepszysystemwyborow.services.impl;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Admin;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.AdminRepository;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.UserRepository;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.AdminService;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.UserService;

import java.util.NoSuchElementException;

public class AdminServiceImpl implements AdminService {
    private Admin loggedIn;
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin createUser(String fullName, String email, String password) {
        Admin admin = new Admin(fullName, email, password);
        this.adminRepository.save(admin);

        return admin;
    }

    @Override
    public Admin login(String email, String password) throws Exception {
        Admin admin;
        try {
            admin = this.adminRepository.findAll(null, email).getFirst();
        } catch (NoSuchElementException e) {
            throw new Exception("Nie znaleziono admina z emailem: " + email);
        }

        
        if (!admin.getPassword().equals(password)) {
            throw new Exception("Niepoprawne dane logowania.");
        }

        this.loggedIn = admin;
        return admin;
    }

    @Override
    public void logout() {
        this.loggedIn = null;
    }

    @Override
    public Admin getLoggedIn() {
        return this.loggedIn;
    }

    @Override
    public AdminRepository getRepository() {
        return this.adminRepository;
    }
}
