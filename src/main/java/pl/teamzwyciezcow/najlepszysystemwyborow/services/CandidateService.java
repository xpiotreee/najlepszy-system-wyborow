package pl.teamzwyciezcow.najlepszysystemwyborow.services;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.CandidateRepository;

public interface CandidateService {
    Candidate createCandidate(Long electionId, String name, String description, String photoUrl, String externalLink);
    Candidate updateCandidate(Long candidateId, String name, String description, String photoUrl, String externalLink);
    CandidateRepository getRepository();
}
