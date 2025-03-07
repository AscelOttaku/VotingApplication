package repository;

import com.google.gson.reflect.TypeToken;
import models.Candidate;
import utils.JsonMapper;
import utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VotingApplicationRepository {
    private List<Candidate> candidates = new ArrayList<>();

    public VotingApplicationRepository() {
        getData();
    }

    private void getData() {
        Optional<List<Candidate>> optionalCandidates = JsonMapper.readFile(
                "data/json/candidates.json", Util.getType(new TypeToken<List<Candidate>>() {})
        );

        optionalCandidates.ifPresent(candidates -> this.candidates = candidates);
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }
}
