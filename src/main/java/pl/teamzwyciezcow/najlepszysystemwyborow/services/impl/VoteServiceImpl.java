package pl.teamzwyciezcow.najlepszysystemwyborow.services.impl;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Vote;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Vote;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.VoteRepository;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.VoteService;

import java.util.List;

public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public void castVotes(User user, Election election, List<Candidate> candidates) throws Exception {
        if (hasVoted(user, election)) {
            throw new Exception("Użytkownik już zagłosował w tych wyborach.");
        }
        
        if (candidates == null || candidates.isEmpty()) {
            throw new Exception("Nie wybrano żadnego kandydata.");
        }

        if (election.getElectionType() == Election.ElectionType.SINGLE_CHOICE && candidates.size() > 1) {
            throw new Exception("W wyborach jednokrotnego wyboru można oddać tylko jeden głos.");
        }
        
        if (election.getElectionType() == Election.ElectionType.MULTIPLE_CHOICE) {
            if (election.getMaxChoices() != null && candidates.size() > election.getMaxChoices()) {
                throw new Exception("Możesz zagłosować na maksymalnie " + election.getMaxChoices() + " kandydatów.");
            }
        }

        // Validate candidates belong to election
        for (Candidate c : candidates) {
             boolean isValid = c.getElections() != null && 
                               c.getElections().stream().anyMatch(e -> e.getId() == election.getId());
             if (!isValid) {
                 throw new Exception("Kandydat " + c.getName() + " nie bierze udziału w tych wyborach.");
             }
        }

        for (Candidate candidate : candidates) {
            Vote vote = new Vote();
            vote.setUser(user);
            vote.setElection(election);
            vote.setCandidate(candidate);
            voteRepository.save(vote);
        }
    }

    @Override
    public boolean hasVoted(User user, Election election) {
        if (user == null || election == null) return false;
        return voteRepository.hasUserVoted(user.getId(), election.getId());
    }

    @Override
    public List<Vote> getUserVotes(User user, Election election) {
        if (user == null || election == null) return List.of();
        return voteRepository.findUserVotes(user.getId(), election.getId());
    }

    @Override
    public int getVoteCount(Long electionId) {
        return voteRepository.countByElectionId(electionId);
    }

    @Override
    public int getCandidateVoteCount(Long candidateId, Long electionId) {
        return voteRepository.countByCandidateId(candidateId, electionId);
    }

    @Override
    public VoteRepository getRepository() {
        return voteRepository;
    }
}
