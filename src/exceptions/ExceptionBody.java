package exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExceptionBody {
    private final int statusCode;
    private final String message;
    private final String path;
    private final Throwable throwable;
    private final String currentTime;

    public ExceptionBody(Builder builder) {
        this.statusCode = builder.statusCode;
        this.message = builder.message;
        this.path = builder.path;
        this.throwable = builder.throwable;
        this.currentTime = getCurrentTimeInCorrectFormat(builder.currentTime);
    }

    private String getCurrentTimeInCorrectFormat(LocalDateTime currentTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentTime.format(formatter);
    }

    public static class Builder {
        private int statusCode;
        private String message;
        private String path;
        private Throwable throwable;
        private LocalDateTime currentTime;

        public Builder status(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder path(String path) {
            this.path = "Path: " + path;
            return this;
        }

        public Builder throwable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        public Builder currentTime(LocalDateTime currentTime) {
            this.currentTime = currentTime;
            return this;
        }

        public ExceptionBody build() {
            return new ExceptionBody(this);
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getCurrentTime() {
        return currentTime;
    }
}
