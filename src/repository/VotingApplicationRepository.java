package repository;

import com.google.gson.reflect.TypeToken;
import models.Candidate;
import models.Voter;
import utils.JsonMapper;
import utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VotingApplicationRepository {
    private List<Candidate> candidates = new ArrayList<>();
    private List<Voter> voters = new ArrayList<>();

    public VotingApplicationRepository() {
        getData();
    }

    private void getData() {
        getCandidatesData();
        getVotersData();
    }

    private void getCandidatesData() {
        Optional<List<Candidate>> optionalCandidates = JsonMapper.readFile(
                "data/json/candidates.json", Util.getType(new TypeToken<List<Candidate>>() {})
        );

        optionalCandidates.ifPresent(candidates -> this.candidates = candidates);
    }

    private void getVotersData() {
        Optional<List<Voter>> optionalVoters = JsonMapper.readFile(
                "data/json/voters.json", Util.getType(new TypeToken<List<Voter>>() {})
        );

        optionalVoters.ifPresent(voters -> this.voters = voters);
    }

    public Optional<Candidate> findCandidateById(long id) {
        return candidates.stream()
                .filter(candidate -> candidate.getId() == id)
                .findFirst();
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public List<Voter> getVoters() {
        return voters;
    }

    public Optional<Voter> findVoterByEmail(String email) {
        return voters.stream()
                .filter(voter -> voter.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Optional<Voter> findVoterByCookieIdentityValue(String cookieIdentity) {
        return voters.stream()
                .filter(voter -> voter.getUniqueCookieIdentity().equals(cookieIdentity))
                .findFirst();
    }

    public void addVoter(Voter voter) {
        voters.add(voter);
    }
}
