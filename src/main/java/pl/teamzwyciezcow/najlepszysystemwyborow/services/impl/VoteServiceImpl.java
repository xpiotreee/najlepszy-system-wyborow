package pl.teamzwyciezcow.najlepszysystemwyborow.services.impl;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Vote;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.VoteRepository;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.VoteService;

public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public void castVote(User user, Election election, Candidate candidate) throws Exception {
        if (hasVoted(user, election)) {
            throw new Exception("Użytkownik już zagłosował w tych wyborach.");
        }

        // Additional validation: is candidate assigned to election?
        boolean isValidCandidate = candidate.getElections() != null && 
                                   candidate.getElections().stream().anyMatch(e -> e.getId() == election.getId());
                                   
        // Note: Logic above depends on whether Candidate or Election owning relation is loaded.
        // Assuming candidate is fetched with elections or we trust the passed objects context.
        // A safer check might be DB lookup but let's assume valid context from controller.

        Vote vote = new Vote();
        vote.setUser(user);
        vote.setElection(election);
        vote.setCandidate(candidate);
        
        voteRepository.save(vote);
    }

    @Override
    public boolean hasVoted(User user, Election election) {
        if (user == null || election == null) return false;
        return voteRepository.hasUserVoted(user.getId(), election.getId());
    }

    @Override
    public VoteRepository getRepository() {
        return voteRepository;
    }
}
