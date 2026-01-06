package pl.teamzwyciezcow.najlepszysystemwyborow.services;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Vote;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.VoteRepository;

public interface VoteService {
    void castVote(User user, Election election, Candidate candidate) throws Exception;
    boolean hasVoted(User user, Election election);
    VoteRepository getRepository();
}
