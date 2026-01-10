package pl.teamzwyciezcow.najlepszysystemwyborow.repositories.impl;

import io.ebean.DB;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Vote;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.VoteRepository;

import java.util.List;
import java.util.Optional;

public class VoteRepositoryImpl implements VoteRepository {

    @Override
    public Vote save(Vote vote) {
        DB.save(vote);
        return vote;
    }

    @Override
    public List<Vote> findAll() {
        return DB.find(Vote.class).fetch("user").fetch("election").fetch("candidate").findList();
    }

    @Override
    public Optional<Vote> findById(Long id) {
        return Optional.ofNullable(DB.find(Vote.class, id));
    }

    @Override
    public void deleteById(Long id) {
        DB.delete(Vote.class, id);
    }

    @Override
    public List<Vote> findByElectionId(Long electionId) {
        return DB.find(Vote.class).where().eq("election.id", electionId).findList();
    }

    @Override
    public boolean hasUserVoted(Long userId, Long electionId) {
        return DB.find(Vote.class)
                .where()
                .eq("user.id", userId)
                .eq("election.id", electionId)
                .exists();
    }

    @Override
    public List<Vote> findUserVotes(Long userId, Long electionId) {
        return DB.find(Vote.class)
                .where()
                .eq("user.id", userId)
                .eq("election.id", electionId)
                .findList();
    }

    @Override
    public List<Vote> findByUserId(Long userId) {
        return DB.find(Vote.class)
                .fetch("election")
                .fetch("candidate")
                .where()
                .eq("user.id", userId)
                .findList();
    }

    @Override
    public int countByElectionId(Long electionId) {
        return DB.find(Vote.class).where().eq("election.id", electionId).findCount();
    }

    @Override
    public int countByCandidateId(Long candidateId, Long electionId) {
        return DB.find(Vote.class)
                .where()
                .eq("candidate.id", candidateId)
                .eq("election.id", electionId)
                .findCount();
    }
}
