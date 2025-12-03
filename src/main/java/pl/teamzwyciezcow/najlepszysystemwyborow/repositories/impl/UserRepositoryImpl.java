package pl.teamzwyciezcow.najlepszysystemwyborow.repositories.impl;

import io.ebean.DB;
import io.ebean.ExpressionList;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.ElectionRepository;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public List<User> findAll(String fullName, String email) {
        ExpressionList<User> query = DB.find(User.class).where();
        if (fullName != null && !fullName.isEmpty()) {
            query.icontains("fullName", fullName);
        }

        if (email != null && !email.isEmpty()) {
            query.contains("email", email);
        }

        return query.findList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(DB.find(User.class, id));
    }

    @Override
    public User save(User user) {
        DB.save(user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        DB.delete(User.class, id);
    }
}
