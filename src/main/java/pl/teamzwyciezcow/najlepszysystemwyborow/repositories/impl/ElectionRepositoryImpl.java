package pl.teamzwyciezcow.najlepszysystemwyborow.repositories.impl;

import io.ebean.DB;
import io.ebean.ExpressionList;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.ElectionRepository;

import java.util.List;
import java.util.Optional;

public class ElectionRepositoryImpl implements ElectionRepository {

    @Override
    public List<Election> findAll(String title) {
        ExpressionList<Election> query = DB.find(Election.class).fetch("candidates").where();
        if (title != null && !title.isEmpty()) {
            query.icontains("title", title);
        }
        return query.findList();
    }

    @Override
    public Optional<Election> findById(Long id) {
        return Optional.ofNullable(DB.find(Election.class).fetch("candidates").where().idEq(id).findOne());
    }

    @Override
    public Election save(Election election) {
        DB.save(election);
        return election;
    }

    @Override
    public void deleteById(Long id) {
        DB.delete(Election.class, id);
    }
}
