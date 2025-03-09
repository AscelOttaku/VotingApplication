package server;

import com.sun.net.httpserver.HttpExchange;
import exception.ExceptionBody;
import service.VotingApplication;
import utils.JsonMapper;
import utils.Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.Util.createDataModel;

public class LocalVotingServer extends BasicServer {
    private final VotingApplication service = new VotingApplication();

    public LocalVotingServer(int port) throws IOException {
        super(port);
        registerGet("/", this::handleMainVotingApplicationRequest);
        registerGet("/votes", this::handleShowVotingPageRequest);
        registerGet("/vote", this::handleVoteRequest);
    }

    private void handleVoteRequest(HttpExchange exchange) {
        String candidateId = getQuery(exchange).split("=")[1];

        boolean res = service.voteForCandidate(Util.parseLong(candidateId));

        if (!res) {
            sendExceptionClass(exchange,"Candidate not found by id " + candidateId, getPath(exchange));
            return;
        }

        var candidate = service.findCandidateById(Util.parseLong(candidateId));
        var votingQuantityInProccent = service.getVoteQuantityInProccent(candidate);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("candidate", candidate);
        dataModel.put("votingQuantityInProccent", votingQuantityInProccent);

        renderTemplate(exchange, "ftlh/thankyou.ftlh", dataModel);
    }

    private void sendExceptionClass(HttpExchange exchange, String message, String path) {
        var exceptionBody = new ExceptionBody.Builder()
                .message(message)
                .status(404)
                .cause(null)
                .path(path)
                .build();

        renderTemplate(exchange, "ftlh/candidateNotFound.ftlh", createDataModel("exception", exceptionBody));
    }

    private void handleMainVotingApplicationRequest(HttpExchange exchange) {
        var allCandidates = service.getAllCandidates();

        renderTemplate(exchange, "ftlh/candidates.ftlh", createDataModel("candidates", allCandidates));
    }

    private void handleShowVotingPageRequest(HttpExchange exchange) {
        var allCandidatesAndVotes = service.getCandidatesAndVotes();

        renderTemplate(exchange, "ftlh/votes.ftlh", createDataModel("candidatesAndVotes", allCandidatesAndVotes));
    }

    private String getQuery(HttpExchange exchange) {
        return exchange.getRequestURI().getQuery();
    }

    private String getPath(HttpExchange exchange) {
        return exchange.getRequestURI().getPath();
    }
}
