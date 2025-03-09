package service;

import models.Candidate;
import repository.VotingApplicationRepository;
import utils.Util;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VotingApplication {
    private final VotingApplicationRepository repository = new VotingApplicationRepository();

    public Map<Candidate, Integer> getCandidatesAndVotes() {
        var allCandidatesWithVotes = getCandidateAndVotes();
        long getVotesQuantity = getTotalVotesQuantity();

        return allCandidatesWithVotes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, candidateLongEntry ->
                        Util.calculateVoteProccentInAllVotes(candidateLongEntry.getValue(), getVotesQuantity)));
    }

    private Map<Candidate, Long> getCandidateAndVotes() {
        var allCandidates = repository.getCandidates();

        return allCandidates.stream()
                .collect(Collectors.toMap(Function.identity(), Candidate::getVotesQuantity));
    }

    private long getTotalVotesQuantity() {
        return repository.getCandidates().stream()
                .map(Candidate::getVotesQuantity)
                .reduce(0L, Long::sum);
    }

    public Candidate findCandidateById(long id) {
        if (id <= 0 || id > repository.getCandidates().size())
            throw new InputMismatchException("Id must be between 1 and " + (repository.getCandidates().size() - 1));

        var optionalCandidate = repository.findCandidateById(id);

        if (optionalCandidate.isPresent())
            return optionalCandidate.get();

        throw new NoSuchElementException("Candidate with id " + id + " not found");
    }

    public boolean voteForCandidate(long id) {
        try {
            var candidate = findCandidateById(id);

            candidate.setVotesQuantity(candidate.getVotesQuantity() + 1);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public Integer getVoteQuantityInProccent(Candidate candidate) {
        return Util.calculateVoteProccentInAllVotes(candidate.getVotesQuantity(), getTotalVotesQuantity());
    }

    public List<Candidate> getAllCandidates() {
        return repository.getCandidates();
    }
}
