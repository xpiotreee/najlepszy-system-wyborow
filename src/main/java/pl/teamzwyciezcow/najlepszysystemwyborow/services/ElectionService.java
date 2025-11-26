package pl.teamzwyciezcow.najlepszysystemwyborow.services;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.ResultVisibility;

import java.time.LocalDateTime;
import java.util.List;

public interface ElectionService {
    Election createElection(String name, String description, LocalDateTime from, LocalDateTime to, ResultVisibility resultVisibility, List<Long> candidateIds);
    Election updateElection(Long electionId, String name, String description, LocalDateTime from, LocalDateTime to, ResultVisibility resultVisibility, List<Long> candidateIds);
}
