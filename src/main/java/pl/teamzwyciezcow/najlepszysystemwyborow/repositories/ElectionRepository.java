package pl.teamzwyciezcow.najlepszysystemwyborow.repositories;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.ResultVisibility;
import java.util.List;
import java.util.Optional;

public interface ElectionRepository {
    List<Election> findAll(String title);
    Optional<Election> findById(Long id);
    Election save(Election election);
    void deleteById(Long id);
}
