package service;

import models.Candidate;
import repository.VotingApplicationRepository;

import java.util.List;

public class VotingApplication {
    private final VotingApplicationRepository repository = new VotingApplicationRepository();

    public List<Candidate> getAllCandidates() {
        return repository.getCandidates();
    }
}
