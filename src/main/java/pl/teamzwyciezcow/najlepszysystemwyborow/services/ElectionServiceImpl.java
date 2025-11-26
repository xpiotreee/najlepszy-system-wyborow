package pl.teamzwyciezcow.najlepszysystemwyborow.services;

import io.ebean.DB;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.ResultVisibility;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.ElectionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;

    public ElectionServiceImpl(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    @Override
    public Election createElection(String name, String description, LocalDateTime from, LocalDateTime to, ResultVisibility resultVisibility, List<Long> candidateIds) {
        Election election = new Election();
        election.setTitle(name);
        election.setDescription(description);
        election.setStartDate(from);
        election.setEndDate(to);
        election.setResultVisibility(resultVisibility);

        List<Candidate> candidates = candidateIds.stream().map(id -> {
            Candidate candidate = DB.find(Candidate.class, id);
            if (candidate == null) {
                throw new RuntimeException("Candidate not found with id: " + id);
            }
            candidate.setElection(election);
            return candidate;
        }).collect(Collectors.toList());
        election.setCandidates(candidates);

        return electionRepository.save(election);
    }

    @Override
    public Election updateElection(Long electionId, String name, String description, LocalDateTime from, LocalDateTime to, ResultVisibility resultVisibility, List<Long> candidateIds) {
        Election election = electionRepository.findById(electionId).orElseThrow(() -> new RuntimeException("Election not found"));
        election.setTitle(name);
        election.setDescription(description);
        election.setStartDate(from);
        election.setEndDate(to);
        election.setResultVisibility(resultVisibility);

        List<Candidate> candidates = candidateIds.stream().map(id -> {
            Candidate candidate = DB.find(Candidate.class, id);
            if (candidate == null) {
                throw new RuntimeException("Candidate not found with id: " + id);
            }
            candidate.setElection(election);
            return candidate;
        }).collect(Collectors.toList());
        election.setCandidates(candidates);
        
        return electionRepository.save(election);
    }
}
