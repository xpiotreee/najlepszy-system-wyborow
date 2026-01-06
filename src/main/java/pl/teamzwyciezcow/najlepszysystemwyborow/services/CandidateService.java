package pl.teamzwyciezcow.najlepszysystemwyborow.services;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.CandidateRepository;

import java.util.List;

public interface CandidateService {
    Candidate createCandidate(List<Long> electionIds, String name, String description, String photoUrl, String externalLink);
    Candidate updateCandidate(Long candidateId, List<Long> electionIds, String name, String description, String photoUrl, String externalLink);
    CandidateRepository getRepository();
}
