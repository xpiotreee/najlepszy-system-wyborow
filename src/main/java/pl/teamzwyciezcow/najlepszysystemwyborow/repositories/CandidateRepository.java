package pl.teamzwyciezcow.najlepszysystemwyborow.repositories;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import java.util.List;
import java.util.Optional;

public interface CandidateRepository {
    List<Candidate> findAll(String name);
    Optional<Candidate> findById(Long id);
    Candidate save(Candidate candidate);
    void deleteById(Long id);
}
