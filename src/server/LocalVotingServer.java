package server;

import com.sun.net.httpserver.HttpExchange;
import cookie.Cookie;
import exception.ExceptionBody;
import models.Voter;
import service.VotingApplication;
import utils.Util;
import validators.InputDataValidator;

import java.io.IOException;
import java.nio.file.Path;
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
        registerGet("/register", this::registerUser);
        registerGet("/login", this::login);
        registerPost("/register", this::handleRegisterUserRequest);
        registerPost("/login", this::handleLoginRequest);
        registerGet("/exist", this::existRequestHandle);
    }

    private void handleVoteRequest(HttpExchange exchange) {
        if (!service.checkIfVoterAuthorized(exchange)) {
            redirect(exchange, "/login");
            return;
        }

        var voter = service.findVoterByCookie(getCookieValueOfVoter(exchange));

        if (voter.isVoted()) {
            sendExceptionClass(
                    exchange, "Voter " + voter.getFirstName() + " is already voted", getPath(exchange)
            );
            return;
        }

        String candidateId = getQuery(exchange).split("=")[1];

        boolean res = service.voteForCandidate(voter, Util.parseLong(candidateId));

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

    private String getCookieValueOfVoter(HttpExchange exchange) {
        return Util.getCookieValue(exchange).split("=")[1];
    }

    private void sendExceptionClass(HttpExchange exchange, String message, String path) {
        var exceptionBody = new ExceptionBody.Builder()
                .message(message)
                .status(404)
                .cause(null)
                .path(path)
                .build();

        renderTemplate(exchange, "ftlh/operationException.ftlh", createDataModel("exception", exceptionBody));
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

    private void registerUser(HttpExchange exchange) {
        Path path = makeFilePath("auth/register.ftlh");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void login(HttpExchange exchange) {
        Path path = makeFilePath("auth/login.ftlh");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void handleRegisterUserRequest(HttpExchange exchange) {
        var optionalParsed = InputDataValidator.getValidDataOrThrowAnException(exchange);

        if (optionalParsed.isEmpty()) {
            failedRegistration(exchange);
            return;
        }

        Map<String, String> parsed = optionalParsed.get();

        String email = parsed.get("email");
        boolean res = service.checkIfVoterExist(email);

        if (res) {
            failedRegistration(exchange);
            return;
        }

        var newEmployee = service.registerNewVoter(parsed);
        sendCookieWithEmployeeIdentity(exchange, newEmployee.getUniqueCookieIdentity());

        redirect(exchange, "/");
    }

    private void failedRegistration(HttpExchange exchange) {
        sendFile(exchange, "auth/registerFailedData.ftlh");
    }

    private void handleLoginRequest(HttpExchange exchange) {
        var optionalParsed = InputDataValidator.getValidDataOrThrowAnException(exchange);

        if (optionalParsed.isEmpty()) {
            loginFailed(exchange);
            return;
        }

        var parsed = optionalParsed.get();

        String email = parsed.get("email");
        String password = parsed.get("password");
        boolean res = service.checkForVoterByEmailAndPassword(email, password);

        if (res) {
            sendCookie(exchange, email);
            redirect(exchange, "/");
            return;
        }

        loginFailed(exchange);
    }

    private void sendCookie(HttpExchange exchange, String email) {
        boolean isSetCookie = service.setVoterCookieIdentity(email);

        if (isSetCookie) {
             var optionalVoter = service.findVoterByEmail(email);

             if (optionalVoter.isEmpty()) {
                 loginFailed(exchange);
                 return;
             }

            String uniqueCookieIdentity = optionalVoter.get().getUniqueCookieIdentity();

            sendCookieWithEmployeeIdentity(exchange, uniqueCookieIdentity);
        }
    }

    private void loginFailed(HttpExchange exchange) {
        sendFile(exchange, "auth/loginFailed.ftlh");
    }

    private void sendFile(HttpExchange exchange, String path) {
        Path getPath = makeFilePath(path);
        sendFile(exchange, getPath, ContentType.TEXT_HTML);
    }

    private void sendCookieWithEmployeeIdentity(HttpExchange exchange, String identity) {
        Cookie cookie = Cookie.make("voterIdentity", identity);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(600);
        cookie.setPath("/");
        setCookie(exchange, cookie);
    }

    protected void setCookie(HttpExchange exchange, Cookie cookie) {
        exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
    }

    private void existRequestHandle(HttpExchange exchange) {
        Voter voter;

        try {
            voter = service.findVoterByCookie(getCookieValueOfVoter(exchange));
        } catch (Exception e) {
            sendExceptionClass(exchange, e.getMessage(), getPath(exchange));
            return;
        }

        if (voter == null) {
            sendExceptionClass(exchange, "Voter not found", getPath(exchange));
            return;
        }

        if (voter.getUniqueCookieIdentity().isBlank())
            redirect(exchange, "/login");
        else
            deleteCookie(exchange, voter);
    }

    private void deleteCookie(HttpExchange exchange, Voter voter) {
        Cookie cookie = Cookie.make("voterIdentity", voter.getUniqueCookieIdentity());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);

        setCookie(exchange, cookie);
        redirect(exchange, "/login");
    }
}
