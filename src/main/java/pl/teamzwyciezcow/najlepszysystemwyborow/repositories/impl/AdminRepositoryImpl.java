package pl.teamzwyciezcow.najlepszysystemwyborow.repositories.impl;

import io.ebean.DB;
import io.ebean.ExpressionList;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Admin;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.AdminRepository;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class AdminRepositoryImpl implements AdminRepository {

    @Override
    public List<Admin> findAll(String fullName, String email) {
        ExpressionList<Admin> query = DB.find(Admin.class).where();
        if (fullName != null && !fullName.isEmpty()) {
            query.icontains("fullName", fullName);
        }

        if (email != null && !email.isEmpty()) {
            query.contains("email", email);
        }

        return query.findList();
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return Optional.ofNullable(DB.find(Admin.class, id));
    }

    @Override
    public Admin save(Admin user) {
        DB.save(user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        DB.delete(Admin.class, id);
    }
}
