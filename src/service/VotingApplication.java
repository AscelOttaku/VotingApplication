package service;

import models.Candidate;
import repository.VotingApplicationRepository;
import utils.JsonMapper;
import utils.Util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VotingApplication {
    private final VotingApplicationRepository repository = new VotingApplicationRepository();

    public Map<Candidate, Integer> getCandidatesAndVotes() {
        var allCandidatesWithVotes = getCandidateAndVotes();
        long getVotesQuantity = getTotalVotesQuantity();

        var candidatesAndVotes = allCandidatesWithVotes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, candidateLongEntry ->
                        Util.calculateVoteProccentInAllVotes(candidateLongEntry.getValue(), getVotesQuantity)));

        return sortCandidatesInReversed(candidatesAndVotes);
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
            saveToRepositoryChanges();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public Map<Candidate, Integer> sortCandidatesInReversed(Map<Candidate, Integer> candidatesAndVotes) {
        return new TreeMap<>(candidatesAndVotes).reversed();
    }

    public Integer getVoteQuantityInProccent(Candidate candidate) {
        return Util.calculateVoteProccentInAllVotes(candidate.getVotesQuantity(), getTotalVotesQuantity());
    }

    private void saveToRepositoryChanges() {
        JsonMapper.writeToFile(repository.getCandidates(), "data/json/candidates.json");
    }

    public List<Candidate> getAllCandidates() {
        return repository.getCandidates();
    }
}
