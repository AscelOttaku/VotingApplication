package server;

public enum ResponseCodes {
    OK(200),
    NOT_FOUND(404),
    NO_CONTENT(201),
    BAD_REQUEST(400),
    REDIRECT(303);

    private final int code;

    ResponseCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
