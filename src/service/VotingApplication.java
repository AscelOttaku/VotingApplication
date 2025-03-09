package service;

import com.sun.net.httpserver.HttpExchange;
import cookie.Cookie;
import models.Candidate;
import models.Voter;
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

    public boolean voteForCandidate(Voter voter, long id) {
        try {
            var candidate = findCandidateById(id);

            candidate.setVotesQuantity(candidate.getVotesQuantity() + 1);
            voter.setVoted(true);
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
        JsonMapper.writeToFile(repository.getVoters(), "data/json/voters.json");
    }

    public List<Candidate> getAllCandidates() {
        return repository.getCandidates();
    }

    public Voter findVoterByCookie(String cookieIdentity) {
        var voter = repository.findVoterByCookieIdentityValue(cookieIdentity);

        if (voter.isPresent())
            return voter.get();

        throw new NoSuchElementException("Candidate with id " + cookieIdentity + " not found");
    }

    public boolean checkForVoterByEmailAndPassword(String email, String password) {
        var employees = repository.getVoters();

        return employees.stream()
                .anyMatch(employee -> employee.getEmail().equalsIgnoreCase(email)
                        && employee.getPassword().equalsIgnoreCase(password));
    }

    public boolean setVoterCookieIdentity(String email) {
        var optionalEmployee = repository.findVoterByEmail(email);

        if (optionalEmployee.isPresent()) {
            optionalEmployee.get().setCookieIdentity(getUniqueUUID());
            return true;
        }
        return false;
    }

    private String getUniqueUUID() {
        return UUID.randomUUID().toString();
    }

    public Optional<Voter> findVoterByEmail(String email) {
        return repository.findVoterByEmail(email);
    }


    public boolean checkIfVoterExist(String email) {
        return repository.findVoterByEmail(email).isPresent();
    }

    public Voter registerNewVoter(Map<String, String> parsed) {
        String name = removeSpaceInText(parsed.get("name"));
        String surname = removeSpaceInText(parsed.get("surname"));
        String middleName = removeSpaceInText(parsed.get("middleName"));
        String email = removeSpaceInText(parsed.get("email"));
        String password = removeSpaceInText(parsed.get("password"));

        Voter employee = new Voter.Builder()
                .firstName(name)
                .middleName(middleName)
                .lastName(surname)
                .email(email)
                .password(password)
                .build();

        repository.addVoter(employee);
        saveToRepositoryChanges();

        return employee;
    }

    private String removeSpaceInText(String input) {
        if (input == null || input.isBlank())
            return input;

        return input.replace("\r", "");
    }

    public boolean checkIfVoterAuthorized(HttpExchange exchange) {
        String cookie = Util.getCookieValue(exchange);

        if (cookie == null || cookie.isBlank())
            return false;

        Map<String, String> cookieValues = Cookie.parse(cookie);

        String employeeIdentity = "voterIdentity";
        String cookieValue = cookieValues.get(employeeIdentity);
        return isVoterAuthorized(cookieValue);
    }

    private boolean isVoterAuthorized(String cookieValue) {
        var allVoters = repository.getVoters();

        return allVoters.stream()
                .anyMatch(voter -> voter.getUniqueCookieIdentity().equals(cookieValue));
    }
}
