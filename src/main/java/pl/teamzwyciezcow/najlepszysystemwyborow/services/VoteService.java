package pl.teamzwyciezcow.najlepszysystemwyborow.services;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Vote;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.VoteRepository;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Vote;

import java.util.List;

public interface VoteService {
    void castVotes(User user, Election election, List<Candidate> candidates) throws Exception;
    boolean hasVoted(User user, Election election);
    List<Vote> getUserVotes(User user, Election election);
    int getVoteCount(Long electionId);
    int getCandidateVoteCount(Long candidateId, Long electionId);
    VoteRepository getRepository();
}
