package pl.teamzwyciezcow.najlepszysystemwyborow.services.impl;

import io.ebean.DB;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.ResultVisibility;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.ElectionRepository;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.ElectionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election.ElectionType;

public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;

    public ElectionServiceImpl(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    @Override
    public Election createElection(String name, String description, LocalDateTime from, LocalDateTime to, ResultVisibility resultVisibility, ElectionType electionType, Integer maxChoices, List<Long> candidateIds) {
        Election election = new Election();
        election.setTitle(name);
        election.setDescription(description);
        election.setStartDate(from);
        election.setEndDate(to);
        election.setResultVisibility(resultVisibility);
        election.setElectionType(electionType);
        election.setMaxChoices(maxChoices);
        
        // Save first to get ID
        electionRepository.save(election);

        if (candidateIds != null && !candidateIds.isEmpty()) {
            List<Candidate> candidates = DB.find(Candidate.class).where().idIn(candidateIds).findList();
            for (Candidate candidate : candidates) {
                if (candidate.getElections() == null) {
                    candidate.setElections(new ArrayList<>());
                }
                candidate.getElections().add(election);
                DB.save(candidate);
            }
            // Refresh to get updated list if needed, or set manually
            election.setCandidates(candidates);
        }

        return election;
    }

    @Override
    public Election updateElection(Long electionId, String name, String description, LocalDateTime from, LocalDateTime to, ResultVisibility resultVisibility, ElectionType electionType, Integer maxChoices, List<Long> candidateIds) {
        Election election = electionRepository.findById(electionId).orElseThrow(() -> new RuntimeException("Election not found"));
        election.setTitle(name);
        election.setDescription(description);
        election.setStartDate(from);
        election.setEndDate(to);
        election.setResultVisibility(resultVisibility);
        election.setElectionType(electionType);
        election.setMaxChoices(maxChoices);
        electionRepository.save(election);

        if (candidateIds != null) { 
            // Fetch all candidates currently linked to this election
            List<Candidate> currentCandidates = election.getCandidates();
            if (currentCandidates == null) currentCandidates = new ArrayList<>();
            
            // Remove election from those not in new list
            for (Candidate c : currentCandidates) {
                if (!candidateIds.contains(c.getId())) {
                    c.getElections().removeIf(e -> e.getId() == electionId);
                    DB.save(c);
                }
            }
            
            // Add election to those in new list
            List<Candidate> newCandidates = DB.find(Candidate.class).where().idIn(candidateIds).findList();
            for (Candidate c : newCandidates) {
                boolean hasElection = false;
                if (c.getElections() != null) {
                    hasElection = c.getElections().stream().anyMatch(e -> e.getId() == electionId);
                } else {
                    c.setElections(new ArrayList<>());
                }
                
                if (!hasElection) {
                    c.getElections().add(election);
                    DB.save(c);
                }
            }
            election.setCandidates(newCandidates);
        }
        
        return election;
    }

    @Override
    public ElectionRepository getRepository() {
        return this.electionRepository;
    }
}
