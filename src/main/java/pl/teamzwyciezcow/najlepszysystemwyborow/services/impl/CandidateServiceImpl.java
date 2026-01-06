package pl.teamzwyciezcow.najlepszysystemwyborow.services.impl;

import io.ebean.DB;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.CandidateRepository;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.CandidateService;

import java.util.ArrayList;
import java.util.List;

public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Candidate createCandidate(List<Long> electionIds, String name, String description, String photoUrl, String externalLink) {
        Candidate candidate = new Candidate();
        candidate.setName(name);
        candidate.setDescription(description);
        candidate.setPhotoUrl(photoUrl);
        candidate.setExternalLink(externalLink);
        
        if (electionIds != null && !electionIds.isEmpty()) {
            List<Election> elections = DB.find(Election.class).where().idIn(electionIds).findList();
            candidate.setElections(elections);
        } else {
            candidate.setElections(new ArrayList<>());
        }

        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate updateCandidate(Long candidateId, List<Long> electionIds, String name, String description, String photoUrl, String externalLink) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found with id: " + candidateId));

        candidate.setName(name);
        candidate.setDescription(description);
        candidate.setPhotoUrl(photoUrl);
        candidate.setExternalLink(externalLink);
        
        if (electionIds != null) {
            List<Election> elections = DB.find(Election.class).where().idIn(electionIds).findList();
            candidate.setElections(elections);
        }

        return candidateRepository.save(candidate);
    }

    @Override
    public CandidateRepository getRepository() {
        return this.candidateRepository;
    }
}
