package pl.teamzwyciezcow.najlepszysystemwyborow.repositories.impl;

import io.ebean.DB;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Vote;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.VoteRepository;

import java.util.List;

public class VoteRepositoryImpl implements VoteRepository {

    @Override
    public Vote save(Vote vote) {
        DB.save(vote);
        return vote;
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
}
