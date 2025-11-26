package pl.teamzwyciezcow.najlepszysystemwyborow.services;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;

public interface CandidateService {
    Candidate createCandidate(Long electionId, String name, String description, String photoUrl, String externalLink);
    Candidate updateCandidate(Long candidateId, String name, String description, String photoUrl, String externalLink);
}
