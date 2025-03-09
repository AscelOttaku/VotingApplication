package exception;

public class ExceptionBody {
    private final int status;
    private final String message;
    private final Throwable cause;
    private final String path;

    public ExceptionBody(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.cause = builder.cause;
        this.path = builder.path;
    }

    public static class Builder {
        private int status;
        private String message;
        private Throwable cause;
        private String path;

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder cause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public ExceptionBody build() {
            return new ExceptionBody(this);
        }
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getCause() {
        return cause;
    }

    public String getPath() {
        return path;
    }
}
