package pl.teamzwyciezcow.najlepszysystemwyborow.repositories;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Vote;

import java.util.List;
import java.util.Optional;

public interface VoteRepository {
    Vote save(Vote vote);
    List<Vote> findAll();
    Optional<Vote> findById(Long id);
    void deleteById(Long id);
    List<Vote> findByElectionId(Long electionId);
    boolean hasUserVoted(Long userId, Long electionId);
    List<Vote> findUserVotes(Long userId, Long electionId);
    List<Vote> findByUserId(Long userId);
    int countByElectionId(Long electionId);
    int countByCandidateId(Long candidateId, Long electionId);
}
