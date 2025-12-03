package pl.teamzwyciezcow.najlepszysystemwyborow.repositories.impl;

import io.ebean.DB;
import io.ebean.ExpressionList;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.CandidateRepository;

import java.util.List;
import java.util.Optional;

public class CandidateRepositoryImpl implements CandidateRepository {

    @Override
    public List<Candidate> findAll(String name) {
        ExpressionList<Candidate> query = DB.find(Candidate.class).where();
        if (name != null && !name.isEmpty()) {
            query.icontains("name", name);
        }
        return query.findList();
    }

    @Override
    public Optional<Candidate> findById(Long id) {
        return Optional.ofNullable(DB.find(Candidate.class, id));
    }

    @Override
    public Candidate save(Candidate candidate) {
        DB.save(candidate);
        return candidate;
    }

    @Override
    public void deleteById(Long id) {
        DB.delete(Candidate.class, id);
    }
}
