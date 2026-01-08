package pl.teamzwyciezcow.najlepszysystemwyborow.services;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.ResultVisibility;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.ElectionRepository;

import java.time.LocalDateTime;
import java.util.List;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election.ElectionType;

public interface ElectionService {
    Election createElection(String name, String description, LocalDateTime from, LocalDateTime to, ResultVisibility resultVisibility, ElectionType electionType, Integer maxChoices, List<Long> candidateIds);
    Election updateElection(Long electionId, String name, String description, LocalDateTime from, LocalDateTime to, ResultVisibility resultVisibility, ElectionType electionType, Integer maxChoices, List<Long> candidateIds);
    ElectionRepository getRepository();
}
