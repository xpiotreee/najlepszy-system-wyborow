package pl.teamzwyciezcow.najlepszysystemwyborow.services.impl;

import io.ebean.DB;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.CandidateRepository;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.CandidateService;

public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Candidate createCandidate(Long electionId, String name, String description, String photoUrl, String externalLink) {
        Election election = DB.find(Election.class, electionId);
        if (election == null) {
            throw new RuntimeException("Election not found with id: " + electionId);
        }

        Candidate candidate = new Candidate();
        candidate.setElection(election);
        candidate.setName(name);
        candidate.setDescription(description);
        candidate.setPhotoUrl(photoUrl);
        candidate.setExternalLink(externalLink);
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate updateCandidate(Long candidateId, String name, String description, String photoUrl, String externalLink) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found with id: " + candidateId));

        candidate.setName(name);
        candidate.setDescription(description);
        candidate.setPhotoUrl(photoUrl);
        candidate.setExternalLink(externalLink);
        return candidateRepository.save(candidate);
    }

    @Override
    public CandidateRepository getRepository() {
        return this.candidateRepository;
    }
}
