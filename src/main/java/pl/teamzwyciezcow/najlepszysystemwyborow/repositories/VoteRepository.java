package pl.teamzwyciezcow.najlepszysystemwyborow.repositories;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Vote;

import java.util.List;

public interface VoteRepository {
    Vote save(Vote vote);
    List<Vote> findByElectionId(Long electionId);
    boolean hasUserVoted(Long userId, Long electionId);
    List<Vote> findUserVotes(Long userId, Long electionId);
    int countByElectionId(Long electionId);
    int countByCandidateId(Long candidateId, Long electionId);
}
