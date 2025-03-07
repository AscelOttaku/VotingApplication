package server;

import com.sun.net.httpserver.HttpExchange;
import service.VotingApplication;

import java.io.IOException;
import java.util.Map;

public class LocalVotingServer extends BasicServer {
    private final VotingApplication server = new VotingApplication();

    public LocalVotingServer(int port) throws IOException {
        super(port);
        registerGet("/", this::handleMainVotingApplicationRequest);
    }

    private void handleMainVotingApplicationRequest(HttpExchange exchange) {
        var allCandidates = server.getAllCandidates();

        renderTemplate(exchange, "ftlh/candidates.ftlh", Map.of("candidates", allCandidates));
    }
}
